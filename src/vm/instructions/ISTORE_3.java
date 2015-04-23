package vm.instructions;

import vm.VM;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class ISTORE_3 extends Instruction {

    @Override
    public void execute(VM vm, String[] args) {
        vm.getStack().setLocalInt(3, vm.getStack().popInt());

    }

}
