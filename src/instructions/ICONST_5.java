package instructions;

import java.util.Stack;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class ICONST_5 extends Instruction {

    @Override
    public void execute(Stack<Integer> stack) {
        stack.push(5);
    }
}
