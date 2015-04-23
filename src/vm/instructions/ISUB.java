package vm.instructions;

import vm.VM;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class ISUB extends Instruction {

    @Override
    public void execute(VM vm, String[] args) {
        // subtract the top operand from the operand below
        int operand1 = vm.getStack().popInt();
        int operand2 = vm.getStack().popInt();
        vm.getStack().pushInt(operand2 - operand1);
    }
}
