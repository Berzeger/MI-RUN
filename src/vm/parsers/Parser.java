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
public interface Parser {
    boolean canParse(Method method, LocalVariable var, int index);
}
