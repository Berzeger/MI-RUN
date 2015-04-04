package vm.instructions;

import vm.VM;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class ICONST_M1 extends Instruction {

    @Override
    public void execute(VM vm) {
        vm.getStack().push(-1);
    }
}
