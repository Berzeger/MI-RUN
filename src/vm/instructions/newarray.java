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
public class newarray extends Instruction {

    @Override
    public void execute(VM vm, String[] args) {
        int size;
        size = getSize(args, 0);
        if (size < 0) {
            size = vm.getStack().popInt();
        }
        int pointer = vm.getHeap().allocByteArray(size);
        vm.getStack().pushPointer(pointer);
    }

    public static int getSize(String params[], int start) {
        if (start >= params.length) {
            return -1;
        }
        try {
            return Integer.parseInt(params[start]);
        } catch (Exception e) {
            return getSize(params, start + 1);
        }
    }

}
