package vm.instructions;

import vm.VM;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class ISTORE extends Instruction {

    private int arg;
    
    public ISTORE() {
        arg = -1;
    }
    public ISTORE(int arg) {
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
