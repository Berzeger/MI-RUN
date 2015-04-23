package vm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantCP;
import org.apache.bcel.classfile.ConstantClass;
import org.apache.bcel.classfile.ConstantFieldref;
import org.apache.bcel.classfile.ConstantInteger;
import org.apache.bcel.classfile.ConstantNameAndType;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.ConstantString;
import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.classfile.JavaClass;
import vm.model.VMCPoolItem;
import vm.model.VMClass;
import vm.model.VMInstruction;

/**
 *
 * @author Filip
 */
public class ConstantPoolTranslator {

    private static final Pattern insPattern = Pattern.compile("L([a-zA-Z\\/]+);|(\\[B)|(I)");

    public static void translateInstruction(VMInstruction instruction, VMClass clazz, JavaClass jc) {
        ConstantPool constantPool = jc.getConstantPool();

        //System.out.println("Before translation: " + instruction.toString());
        
        if (instruction.getName().startsWith("invoke")) {
            translateInvokeMethod(instruction, jc);
        } else if (instruction.getName().equalsIgnoreCase("new")) {
            translateNew(instruction, constantPool);
        } else if (instruction.getName().equalsIgnoreCase("getstatic")
                || instruction.getName().equalsIgnoreCase("putstatic")) {
            translateStaticField(instruction, constantPool);
        } else if (instruction.getName().equalsIgnoreCase("putfield")
                || instruction.getName().equalsIgnoreCase("getfield")) {
            translateField(instruction, constantPool);
        } else if (instruction.getName().equalsIgnoreCase("ldc")) {
            translateCPoolInstruction(instruction, clazz, constantPool);
        }
        
        //System.out.println("After translation: " + instruction.toString());
    }

    private static void translateCPoolInstruction(VMInstruction instruction, VMClass clazz, ConstantPool cPool) {
        int pos = Integer.parseInt(instruction.getArgs()[0]);
        Constant constant = cPool.getConstant(pos);
        if (constant instanceof ConstantString) {
            String value = cPool.getConstantString(pos, constant.getTag());
            int item = clazz.constantPool.addItem(new VMCPoolItem(VMCPoolItem.CPType.STRING, value));

            instruction.getArgs()[0] = Integer.toString(item);
        } else if (constant instanceof ConstantInteger) {
            int value = ((ConstantInteger) constant).getBytes();
            int item = clazz.constantPool.addItem(new VMCPoolItem(VMCPoolItem.CPType.INT, Integer.toString(value)));

            instruction.getArgs()[0] = Integer.toString(item);
        }
    }

    private static void translateField(VMInstruction instruction, ConstantPool cPool) {
        int fieldIndex = Integer.parseInt(instruction.getArgs()[0]);
        String fieldName = getFieldName(fieldIndex, cPool);
        instruction.getArgs()[0] = fieldName;
    }

    private static void translateStaticField(VMInstruction instruction, ConstantPool cPool) {
        int fieldIndex = Integer.parseInt(instruction.getArgs()[0]);
        ConstantFieldref fieldRef = (ConstantFieldref) cPool.getConstant(fieldIndex);
        String fieldName = getClassName(fieldRef.getClassIndex(), cPool) + "#" + getFieldName(fieldIndex, cPool);
        instruction.getArgs()[0] = fieldName;
    }

    private static void translateNew(VMInstruction instruction, ConstantPool cPool) {
        int classIndex = Integer.parseInt(instruction.getArgs()[0]);
        instruction.getArgs()[0] = getClassName(classIndex, cPool);
    }

    private static void translateInvokeMethod(VMInstruction instruction, JavaClass jc) {
        org.apache.bcel.classfile.ConstantPool constantPool = jc.getConstantPool();
        String instructionClassName = jc.getClassName();

        int methodIndex = Integer.parseInt(instruction.getArgs()[0]);
        String className = getClassNameForMethod(methodIndex, constantPool);
        String methodName = getMethodName(methodIndex, constantPool);
        String argsNumber = getNumberOfMethodArguments(methodIndex, constantPool);

        if (className.equalsIgnoreCase(instructionClassName)) {
            className = "";
        }
        instruction.getArgs()[0] = className + "::" + methodName + "::" + argsNumber;
    }

    public static int getNameAndTypeIndex(int methodIndex, ConstantPool constantPool) {
        int nameAndTypeIndex;
        ConstantCP constant = (ConstantCP) constantPool.getConstant(methodIndex);
        nameAndTypeIndex = constant.getNameAndTypeIndex();
        return nameAndTypeIndex;
    }

    private static String getMethodName(int methodIndex, ConstantPool constantPool) {
        int nameAndTypeIndex = getNameAndTypeIndex(methodIndex, constantPool);
        ConstantNameAndType nameAndType = (ConstantNameAndType) constantPool.getConstant(nameAndTypeIndex);
        ConstantUtf8 utf8 = (ConstantUtf8) constantPool.getConstant(nameAndType.getNameIndex());
        String name = utf8.getBytes();
        return name;
    }

    private static String getFieldName(int fieldIndex, ConstantPool constantPool) {
        ConstantFieldref fieldRef = (ConstantFieldref) constantPool.getConstant(fieldIndex);
        ConstantNameAndType nameAndType = (ConstantNameAndType) constantPool.getConstant(fieldRef.getNameAndTypeIndex());
        ConstantUtf8 utf8 = (ConstantUtf8) constantPool.getConstant(nameAndType.getNameIndex());
        String name = utf8.getBytes();
        return name;
    }

    private static String getClassName(int classIndex, ConstantPool constantPool) {
        ConstantClass constantClass = (ConstantClass) constantPool.getConstant(classIndex);
        ConstantUtf8 utf8 = (ConstantUtf8) constantPool.getConstant(constantClass.getNameIndex());
        String name = utf8.getBytes();
        name = name.replaceAll("/", ".");
        return name;
    }

    private static String getClassNameForMethod(int methodIndex, ConstantPool constantPool) {
        ConstantCP constant = (ConstantCP) constantPool.getConstant(methodIndex);
        String name = getClassName(constant.getClassIndex(), constantPool);
        return name;
    }

    private static String getNumberOfMethodArguments(int methodIndex, ConstantPool constantPool) {
        int nameAndTypeIndex = getNameAndTypeIndex(methodIndex, constantPool);
        ConstantNameAndType nameAndType = (ConstantNameAndType) constantPool.getConstant(nameAndTypeIndex);
        ConstantUtf8 signature = (ConstantUtf8) constantPool.getConstant(nameAndType.getSignatureIndex());
        return parseNumberOfMethodArguments(signature.getBytes());
    }

    private static String parseNumberOfMethodArguments(String signature) {
        int pos = signature.indexOf(")");
        signature = signature.substring(0, pos);
        signature = signature.replace("(", "");
        Matcher matcher = insPattern.matcher(signature);

        int count = 0;
        while (matcher.find()) {
            signature = matcher.replaceFirst("");
            matcher = insPattern.matcher(signature);
            count++;
        }
        return Integer.toString(count);
    }
}
