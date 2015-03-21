package vm;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class Bytecode {

    public Bytecode() {
        instructions = new HashMap<>();
        instructions.put(0x60, new Instruction("IADD"));
        instructions.put(0x64, new Instruction("ISUB"));
        instructions.put(0x68, new Instruction("IMUL"));
        instructions.put(0x6c, new Instruction("IDIV"));

        instructions.put(0xa1, new Instruction("IF_ICMPLT"));
        instructions.put(0xa3, new Instruction("IF_ICMPGT"));
        instructions.put(0x9f, new Instruction("IF_ICMPEQ"));

        instructions.put(0x2, new Instruction("ICONST_M1"));
        instructions.put(0x3, new Instruction("ICONST_0"));
        instructions.put(0x4, new Instruction("ICONST_1"));
        instructions.put(0x5, new Instruction("ICONST_2"));
        instructions.put(0x6, new Instruction("ICONST_3"));
        instructions.put(0x7, new Instruction("ICONST_4"));
        instructions.put(0x8, new Instruction("ICONST_5"));

        instructions.put(0x36, new Instruction("ISTORE", 1));
        instructions.put(0x3b, new Instruction("ISTORE_0"));
        instructions.put(0x3c, new Instruction("ISTORE_1"));
        instructions.put(0x3d, new Instruction("ISTORE_2"));
        instructions.put(0x3e, new Instruction("ISTORE_3"));

        instructions.put(0xbb, new Instruction("NEW", 1));
        instructions.put(0xb9, new Instruction("INVOKEINTERFACE", 1));
        instructions.put(0xb8, new Instruction("INVOKESTATIC", 1));
        instructions.put(0xb7, new Instruction("INVOKESPECIAL", 1));
        instructions.put(0xb6, new Instruction("INVOKEVIRTUAL", 1));
        instructions.put(0x59, new Instruction("DUP"));
    }

    public Map<Integer, Instruction> getInstructions() {
        return instructions;
    }

    private Map<Integer, Instruction> instructions;

    // Basic math operations
    public static final int IADD = 0x60; // int add
    public static final int ISUB = 0x64; // int sub
    public static final int IMUL = 0x68; // int mul
    public static final int IDIV = 0x6c; // int div

    // Branching
    public static final int IF_ICMPLT = 0xa1; // int less than 
    public static final int IF_ICMPGT = 0xa3; // int greater than
    public static final int IF_ICMPEQ = 0x9f; // int equal 

    // ICONST
    public static final int ICONST_M1 = 0x2; // int -1 constant push
    public static final int ICONST_0 = 0x3;
    public static final int ICONST_1 = 0x4;
    public static final int ICONST_2 = 0x5;
    public static final int ICONST_3 = 0x6;
    public static final int ICONST_4 = 0x7;
    public static final int ICONST_5 = 0x8;

    // ISTORE
    public static final int ISTORE = 0x36;
    public static final int ISTORE_0 = 0x3b;
    public static final int ISTORE_1 = 0x3c;
    public static final int ISTORE_2 = 0x3d;
    public static final int ISTORE_3 = 0x3e;

    // TODO: Convert to Java specifications
    /*
     public static final int LOAD = 10; // load from local constant
     public static final int GLOAD = 11; // load from global constant
     public static final int STORE = 12; // store in local constant
     public static final int GSTORE = 13; // store in global constant
     public static final int PRINT = 14; // prints stack top
     public static final int POP = 15; // throws away the top of the stack
     public static final int HALT = 16;
     */
    // java
    public static final int NEW = 0xBB; // create a reference, usually with this pattern: new -> dup -> invokespecial -> astore
    public static final int INVOKEINTERFACE = 0xB9; // call an interface method
    public static final int INVOKESTATIC = 0xB8; // call a static method
    public static final int INVOKESPECIAL = 0xB7; // call either a constructor, a private method or a superclass method
    public static final int INVOKEVIRTUAL = 0xB6; // call a class instance method (eg. System.out.println(...))
    public static final int DUP = 0x59; // duplicate an object on the top of the stack

    /**
     * Used for debugging purposes - string representation of opcodes for
     * output.
     */
    public class Instruction {

        String name;
        int numOfOperands = 0;

        public Instruction(String name) {
            this(name, 0);
        }

        public Instruction(String name, int nargs) {
            this.name = name;
            this.numOfOperands = nargs;
        }
    }
}
