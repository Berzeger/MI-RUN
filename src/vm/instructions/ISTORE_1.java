package vm.instructions;

import vm.VM;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class ISTORE_1 extends Instruction {

    @Override
    public void execute(VM vm, String[] args) {
        int operand = vm.getStack().popInt();
        System.out.println("operand istore: " + operand);
        vm.getStack().setLocalInt(1, operand);
    }
}
