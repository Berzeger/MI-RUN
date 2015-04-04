package vm.instructions;

import vm.VM;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public abstract class Instruction {
    public abstract void execute(VM vm);
}
