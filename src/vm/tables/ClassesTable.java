/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.tables;

import java.util.ArrayList;
import java.util.List;
import vm.model.VMClass;

/**
 *
 * @author Filip
 */
public class ClassesTable {
    private final List<VMClass> classList;
    
    public ClassesTable() {
        classList = new ArrayList<>();
    }

    public ClassesTable(List<VMClass> parsedClasses) {
        classList = parsedClasses;
    }
    
    public void addClass(VMClass clazz) {
        classList.add(clazz);
    }
    
    public List<VMClass> getClasses() {
        return classList;
    }
}
