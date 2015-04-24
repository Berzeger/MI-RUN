package vm.instructions;

import vm.VM;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class istore extends Instruction {

    private final int arg;
    
    public istore() {
        arg = -1;
    }
    public istore(int arg) {
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
        
        vm.getStack().setLocalInt(operand, vm.getStack().popInt());
    }
    
}
