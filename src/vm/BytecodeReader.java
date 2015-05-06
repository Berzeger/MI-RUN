package vm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantInteger;
import org.apache.bcel.classfile.ConstantString;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.LocalVariable;
import org.apache.bcel.classfile.LocalVariableTable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.BranchInstruction;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.Type;
import static org.apache.bcel.generic.Type.INT;
import static org.apache.bcel.generic.Type.VOID;
import org.apache.commons.io.FilenameUtils;
import vm.model.FieldType;
import vm.model.VMCPoolItem;
import vm.model.VMCPoolItem.CPType;
import vm.model.VMClass;
import vm.model.VMConstantPool;
import vm.model.VMField;
import vm.model.VMInstruction;
import vm.model.VMMethod;
import vm.parsers.ArgumentParser;
import vm.parsers.LocalVariableParser;
import vm.parsers.Parser;
import vm.tables.InstructionsTable;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class BytecodeReader {

    /**
     * Class file contents: Magic number CAFEBABE (4 bytes) Class file minor
     * version (2 bytes) Class file major version (2 bytes) Constant pool count
     * (2 bytes) - note: This count is at least one greater than the actual
     * number of entries. Constant pool table (variable size) - note: Indexed
     * starting at 1. Access flags, a bitmask (2 bytes) This class identifier (2
     * bytes) Super class identifier (2 bytes) Interface count (2 bytes)
     * Interface table (variable size) Field count (2 bytes) Field table
     * (variable size) Method count (2 bytes) Method table (variable size)
     * Attribute count (2 bytes) Attribute table (variable size)
     */
    private ClassParser cParser;
    private JavaClass bcelClass;
    private final VM virtualMachine;

    /**
     * pattern for instructions with parameters
     */
    private static final Pattern insPattern = Pattern.compile(".*\\[\\d+\\]\\(\\d+\\)\\s(\\w+)+");  // For example: invokestatic[184](3) 5

    public BytecodeReader(VM virtualMachine) {
        this.virtualMachine = virtualMachine;
        try {
            // Java classes
            // For each file in directory
            Files.walk(Paths.get("./lib/java")).forEach(filePath -> {
                // Is it a class file?
                if (FilenameUtils.getExtension(filePath.toString()).equals("class")) {
                    parseClass(filePath.toString());
                }
            });

            // Custom classes
            // For each file in directory
            Files.walk(Paths.get("./lib/custom")).forEach(filePath -> {
                // Is it a class file?
                if (FilenameUtils.getExtension(filePath.toString()).equals("class")) {
                    parseClass(filePath.toString());
                }
            });

            loadSuperClasses();

            // I'll keep this here just for future references
            // as how to read method bytecode
            /*
             for (Method method : bcelClass.getMethods()) {
             System.out.println(method);
             //System.out.println(-79 & 0xFF);
             // TODO: Figure out why there are negative integers in the code
             System.out.println(Arrays.toString(method.getCode().getCode()));
   
             // Prints out one byte
             System.out.println(method.getCode().getCode()[0]);
             }
             */
        } catch (IOException ex) {
            System.err.println("Could not locate or read class file.");
        }
    }

    private FieldType translateType(Type type) {
        if (type == INT) {
            return FieldType.INT;
        } else if (type == VOID) {
            return FieldType.VOID;
        }

        return FieldType.POINTER;
    }

    private FieldType getVariableType(LocalVariable var) {
        String signature = var.getSignature();
        if (signature.equalsIgnoreCase("I")) {
            return FieldType.INT;
        } else {
            return FieldType.POINTER;
        }
    }

    private FieldType getArgumentTypeType(Type type) {
        String signature = type.getSignature();
        if (signature.equalsIgnoreCase("int")) {
            return FieldType.INT;
        } else {
            return FieldType.POINTER;
        }
    }

    private void parseClass(String filePath) {
        VMClass classToSave = new VMClass();

        try {
            cParser = new ClassParser(filePath);
            bcelClass = cParser.parse();

            classToSave.name = bcelClass.getClassName();
            classToSave.fields = new ArrayList<>();
            classToSave.methods = new ArrayList<>();

            for (Field field : bcelClass.getFields()) {
                classToSave.fields.add(parseField(field));
            }

            classToSave.superClassName = bcelClass.getSuperclassName();
            //classToSave.constantPool = parseConstantPool(bcelClass);
            classToSave.constantPool = new VMConstantPool();

            for (Method method : bcelClass.getMethods()) {
                VMMethod meth = parseMethod(method, classToSave);
                classToSave.methods.add(meth);
                virtualMachine.getMethodsTable().getMethods().add(meth);
            }

            virtualMachine.getClassesTable().addClass(classToSave);
        } catch (IOException ex) {
            Logger.getLogger(BytecodeReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private VMField parseField(Field field) {
        return new VMField(
                field.getName(),
                translateType(field.getType()),
                getClass(field.getType().getSignature())
        );
    }

    private VMMethod parseMethod(Method method, VMClass clazz) {
        VMMethod meth = new VMMethod();
        meth.name = method.getName();
        meth.arguments = parseVariables(method, new ArgumentParser());
        meth.locals = parseVariables(method, new LocalVariableParser());
        meth.returnType = translateType(method.getReturnType());
        meth.instructionPointer = parseInstructions(method, clazz, bcelClass);
        meth.clazz = clazz;
        meth.isStatic = method.isStatic();
        meth.isNative = method.isNative();
        return meth;
    }

    private List<VMField> parseVariables(Method method, Parser parser) {
        List<VMField> variables = new ArrayList<>();
        LocalVariableTable varTable = method.getLocalVariableTable();

        if (varTable != null) {
            for (int i = 0; i < varTable.getTableLength(); i++) {
                LocalVariable var = varTable.getLocalVariable(i);
                if (!parser.canParse(method, var, i)) {
                    continue;
                }
                variables.add(new VMField(var.getName(), getVariableType(var), getClass(var.getSignature())));
            }
        } else if (method.getName().equalsIgnoreCase("<init>") || method.isNative()) {
            Type[] types = method.getArgumentTypes();
            for (Type type : types) {
                variables.add(new VMField("wtf", getArgumentTypeType(type), getClass(type.getSignature())));
            }
        }

        return variables;
    }

    private String getClass(String signature) {
        switch (signature) {
            case "I":
                return "Integer";
            case "[B":
            case "[I":
                return "java.lang.Array";
            default:
                signature = signature.replace("[L", "").replace(";", "").replace("/", ".");

                if (signature.startsWith("L")) {
                    signature = signature.substring(1);
                }

                return signature;
        }
    }

    private VMConstantPool parseConstantPool(JavaClass clazz) {
        VMConstantPool cPool = new VMConstantPool();

        for (int i = 0; i < clazz.getConstantPool().getLength(); i++) {
            Constant cPoolConstant = clazz.getConstantPool().getConstant(i);

            if (cPoolConstant instanceof ConstantString) {
                String value = (((ConstantString) cPoolConstant).getBytes(clazz.getConstantPool()));
                cPool.addItem(new VMCPoolItem(CPType.STRING, value));
                // Does the following else if ever trigger? Doesn't seem to.
                // Or maybe it does. Sometimes. WTF. Gotta read something
                // 'bout saving int constants. When does it do that? 
            } else if (cPoolConstant instanceof ConstantInteger) {
                int value = ((ConstantInteger) cPoolConstant).getBytes();
                cPool.addItem(new VMCPoolItem(CPType.INT, Integer.toString(value)));
            }
        }

        return cPool;
    }

    private int parseInstructions(Method method, VMClass clazz, JavaClass jc) {
        int instructionPointer = -1;
        if (method.getCode() != null) {
            org.apache.bcel.generic.InstructionList instructions = new org.apache.bcel.generic.InstructionList(method.getCode().getCode());

            for (InstructionHandle ih : instructions.getInstructionHandles()) {

                VMInstruction instruction = new VMInstruction();
                List<String> insParams = new LinkedList<>();
                Instruction i = ih.getInstruction();
                if (i instanceof BranchInstruction) {
                    InstructionHandle targetIh = ((BranchInstruction) i).getTarget();
                    insParams.add(String.valueOf(targetIh.getPosition()));
                    instruction.isBranchInstruction = true;
                } else {
                    Matcher matcher = insPattern.matcher(i.toString(true));
                    if (matcher.find()) {
                        for (int index = 1; index <= matcher.groupCount(); index++) {
                            insParams.add(matcher.group(index));
                        }
                    }
                }
                instruction.name = i.getName();
                instruction.args = insParams.toArray(new String[insParams.size()]);
                instruction.originPosition = ih.getPosition();

                ConstantPoolTranslator.translateInstruction(instruction, clazz, jc);
                int position = virtualMachine.getInstructionsTable().addInstruction(instruction);
                if (instructionPointer < 0) {
                    instructionPointer = position;
                }
            }
        }

        if (instructionPointer != -1) {
            translateBranchPointers(instructionPointer, virtualMachine.getInstructionsTable());
        }

        return instructionPointer;
    }

    private void loadSuperClasses() {
        for (VMClass clazz : virtualMachine.getClassesTable().getClasses()) {
            if (!clazz.name.equals(clazz.superClassName)) {
                int superClassHandle = virtualMachine.getClassesTable().getClassHandle(clazz.superClassName);
                VMClass superclass = virtualMachine.getClassesTable().getClassByHandle(superClassHandle);
                clazz.superVMClass = superclass;
            }
        }
    }

    private void translateBranchPointers(int fromInstructionPointer, InstructionsTable instructionList) {
        for (int i = fromInstructionPointer; i < instructionList.instructions.size(); i++) {
            VMInstruction instruction = instructionList.instructions.get(i);
            if (instruction.isBranchInstruction) {
                int param = Integer.parseInt(instruction.args[0]);
                int newPointer = findInstructionPositionByOriginPointer(param, fromInstructionPointer, instructionList);
                if (newPointer != -1) {
                    instruction.args[0] = String.valueOf(newPointer);
                }
            }
        }
    }

    private int findInstructionPositionByOriginPointer(int originPointer, int fromInstructionPointer, InstructionsTable instructionList) {
        for (int i = fromInstructionPointer; i < instructionList.instructions.size(); i++) {
            VMInstruction instruction = instructionList.instructions.get(i);
            if (instruction.originPosition == originPointer) {
                return i;
            }
        }
        return -1;
    }
}
