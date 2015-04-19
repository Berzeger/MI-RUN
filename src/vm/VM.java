package vm;

import java.util.ArrayList;
import java.util.List;
import static vm.Bytecode.*;
import vm.model.Heap;
import vm.model.Stack;
import vm.model.VMClass;
import vm.model.VMField;
import vm.model.VMMethod;
import vm.tables.ClassesTable;
import vm.tables.InstructionsTable;
import vm.tables.MethodsTable;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class VM {

    final int STACK_SIZE = 100;

    int[] globals;
    int[] code;
    Stack stack;

    int ip; // Instruction Pointer
    int sp = -1; // Stack Pointer
    int fp; // Function Pointer

    BytecodeReader bcReader; // byte code reader
    Bytecode bytecode; // bytecode constants etc
    ClassesTable classesTable;
    MethodsTable methodsTable;
    InstructionsTable instructionsTable;
    Heap heap;

    public boolean debug = false;

    private VM() {
    }

    public VM(int[] code, int main, int datasize) {
        this.code = code;
        this.ip = main;
        globals = new int[datasize];
        stack = new Stack(256, 256);
        heap = new Heap(this, 1024);
        classesTable = new ClassesTable();
        methodsTable = new MethodsTable();
        instructionsTable = new InstructionsTable();
        bcReader = new BytecodeReader(this);
        bytecode = new Bytecode();
    }
    
    private void printDebugInfo() {
        if (debug) {
            for (VMClass clazz : classesTable.getClasses()) {
                System.err.println("Class name: " + clazz.name + ", superclass: " + clazz.superClassName);    
                
                for (VMMethod method : clazz.methods) {
                    System.err.println("\tMethod name: " + method.name + ", return type: " + method.returnType);
                    
                    for (VMField argument : method.arguments) {
                        System.err.println("\t\tArgument: " + argument.name + ", argument type: " + argument.type);
                    }
                    for (VMField local : method.locals) {
                        System.err.println("\t\tLocal: " + local.name + ", local type: " + local.type);
                    }
                }
                
                System.err.println("\tConstant Pool:");
                
                for (int i = 0; i < clazz.constantPool.size(); i++) {
                    try {
                        // Maybe we don't have to parseInt?
                        System.err.println("\t\tcPoolItem type: " + clazz.constantPool.getItem(i).getType() + ", cPoolItem value: " + Integer.parseInt(clazz.constantPool.getItem(i).getValue()));
                    } catch (Exception ex) {
                        System.err.println("\t\tcPoolItem type: " + clazz.constantPool.getItem(i).getType() + ", cPoolItem value: " + clazz.constantPool.getItem(i).getValue());  
                    }
                }
            }
        }
    }

    public void run() {
        printDebugInfo();
        VMClass mainClass = getMainClass();
        int address = getHeap().allocClass(mainClass);
        
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
                new vm.instructions.ICONST_M1().execute(this);
                break;
            case ICONST_0:
                new vm.instructions.ICONST_0().execute(this);
                break;
            case ICONST_1:
                new vm.instructions.ICONST_1().execute(this);
                break;
            case ICONST_2:
                new vm.instructions.ICONST_2().execute(this);
                break;
            case ICONST_3:
                new vm.instructions.ICONST_3().execute(this);
                break;
            case ICONST_4:
                new vm.instructions.ICONST_4().execute(this);
                break;
            case ICONST_5:
                new vm.instructions.ICONST_5().execute(this);
                break;
            case IADD:
                new vm.instructions.IADD().execute(this);
                break;
            case ISUB:
                new vm.instructions.ISUB().execute(this);
                break;
            case IDIV:
                new vm.instructions.IDIV().execute(this);
                break;
            case IMUL:
                new vm.instructions.IMUL().execute(this);
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
    
    public Stack getStack() {
        return stack;
    }
    
    public Heap getHeap() {
        return heap;
    }
    
    public ClassesTable getClassesTable() {
        return classesTable;
    }
    
    public List<VMMethod> getMethodsTable() {
        return methodsTable.getMethods();
    }
    
    public InstructionsTable getInstructionsTable() {
        return instructionsTable;
    }

    private VMClass getMainClass() {
        for (VMClass clazz : getClassesTable().getClasses()) {
            for (VMMethod method : getMethodsTable()) {
                if (method.isStatic && method.name.equals("main") && !method.clazz.name.startsWith("java.")) {
                    return clazz;
                }
            }
        }
        
        throw new RuntimeException("Main class not found.");
    }
}
