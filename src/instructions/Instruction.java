package instructions;

import java.util.Stack;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public abstract class Instruction {
    public abstract void execute(Stack<Integer> stack);
}
