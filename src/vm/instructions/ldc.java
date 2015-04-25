/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.instructions;

import vm.VM;
import vm.model.VMCPoolItem;
import vm.model.VMClass;

/**
 *
 * @author Filip
 */
public class ldc extends Instruction {

    @Override
    public void execute(VM vm, String[] args) {
        VMClass clazz = vm.getStack().getCurrentStackFrame().actualMethod.clazz;
        int arg = Integer.parseInt(args[0]);
        
        VMCPoolItem item = clazz.constantPool.getItem(arg);
        if (item.getType() == VMCPoolItem.CPType.INT) {
            vm.getStack().pushInt(Integer.parseInt(item.getValue()));
        } else if (item.getType() == VMCPoolItem.CPType.STRING) {
            // TODO: New string instruction
        }
    }
    
}
