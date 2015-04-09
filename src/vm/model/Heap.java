package vm.model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import vm.VM;

/**
 *
 * @author Filip
 */
public class Heap {
    private final VM virtualMachine;
    private final int size;
    private byte[] space1;
    private byte[] space2;
    private int pointer;
    private int activeSpace;
    
    public Heap(VM virtualMachine, int size) {
        this.virtualMachine = virtualMachine;
        this.size = size;
        int halfSize = size/2;
        space1 = new byte[halfSize];
        space2 = new byte[halfSize];
        pointer = 0;
        activeSpace = 0;
    }
    
    private byte[] getSpace() {
        return activeSpace == 0 ? space1 : space2;
    }
    
    public int alloc(VMClass clazz, int size, byte[] content) {
        throw new NotImplementedException();
    }
}
