package vm.instructions;

import vm.VM;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class imul extends Instruction {

    @Override
    public void execute(VM vm, String[] args) {
        int operand1 = vm.getStack().popInt();
        int operand2 = vm.getStack().popInt();
        vm.getStack().pushInt(operand1 * operand2);
    }
}
