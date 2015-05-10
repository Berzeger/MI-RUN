/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.garbagecollector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import vm.Utils;
import vm.VM;
import vm.model.FieldType;
import vm.model.Heap;
import vm.model.StackFrame;
import vm.model.VMClass;

/**
 *
 * @author Filip
 */
public class GarbageCollector {

    private VM virtualMachine;
    private HashMap<Integer, List<ReallocationHelper>> isAddressedIn;
    private HashMap<Integer, List<ReallocationHelper>> isHolderOf;
    private HashSet<Integer> allObjects;
    private HashMap<Integer, List<StackPlaceHolder>> stackHolders;

    public GarbageCollector(VM virtualMachine) {
        this.virtualMachine = virtualMachine;
    }

    public void collect() {
        if (virtualMachine.debug) {
            System.out.println("Starting garbage collector.");
        }

        stackHolders = new HashMap<>();
        isAddressedIn = new HashMap<>();
        isHolderOf = new HashMap<>();
        allObjects = new HashSet<>();

        findPointersInStack();
        copyToNewSpace();

        if (virtualMachine.debug) {
            System.out.println("Garbage collecting finished. Enjoy your new trash-free heap!");
        }
    }

    public void findPointersInStack() {
        for (StackFrame sf : virtualMachine.getStack().getStackFrames()) {
            if (sf == null) {
                break;
            }
            for (int i = 0; i < sf.getPointer(); i += FieldType.TYPE_BYTE_SIZE) {

                if (Utils.isPointer(sf.getContent()[i + 3])) {
                    int p = Utils.fieldTypeToInt(Utils.byteArrayToInt(Utils.subArray(sf.getContent(), i, 4), 0));
                    if (p == 0) {
                        continue;
                    }

                    StackPlaceHolder holder = new StackPlaceHolder();
                    holder.pointer = p;
                    holder.pointerPosition = i;
                    holder.stackFrame = sf;

                    List<StackPlaceHolder> holders = stackHolders.get(p);
                    if (holders == null) {
                        holders = new ArrayList<>();
                    }
                    holders.add(holder);

                    stackHolders.put(p, holders);
                }
            }
        }
    }

    public void copyToNewSpace() {
        int newSpace = 0;
        int newSpaceNext = 0;
        if (virtualMachine.getHeap().getActiveSpaceNumber() == 0) {
            newSpace = 1;
        }

        for (int i = 0; i < virtualMachine.getHeap().getSpace(newSpace).length; i++) {
            virtualMachine.getHeap().getSpace(newSpace)[i] = 0;
        }
        newSpaceNext += 4;

        for (int pointer : allObjects) {
            VMClass clazz = virtualMachine.getHeap().getObject(pointer);

            int size = countObjectSize(clazz, pointer);
            int newPointer = newSpaceNext;

            for (int j = pointer; j < pointer + size; j++) {
                virtualMachine.getHeap().getSpace(newSpace)[newSpaceNext++] = virtualMachine.getHeap().getSpace()[j];
            }

            int mod = newSpaceNext % 4;   // 4b padding
            if (mod != 0) {
                newSpaceNext += (4 - mod);
            }

            if (stackHolders.containsKey(pointer)) {
                List<StackPlaceHolder> holders = stackHolders.get(pointer);
                if (holders != null) {
                    for (StackPlaceHolder holder : holders) {
                        if (holder.stackFrame != null) {
                            byte[] value = Utils.intToByteArray(Utils.createPointer(newPointer));
                            System.arraycopy(value, 0, holder.stackFrame.getContent(), holder.pointerPosition, 4);
                        }
                    }
                }
            }

            List<ReallocationHelper> holders = isAddressedIn.get(pointer);
            if (holders != null) {
                for (ReallocationHelper holder : holders) {
                    if (holder.partOfObjectNewPointer != 0) {
                        Utils.setPointerField(virtualMachine.getHeap().getSpace(newSpace), holder.partOfObjectNewPointer, holder.onField, newPointer);
                    } else {
                        Utils.setPointerField(virtualMachine.getHeap().getSpace(), holder.partOfObjectPointer, holder.onField, newPointer);
                    }
                }
            }

            List<ReallocationHelper> childs = isHolderOf.get(pointer);
            if (childs != null) {
                for (ReallocationHelper child : childs) {
                    child.partOfObjectNewPointer = newPointer;
                }
            }

        }

        virtualMachine.getHeap().setActiveSpace(newSpace);
        virtualMachine.getHeap().setPointer(newSpaceNext);

    }

    private int countObjectSize(VMClass clazz, int pointer) {
        switch (clazz.name) {
            case "java.lang.Array":
            {
                int size = Utils.getArrayLength(virtualMachine, pointer);
                return Heap.OBJECT_HEADER_SIZE + size;
            }
            case "java.lang.ObjectArray":
            {
                int size = Utils.getArrayLength(virtualMachine, pointer);
                return Heap.OBJECT_HEADER_SIZE + size * FieldType.TYPE_BYTE_SIZE;
            }
            default:
                return Heap.OBJECT_HEADER_SIZE + clazz.fields.size() * FieldType.TYPE_BYTE_SIZE;
        }
    }
}
