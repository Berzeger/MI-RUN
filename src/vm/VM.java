package vm;

import static vm.Bytecode.*;
/**
 *
 * @author Berzeger
 */
public class VM {
    final int STACK_SIZE = 100;
    
    int[] data;
    int[] code;
    int[] stack;
    
    int ip; // Instruction Pointer
    int sp = -1; // Stack Pointer
    int fp; // Function Pointer
    
    public boolean debug = false;
    
    public VM(int[] code, int main, int datasize) {
        this.code = code;
        this.ip = main;
        data = new int[datasize];
        stack = new int[STACK_SIZE];
    }
    
    public void run() {
        while (ip < code.length) {
            int opcode = code[ip]; // Fetch the first instruction
            
            if (debug) {
                disassemble(opcode);
            }
            
            ip++; // If we increase the pointer while fetching, debug output will be incorrect
            
            switch (opcode) {
                case ICONST:
                    int operand = code[ip++]; // Read the operand from the code
                    stack[++sp] = operand; // Increase SP and push the operand
                    break;
                case IADD:
                    int operand1 = stack[sp--];
                    int operand2 = stack[sp--];
                    stack[++sp] = operand1 + operand2;
                    break;
                case PRINT:
                    operand = stack[sp--]; // Pop 
                    System.out.println(operand);
                    break;
                case HALT:
                    return;
            }
        }
    }

    private void disassemble(int opcode) {
        Instruction instruction =  Bytecode.instructions[opcode];
        System.err.printf("%04d: %s", ip, instruction.name);

        if (instruction.numOfOperands == 1) {
            System.err.printf(" %d", code[ip + 1]);
        } else if (instruction.numOfOperands == 2) {
            System.err.printf(" %d, %d", code[ip + 1], code[ip + 2]);
        }

        System.err.println();    
    }
}
