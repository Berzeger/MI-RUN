package vm.instructions;

import vm.VM;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class ICONST_3 extends Instruction {

    @Override
    public void execute(VM vm) {
        vm.getStack().push(3);
    }
}
