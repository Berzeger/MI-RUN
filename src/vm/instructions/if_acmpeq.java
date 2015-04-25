/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.instructions;

import vm.VM;

/**
 * if_acmpeq: If references are equal.
 * @author Filip
 */
public class if_acmpeq extends Instruction {

    @Override
    public void execute(VM vm, String[] args) {
        int ref1 = vm.getStack().popPointer();
        int ref2 = vm.getStack().popPointer();
        
        if (ref1 == ref2) {
            vm.getInstructionsTable().jump(Integer.parseInt(args[0]));
        }
    }
    
}
