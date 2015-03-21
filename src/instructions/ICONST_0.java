package instructions;

import java.util.Stack;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class ICONST_0 extends Instruction {

    @Override
    public void execute(Stack<Integer> stack) {
        stack.push(0);
    }
}
