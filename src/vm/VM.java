package vm;

import java.util.ArrayList;
import java.util.List;
import static vm.Bytecode.*;
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

    final int STACK_SIZE = 100;

    Stack stack;

    BytecodeReader bcReader; // byte code reader
    Bytecode bytecode; // bytecode constants etc
    ClassesTable classesTable;
    MethodsTable methodsTable;
    InstructionsTable instructionsTable;
    Heap heap;

    public boolean debug = false;

    public VM() {
        stack = new Stack(256, 256);
        heap = new Heap(this, 1024);
        classesTable = new ClassesTable();
        methodsTable = new MethodsTable();
        instructionsTable = new InstructionsTable();
        bcReader = new BytecodeReader(this);
        bytecode = new Bytecode();
    }
    
    private void printDebugInfo() {
        if (debug) {/*
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
            }*/
        }
    }

    public void run() {
        printDebugInfo();
        VMClass mainClass = getMainClass();
        int address = getHeap().allocClass(mainClass);
        
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
