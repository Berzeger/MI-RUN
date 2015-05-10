package vm;

import vm.model.Heap;
import vm.model.Stack;
import vm.model.VMClass;
import vm.model.VMField;
import vm.model.VMInstruction;
import vm.model.VMMethod;
import vm.tables.ClassesTable;
import vm.tables.InstructionsTable;
import vm.tables.MethodsTable;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class VM {

    private final Stack stack;
    private final BytecodeReader bcReader; // byte code reader
    private final Bytecode bytecode; // bytecode constants etc
    private final ClassesTable classesTable;
    private final MethodsTable methodsTable;
    private final InstructionsTable instructionsTable;
    private final Heap heap;

    public boolean debug = false;
    public boolean printInstructions = false;

    public VM() {
        stack = new Stack(64, 1024);
        heap = new Heap(this, 1000000);
        classesTable = new ClassesTable();
        methodsTable = new MethodsTable();
        instructionsTable = new InstructionsTable();
        bcReader = new BytecodeReader(this);
        bytecode = new Bytecode();
    }

    private void printDebugInfo() {
        if (debug) {
            for (VMClass clazz : classesTable.getClasses()) {
                System.out.println("Class name: " + clazz.name + ", superclass: " + clazz.superClassName);

                for (VMMethod method : clazz.methods) {
                    System.out.println("\tMethod name: " + method.name + ", return type: " + method.returnType);

                    for (VMField argument : method.arguments) {
                        System.out.println("\t\tArgument: " + argument.name + ", argument type: " + argument.type);
                    }
                    for (VMField local : method.locals) {
                        System.out.println("\t\tLocal: " + local.name + ", local type: " + local.type);
                    }
                }

                System.out.println("\tConstant Pool:");

                for (int i = 0; i < clazz.constantPool.size(); i++) {
                    try {
                        // Maybe we don't have to parseInt?
                        System.out.println("\t\tcPoolItem type: " + clazz.constantPool.getItem(i).getType() + ", cPoolItem value: " + Integer.parseInt(clazz.constantPool.getItem(i).getValue()));
                    } catch (Exception ex) {
                        System.out.println("\t\tcPoolItem type: " + clazz.constantPool.getItem(i).getType() + ", cPoolItem value: " + clazz.constantPool.getItem(i).getValue());
                    }
                }
            }

        }
    }

    public void run() {
        printDebugInfo();
        VMClass mainClass = getMainClass();
        int address = getHeap().allocClass(mainClass);
        int startAddress = getInstructionsTable().addInstruction(new VMInstruction("apush", new String[]{String.valueOf(address)}));

        getInstructionsTable().addInstruction(new VMInstruction("invokestatic", new String[]{"::main::1"}));
        getInstructionsTable().addInstruction(new VMInstruction("apush", new String[]{"0"}));
        getInstructionsTable().jump(startAddress);

        VMInstruction instruction;
        while ((instruction = getInstructionsTable().nextInstruction()) != null) {
            InstructionProcessor.execute(this, instruction);
        }
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

    public MethodsTable getMethodsTable() {
        return methodsTable;
    }

    public InstructionsTable getInstructionsTable() {
        return instructionsTable;
    }

    private VMClass getMainClass() {
        for (VMMethod method : getMethodsTable().getMethods()) {
            if (method.isStatic && method.name.equalsIgnoreCase("main") && !method.clazz.name.startsWith("java.")) {
                return method.clazz;
            }
        }

        throw new RuntimeException("Main class not found.");
    }
}
