package vm.model;

import java.util.List;

/**
 *
 * @author Václav
 */
public class VMMethod {

    public String name;
    public List<VMField> arguments;
    public List<VMField> locals;
    public FieldType returnType;
    public int instructionPointer;
    public VMClass clazz;
    public boolean isStatic;
    public boolean isNative;
}
