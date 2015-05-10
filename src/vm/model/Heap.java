package vm.model;

import java.util.ArrayList;
import java.util.List;
import vm.Utils;
import vm.VM;

/**
 *
 * @author Filip
 */
public final class Heap {

    private final VM virtualMachine;
    private final int size;
    private byte[] space1;
    private byte[] space2;
    private int pointer;
    private int activeSpace;

    // 4 bytes for size, 4 bytes for handle
    public static final int OBJECT_HEADER_SIZE = 8;

    public Heap(VM virtualMachine, int size) {
        this.virtualMachine = virtualMachine;
        this.size = size;
        int halfSize = size / 2;
        space1 = new byte[halfSize];
        space2 = new byte[halfSize];
        pointer = 0;
        activeSpace = 0;
    }

    private boolean isHeapFull(int bytes) {
        return (getSpace().length + bytes) > (size / 2);
    }

    public byte[] getSpace() {
        return activeSpace == 0 ? space1 : space2;
    }

    public int allocClass(VMClass clazz) {
        return alloc(clazz, 0, clazz.fields.size() * FieldType.TYPE_BYTE_SIZE);
    }

    public int allocByteArray(int size) {
        return alloc(virtualMachine.getClassesTable().getClassByName("java.lang.Array"), size, size);
    }

    public int allocByteArray(int size, byte[] bytes) {
        int start = alloc(virtualMachine.getClassesTable().getClassByName("java.lang.Array"), size, size * FieldType.TYPE_BYTE_SIZE);
        System.arraycopy(bytes, 0, getSpace(), start + OBJECT_HEADER_SIZE, bytes.length);
        return start;
    }

    public int allocObjectArray(int size) {
        return alloc(virtualMachine.getClassesTable().getClassByName("java.lang.ObjectArray"), size, size * FieldType.TYPE_BYTE_SIZE);
    }

    public int alloc(VMClass clazz, int size, int content) {
        int address = pointer;

        if (isHeapFull(content)) {
            collect();
        }

        // Class handle 
        saveInt(clazz.handle);

        // Fields length
        saveInt(size);

        // Allocate the physical space. Will be filled with actual data when processing instructions.
        while (content > 0) {
            getSpace()[pointer++] = 0;
            content--;
        }

        // 4 bytes padding
        int mod = pointer % 4;
        if (mod != 0) {
            pointer += (4 - mod);
        }

        return address;
    }

    public VMClass getObject(int address) {
        // If it turns out we actually need to store 0xFFFFFFFF or some other shit,
        // we'll need to have something like pointer + 4 here.
        int handle = Utils.byteArrayToInt(getSpace(), address);
        return virtualMachine.getClassesTable().getClassByHandle(handle);
    }

    public void saveInt(int value) {
        byte[] byteArray = Utils.intToByteArray(value);
        getSpace()[pointer++] = byteArray[0];
        getSpace()[pointer++] = byteArray[1];
        getSpace()[pointer++] = byteArray[2];
        getSpace()[pointer++] = byteArray[3];
    }

    public int saveString(byte[] bytes) {
        VMClass clazz = virtualMachine.getClassesTable().getClassByName("java.lang.String");

        int address = allocClass(clazz);
        Utils.setIntField(getSpace(), address, clazz.getFieldIndex("length"), bytes.length);

        int arPointer = allocByteArray(bytes.length, bytes);
        Utils.setPointerField(getSpace(), address, clazz.getFieldIndex("bytes"), arPointer);

        return address;
    }

    private void switchSpaces() {
        // 0 -> 1
        // 1 -> 0
        activeSpace = (activeSpace + 1) % 2;
    }

    private void collect() {
        if (virtualMachine.debug) {
            System.out.println("Starting garbage collector.");
        }

        List<Integer> heapObjects = getAllObjectsOnHeap();
        List<Integer> stackObjects = getAllObjectsOnStack();
        
        // remove objects in use from the collection
        heapObjects.removeAll(stackObjects);
        
        moveAliveObjects(heapObjects);

        if (virtualMachine.debug) {
            System.out.println("Garbage collecting finished. Enjoy your new trash-free heap!");
        }
    }

    private List<Integer> getAllObjectsOnHeap() {
        List<Integer> objects = new ArrayList<>();

        for (int i = 0; i < getSpace().length; i += FieldType.TYPE_BYTE_SIZE) {
            VMClass clazz = getObject(i);

            if (clazz != null) {
                objects.add(i);
            }
        }

        return objects;
    }

    private List<Integer> getAllObjectsOnStack() {
        List<Integer> objects = new ArrayList<>();

        for (StackFrame sf : virtualMachine.getStack().getStackFrames()) {
            if (sf == null) {
                continue;
            }
            for (int i = 0; i < sf.getPointer(); i += FieldType.TYPE_BYTE_SIZE) {
                VMClass clazz = getObject(i);

                if (clazz != null) {
                    objects.add(i);
                }
            }
        }

        return objects;
    }

    private void moveAliveObjects(List<Integer> heapObjects) {
        
    }
}
