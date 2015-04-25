/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.instructions;

import vm.VM;

/**
 *
 * @author Filip
 */
public class dup extends Instruction {

    @Override
    public void execute(VM vm, String[] args) {
        byte[] value = vm.getStack().popValue();
        vm.getStack().pushValue(value);
        vm.getStack().pushValue(value);
    }

}
