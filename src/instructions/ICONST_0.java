package instructions;

import vm.VM;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class ICONST_0 extends Instruction {

    @Override
    public void execute(VM vm) {
        vm.getStack().push(0);
    }
}
