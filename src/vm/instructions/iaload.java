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
public class iaload extends Instruction {

    @Override
    public void execute(VM vm, String[] args) {
        int index = vm.getStack().popInt();
        int objPointer = vm.getStack().popPointer();

        int value = Utils.getIntArrayValue(vm, objPointer, index);
        System.out.println("Loaded " + value);

        vm.getStack().pushInt(value);
    }
}
