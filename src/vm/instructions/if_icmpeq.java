/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.instructions;

import vm.VM;

/**
 * if_acmpeq: If numbers are equal.
 * @author Filip
 */
public class if_icmpeq extends Instruction {

    @Override
    public void execute(VM vm, String[] args) {
        int number1 = vm.getStack().popInt();
        int number2 = vm.getStack().popInt();
        
        if (number1 == number2) {
            vm.getInstructionsTable().jump(Integer.parseInt(args[0]));
        }
    }
}
