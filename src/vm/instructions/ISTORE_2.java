package vm.instructions;

import vm.VM;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class ISTORE_2 extends Instruction {

    @Override
    public void execute(VM vm, String[] args) {
        int operand = vm.getStack().popInt();
        vm.getStack().setLocalInt(2, operand);
    }
}
