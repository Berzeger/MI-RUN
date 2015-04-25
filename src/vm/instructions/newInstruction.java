package vm.instructions;

import vm.VM;
import vm.model.VMClass;

/**
 *
 * @author Filip
 */
public class newInstruction extends Instruction {

    @Override
    public void execute(VM vm, String[] args) {
        VMClass clazz = vm.getClassesTable().getClassByName(args[0]);
        vm.getStack().pushPointer(vm.getHeap().allocClass(clazz));
    }
    
}
