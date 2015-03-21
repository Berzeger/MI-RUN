package instructions;

import java.util.Stack;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class IMUL extends Instruction {

    @Override
    public void execute(Stack<Integer> stack) {
        int operand1 = stack.pop();
        int operand2 = stack.pop();
        stack.push(operand1 * operand2);
    }
}
