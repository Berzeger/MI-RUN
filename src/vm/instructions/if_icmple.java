/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.instructions;

import vm.VM;

/**
 * if number is less or equal than another
 * @author Filip
 */
public class if_icmple extends Instruction {

    @Override
    public void execute(VM vm, String[] args) {
        int number1 = vm.getStack().popInt();
        int number2 = vm.getStack().popInt();
        
        if (number2 <= number1) {
            vm.getInstructionsTable().jump(Integer.parseInt(args[0]));
        }
    }
      
}
