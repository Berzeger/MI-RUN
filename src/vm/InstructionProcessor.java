/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm;

import vm.instructions.Instruction;
import vm.model.VMInstruction;

/**
 *
 * @author Filip
 */
public class InstructionProcessor {

    public static void execute(VM virtualMachine, VMInstruction instruction) {
        if (virtualMachine.debug) {
            System.out.println("Instruction [" + instruction.originPosition + "]: " + instruction.toString());
        }

        Instruction inst = Bytecode.instructions.get(instruction.getName());

        if (inst == null) {
            System.err.println("Unknown instruction: " + instruction.toString());
        }
        
        // TODO: Check for null pointer somewhere else
        if (inst != null) {
            inst.execute(virtualMachine, instruction.getArgs());
        }
    }
}
