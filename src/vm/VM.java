package vm;

import java.util.ArrayList;
import java.util.List;
import static vm.Bytecode.*;
/**
 *
 * @author Berzeger
 */
public class VM {
    final int STACK_SIZE = 100;
    
    int[] globals;
    int[] code;
    int[] stack;
    
    int ip; // Instruction Pointer
    int sp = -1; // Stack Pointer
    int fp; // Function Pointer
    
    public boolean debug = false;
    
    public VM(int[] code, int main, int datasize) {
        this.code = code;
        this.ip = main;
        globals = new int[datasize];
        stack = new int[STACK_SIZE];
    }
    
    public void run() {
    loop:
        while (ip < code.length) {
            int opcode = code[ip]; // Fetch the first instruction
            if (debug) System.err.printf("%-35s", disassemble());       
            ip++;
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
            }
            
            if (debug) System.err.println(stackString());
        }
        
        if (debug) {
            if (ip < code.length) System.err.printf("%-35s", disassemble());
            System.err.println(stackString());
            dumpDataMemory();
        }
    }

    private String disassemble() {
        int opcode = code[ip];
        String opName = Bytecode.instructions[opcode].name;
        StringBuilder buf = new StringBuilder();
        buf.append(String.format("%04d:\t%-11s", ip, opName));
        int nargs = Bytecode.instructions[opcode].numOfOperands;
        
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
        StringBuilder buf = new StringBuilder();
        buf.append("stack=[");
        
        for (int i = 0; i <= sp; i++) {
            int o = stack[i];
            buf.append(" ");
            buf.append(o);
        }
        
        buf.append(" ]");
        return buf.toString();
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
}
