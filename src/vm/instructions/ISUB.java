package vm.instructions;

import vm.VM;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class ISUB extends Instruction {

    @Override
    public void execute(VM vm) {
        // subtract the top operand from the operand below
        int operand1 = vm.getStack().pop();
        int operand2 = vm.getStack().pop();
        vm.getStack().push(operand2 - operand1);
    }
}
