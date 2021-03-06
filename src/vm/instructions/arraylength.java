/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.instructions;

import vm.Utils;
import vm.VM;

/**
 *
 * @author Filip
 */
public class arraylength extends Instruction {

    @Override
    public void execute(VM vm, String[] args) {
        int objPointer = vm.getStack().popPointer();

        int size = Utils.getArrayLength(vm, objPointer);
        vm.getStack().pushInt(size);
    }

}
