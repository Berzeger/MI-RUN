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
public class aload extends Instruction {

    private final int arg;
    
    public aload() {
        arg = -1;
    }
    
    public aload(int arg) {
        this.arg = arg;
    }
    
    @Override
    public void execute(VM vm, String[] args) {
        int operand;
        
        if (arg == -1) {
            operand = vm.getStack().getLocalPointer(Integer.parseInt(args[0]));
        } else {
            operand = vm.getStack().getLocalPointer(arg);
        }
        
        vm.getStack().pushPointer(operand);
    }  
}
