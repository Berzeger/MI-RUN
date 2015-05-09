package vm.instructions;

import vm.VM;

/**
 *
 * @author Filip
 */
public class anewarray extends Instruction {

    @Override
    public void execute(VM vm, String[] args) {
        int size;
        size = vm.getStack().popInt();
        int pointer = vm.getHeap().allocObjectArray(size);
        vm.getStack().pushPointer(pointer);
    }

}
