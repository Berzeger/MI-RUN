package instructions;

import java.util.Stack;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class IDIV extends Instruction {

    @Override
    public void execute(Stack<Integer> stack) {
        // divide the top operand by the operand below
        int operand1 = stack.pop();
        int operand2 = stack.pop();
        stack.push(operand2 / operand1);
    }

}
