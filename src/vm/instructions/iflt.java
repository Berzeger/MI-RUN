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
public class iflt extends Instruction {

    @Override
    public void execute(VM vm, String[] args) {
        int value = vm.getStack().popInt();
        if (value < 0) {
            vm.getInstructionsTable().jump(Integer.parseInt(args[0]));
        }
    }
}
