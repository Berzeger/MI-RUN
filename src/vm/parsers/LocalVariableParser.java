/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.parsers;

import org.apache.bcel.classfile.LocalVariable;
import org.apache.bcel.classfile.Method;

/**
 *
 * @author Filip
 */
public class LocalVariableParser implements Parser {

    @Override
    public boolean canParse(Method method, LocalVariable var, int index) {
        if (var == null) {
            return false;
        }

        if (var.getStartPC() == 0) {
            return false;
        } else {
            return true;
        }
    }

}
