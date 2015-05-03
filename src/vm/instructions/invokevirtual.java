package vm.instructions;

import vm.VM;
import vm.model.FieldType;
import vm.model.MethodLookup;
import vm.model.VMClass;

/**
 *
 * @author Filip
 */
public class invokevirtual extends Instruction {

    @Override
    public void execute(VM vm, String[] args) {
        String descriptor = args[0];
        
        String className = VMClass.getMethodClassName(descriptor);
        String methodName = VMClass.getMethodName(descriptor);
        int argsCount = VMClass.getMethodArgsCount(descriptor);
        
        byte[][] argsArray = new byte[argsCount][FieldType.TYPE_BYTE_SIZE];
        // it's a stack, so pop it in reverse order
        for (int i = argsCount - 1; i >= 0; i--) {
            argsArray[i] = vm.getStack().popValue();
        }
        
        int objectPointer = vm.getStack().popPointer();
        
        VMClass clazz = vm.getHeap().getObject(objectPointer);
        MethodLookup lookup = clazz.lookupMethod(className, methodName, vm, argsArray, true);
        
        vm.getStack().beginStackFrame(vm.getInstructionsTable().getProgramCounter(), lookup, objectPointer);
        vm.getInstructionsTable().jump(lookup.method.instructionPointer);        
    }
    
}
