/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.tables;

import java.util.ArrayList;
import java.util.List;
import vm.model.VMMethod;

/**
 *
 * @author Filip
 */
public class MethodsTable {
    private final List<VMMethod> methodsList;
    
    public MethodsTable() {
        methodsList = new ArrayList<>();
    }
    
    public void addMethod(VMMethod method) {
        methodsList.add(method);
    }
    
    public List<VMMethod> getMethods() {
        return methodsList;
    }
}
