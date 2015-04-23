package vm.instructions;

import vm.VM;

/**
 *
 * @author Filip
 */
public class ASTORE extends Instruction {

    private final int arg;
    
    public ASTORE() {
        arg = -1;
    }
    public ASTORE(int arg) {
        this.arg = arg;
    }
    
    @Override
    public void execute(VM vm, String[] args) {
        int operand;
        
        if (arg == -1) {
            operand = Integer.parseInt(args[0]);
        } else {
            operand = arg;
        }
        
        vm.getStack().setLocalPointer(operand, vm.getStack().popPointer());
    }
    
}
