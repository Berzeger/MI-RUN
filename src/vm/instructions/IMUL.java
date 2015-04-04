package vm.instructions;

import vm.VM;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class IMUL extends Instruction {

    @Override
    public void execute(VM vm) {
        int operand1 = vm.getStack().pop();
        int operand2 = vm.getStack().pop();
        vm.getStack().push(operand1 * operand2);
    }
}
