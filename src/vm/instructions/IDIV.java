package vm.instructions;

import vm.VM;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class IDIV extends Instruction {

    @Override
    public void execute(VM vm) {
        // divide the top operand by the operand below
        int operand1 = vm.getStack().pop();
        int operand2 = vm.getStack().pop();
        vm.getStack().push(operand2 / operand1);
    }

}
