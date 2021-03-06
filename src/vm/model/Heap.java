package vm.model;

import java.util.ArrayList;
import java.util.List;
import vm.Utils;
import vm.VM;
import vm.garbagecollector.GarbageCollector;

/**
 *
 * @author Filip
 */
public final class Heap {

    private final VM virtualMachine;
    private final int size;
    private final GarbageCollector garbageCollector;
    private byte[] space1;
    private byte[] space2;
    private int pointer;
    private int activeSpace;

    // 4 bytes for class marker, 4 bytes for size, 4 bytes for handle
    public static final int OBJECT_HEADER_SIZE = 12;

    public static final int CLASS_MARK = 0xC1A55;

    public Heap(VM virtualMachine, int size) {
        this.virtualMachine = virtualMachine;
        this.size = size;
        int halfSize = size / 2;
        space1 = new byte[halfSize];
        space2 = new byte[halfSize];
        pointer = 0;
        activeSpace = 0;
        garbageCollector = new GarbageCollector(virtualMachine);
    }

    private boolean isHeapFull(int bytes) {
        return (getSpace().length - pointer) <= (OBJECT_HEADER_SIZE + bytes);
    }
    
    public int getActiveSpaceNumber() {
        return activeSpace;
    }
    
    public void setActiveSpace(int space) {
        activeSpace = space;
    }
    
    public void setPointer(int p) {
        pointer = p;
    }

    public byte[] getSpace() {
        return activeSpace == 0 ? space1 : space2;
    }

    public byte[] getOtherSpace() {
        return activeSpace == 0 ? space2 : space1;
    }
    
    public byte[] getSpace(int space) {
        return space == 0 ? space1 : space2;
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
            garbageCollector.collect();
        }

        // Class marker
        saveInt(CLASS_MARK);

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
        int handle = Utils.byteArrayToInt(getSpace(), address + 4);
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

    public void switchSpaces() {
        // 0 -> 1
        // 1 -> 0
        activeSpace = (activeSpace + 1) % 2;
    }
}
