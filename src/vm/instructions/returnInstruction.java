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
public class returnInstruction extends Instruction {

    @Override
    public void execute(VM vm, String[] args) {
        vm.getInstructionsTable().jump(vm.getStack().deleteStackFrame());
    }
    
}
