/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.model;

import java.util.Arrays;

/**
 *
 * @author Filip
 */
public class VMInstruction {
    public String name;
    public String[] args;
    public int originPosition;
    public boolean isBranchInstruction = false;

    public VMInstruction() {}
    
    public VMInstruction(String name, String[] args) {
        this.name = name;
        this.args = args;
    }

    public String getName() {
        return name;
    }

    public String[] getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return name + ((args.length > 0) ? " " + Arrays.toString(args) : "");
    }   
}
