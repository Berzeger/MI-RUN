package instructions;

import vm.VM;

/**
 *
 * @author Filip
 */
public class ICONST_4 extends Instruction {

    @Override
    public void execute(VM vm) {
        vm.getStack().push(4);
    }
}
