package vm.instructions;

import vm.VM;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class ICONST_5 extends Instruction {

    @Override
    public void execute(VM vm, String[] args) {
        vm.getStack().pushInt(5);
    }
}
