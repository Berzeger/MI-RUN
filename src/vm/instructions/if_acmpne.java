package vm.instructions;

import vm.VM;

/**
 * if_acmpne: If references are not equal.
 * @author Filip
 */
public class if_acmpne extends Instruction {

    @Override
    public void execute(VM vm, String[] args) {
        int ref1 = vm.getStack().popPointer();
        int ref2 = vm.getStack().popPointer();
        
        if (ref1 != ref2) {
            vm.getInstructionsTable().jump(Integer.parseInt(args[0]));
        }
    }
    
}
