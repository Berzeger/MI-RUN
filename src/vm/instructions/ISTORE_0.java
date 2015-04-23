package vm.instructions;

import vm.VM;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class ISTORE_0 extends Instruction {

    @Override
    public void execute(VM vm, String[] args) {
        vm.getStack().setLocalInt(0, vm.getStack().popInt());

    }

}
