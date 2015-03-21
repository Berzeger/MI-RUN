package instructions;

import java.util.Stack;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class ISUB extends Instruction {

    @Override
    public void execute(Stack<Integer> stack) {
        // subtract the top operand from the operand below
        int operand1 = stack.pop();
        int operand2 = stack.pop();
        stack.push(operand2 - operand1);
    }
}
