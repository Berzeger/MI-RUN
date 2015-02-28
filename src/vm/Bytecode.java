package vm;

/**
 *
 * @author Berzeger
 */
public class Bytecode {
    public static final int IADD = 1; // int add
    public static final int ISUB = 2; // int sub
    public static final int IMUL = 3; // int mul
    public static final int ILT = 4; // int less than
    public static final int IEQ = 5; // int equal
    public static final int BR = 6; // branch
    public static final int BRT = 7; // branch if true
    public static final int BRF = 8; // branch if false
    public static final int ICONST = 9; // int constant push
    public static final int LOAD = 10; // load from local constant
    public static final int GLOAD = 11; // load from global constant
    public static final int STORE = 12; // store in local constant
    public static final int GSTORE = 13; // store in global constant
    public static final int PRINT = 14; // prints stack top
    public static final int POP = 15; // throws away the top of the stack
    public static final int HALT = 16;
    
    // java
    public static final int NEW = 0xBB; // create a reference, usually with this pattern: new -> dup -> invokespecial -> astore
    public static final int INVOKEINTERFACE = 0xB9; // call an interface method
    public static final int INVOKESTATIC = 0xB8; // call a static method
    public static final int INVOKESPECIAL = 0xB7; // call either a constructor, a private method or a superclass method
    public static final int INVOKEVIRTUAL = 0xB6; // call a class instance method (eg. System.out.println(...))
    public static final int DUP = 0x59;
    
    /**
     * Used for debugging purposes - string representation of opcodes for output.
     */
    static Instruction[] instructions = {
        null, // invalid
        new Instruction("IADD"),
        new Instruction("ISUB"),
        new Instruction("IMUL"),
        new Instruction("ILT"),
        new Instruction("IEQ"),
        new Instruction("BR", 1),
        new Instruction("BRT", 1),
        new Instruction("BRF", 1),
        new Instruction("ICONST", 1),
        new Instruction("LOAD", 1),
        new Instruction("GLOAD", 1),
        new Instruction("STORE", 1),
        new Instruction("GSTORE", 1),
        new Instruction("PRINT"),
        new Instruction("POP"),
        new Instruction("HALT"),
        
        // java
        new Instruction("NEW", 1),
        new Instruction("INVOKESTATIC", 1),
        new Instruction("INVOKEINTERFACE", 1),
        new Instruction("INVOKESPECIAL", 1),
        new Instruction("INVOKEVIRTUAL", 1),
        new Instruction("DUP")
    };
    
    public static class Instruction {
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
