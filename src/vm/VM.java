package vm;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import static vm.Bytecode.*;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class VM {

    final int STACK_SIZE = 100;

    int[] globals;
    int[] code;
    Stack<Integer> stack;

    int ip; // Instruction Pointer
    int sp = -1; // Stack Pointer
    int fp; // Function Pointer

    BytecodeReader bcReader; // byte code reader
    Bytecode bytecode; // bytecode constants etc

    public boolean debug = false;

    private VM() {
    }

    public VM(int[] code, int main, int datasize, Bytecode bc, BytecodeReader br) {
        this.code = code;
        this.ip = main;
        globals = new int[datasize];
        stack = new Stack<>();
        bcReader = br;
        bytecode = bc;
    }

    public void run() {
        loop:
        while (ip < code.length) {
            int opcode = code[ip]; // Fetch the first instruction
            if (debug) {
                System.err.printf("%-35s", disassemble());
            }
            ip++;

            executeOpcode(opcode);

            /*
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
             case GLOAD:
             int addr = code[ip++]; // take the specified address
             stack[++sp] = globals[addr]; // push it on top of the stack
             break;
             case GSTORE:
             int value = stack[sp--]; // take a value from the top of the stack
             addr = code[ip++]; // take the specified address
             globals[addr] = value;
             break;
             case HALT:
             break loop;
             */
            if (debug) {
                System.err.println(stackString());
            }
        }

        if (debug) {
            if (ip < code.length) {
                System.err.printf("%-35s", disassemble());
            }
            System.err.println(stackString());
            dumpDataMemory();
        }
    }

    private void executeOpcode(int opcode) {
        /* TODO:
         *  (x) Create a class for Stack. Allow it to be popped while simultaneously working with sp. Maybe default Java Stack would be enough?
         *  Create Instruction class with a constructor taking globals ArrayList, code ArrayList and stack Stack
         *  Each method will have it's own bytearray - we need to have an ip for each of them - need to think that thru
         */
        switch (opcode) {
            case ICONST_M1:
                new instructions.ICONST_M1().execute(this);
                break;
            case ICONST_0:
                new instructions.ICONST_0().execute(this);
                break;
            case ICONST_1:
                new instructions.ICONST_1().execute(this);
                break;
            case ICONST_2:
                new instructions.ICONST_2().execute(this);
                break;
            case ICONST_3:
                new instructions.ICONST_3().execute(this);
                break;
            case ICONST_4:
                new instructions.ICONST_4().execute(this);
                break;
            case ICONST_5:
                new instructions.ICONST_5().execute(this);
                break;
            case IADD:
                new instructions.IADD().execute(this);
                break;
            case ISUB:
                new instructions.ISUB().execute(this);
                break;
            case IDIV:
                new instructions.IDIV().execute(this);
                break;
            case IMUL:
                new instructions.IMUL().execute(this);
                break;
        }
    }

    private String disassemble() {
        int opcode = code[ip];
        String opName = bytecode.getInstructions().get(opcode).name;
        StringBuilder buf = new StringBuilder();
        buf.append(String.format("%04d:\t%-11s", ip, opName));
        int nargs = bytecode.getInstructions().get(opcode).numOfOperands;

        if (nargs > 0) {
            List<String> operands = new ArrayList<>();
            for (int i = ip + 1; i <= ip + nargs; i++) {
                operands.add(String.valueOf(code[i]));
            }
            for (int i = 0; i < operands.size(); i++) {
                String s = operands.get(i);
                if (i > 0) {
                    buf.append(", ");
                }

                buf.append(s);
            }
        }

        return buf.toString();
    }

    private String stackString() {
        return "stack = " + stack.toString();
    }

    private void dumpDataMemory() {
        System.err.println("Data memory:");
        int addr = 0;
        for (int o : globals) {
            System.err.printf("%04d: %s\n", addr, o);
            addr++;
        }
        System.err.println();
    }
    
    public Stack<Integer> getStack() {
        return stack;
    }
}
