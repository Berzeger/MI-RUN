package vm.tables;

import java.util.ArrayList;
import java.util.List;
import vm.model.VMInstruction;

/**
 *
 * @author Filip
 */
public class InstructionsTable {
    public final List<VMInstruction> instructions;
    private int programCounter;
    private int instructionCounter;

    /**
     * Constructor
     */
    public InstructionsTable() {
        instructions = new ArrayList<>();
        programCounter = 0;
        instructionCounter = 0;
    }

    public int addInstruction(VMInstruction instruction) {
        instructions.add(instruction);
        return instructionCounter++;
    }

    /**
     * returns next instruction
     * @return
     */
    public VMInstruction nextInstruction() {
        return instructions.get(programCounter++);
    }

    /**
     * Jump into specify address
     * @param address
     */
    public void jump(int address) {
        programCounter = address;
    }

    public int getProgramCounter() {
        return programCounter;
    }
    
}
