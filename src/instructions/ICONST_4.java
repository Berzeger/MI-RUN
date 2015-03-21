package instructions;

import java.util.Stack;

/**
 *
 * @author Filip
 */
public class ICONST_4 extends Instruction {

    @Override
    public void execute(Stack<Integer> stack) {
        stack.push(4);
    }
}
