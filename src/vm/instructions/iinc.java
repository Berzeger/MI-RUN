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
public class iinc extends Instruction {

    @Override
    public void execute(VM vm, String[] args) {
        int index = Integer.parseInt(args[0]);
        int operand = vm.getStack().getLocalInt(index);
        operand++;
        vm.getStack().setLocalInt(index, operand);
    }
    
}
