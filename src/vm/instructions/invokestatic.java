package vm.instructions;

import vm.Utils;
import vm.VM;
import vm.model.FieldType;
import vm.model.MethodLookup;
import vm.model.VMClass;
import vm.model.VMMethod;

/**
 *
 * @author Filip
 */
public class invokestatic extends Instruction {

    @Override
    public void execute(VM vm, String[] args) {
        String descriptor = args[0];

        String superClassName = VMClass.getMethodClassName(descriptor);
        String methodName = VMClass.getMethodName(descriptor);
        int argsCount = VMClass.getMethodArgsCount(descriptor);

        byte[][] argsArray = new byte[argsCount][FieldType.TYPE_BYTE_SIZE];
        // it's a stack, so pop it in reverse order
        for (int i = argsCount - 1; i >= 0; i--) {
            argsArray[i] = vm.getStack().popValue();
        }

        VMMethod method = vm.getMethodsTable().getMethodByName(methodName);
        VMClass clazz = method.clazz;
        MethodLookup lookup = clazz.lookupMethod(superClassName, methodName, vm, argsArray);

        if (lookup.method.isNative) {
            if (lookup.method.name.equals("println")) {
                if (lookup.method.arguments.get(0).className.equals("Integer")) {
                    vm.getStack().pushValue(lookup.args[0]);
                    System.out.println(vm.getStack().popInt());
                } else { // String
                    int pointer = Utils.fieldTypeToInt(Utils.byteArrayToInt(argsArray[0], 0));

                    String value = Utils.getStringValue(vm, pointer);
                    System.out.println(value);
                }
            }
        } else {
            vm.getStack().beginStackFrame(vm.getInstructionsTable().getProgramCounter(), lookup);
            vm.getInstructionsTable().jump(lookup.method.instructionPointer);
        }
    }

}
