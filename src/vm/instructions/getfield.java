/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.instructions;

import vm.Utils;
import vm.VM;
import vm.model.VMClass;

/**
 *
 * @author Filip
 */
public class getfield extends Instruction {

    @Override
    public void execute(VM vm, String[] args) {
        String fieldName = args[0];
        int objPointer = vm.getStack().popPointer();
        VMClass clazz = vm.getHeap().getObject(objPointer);
        byte[] value = Utils.getField(vm.getHeap().getSpace(), objPointer, clazz.getFieldIndex(fieldName));
        vm.getStack().pushValue(value);
    }

}
