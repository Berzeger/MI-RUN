/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.tables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vm.model.VMClass;

/**
 *
 * @author Filip
 */
public class ClassesTable {
    private final List<VMClass> classList;
    private final Map<String, Integer> handlesMap;
    private int count;
    
    public ClassesTable() {
        classList = new ArrayList<>();
        handlesMap = new HashMap<>();
        count = 0;
    }
    
    public void addClass(VMClass clazz) {
        clazz.handle = count;
        classList.add(clazz.handle, clazz);
        handlesMap.put(clazz.name, count++);
    }
    
    public List<VMClass> getClasses() {
        return classList;
    }
    
    public int getClassHandle(String className) {
        return handlesMap.get(className);
    }
    
    public VMClass getClassByHandle(int handle) {
        VMClass clazz;
        
        try {
            clazz = classList.get(handle);
        } catch (Exception ex) {
            clazz = null;
        }
        return clazz;
    }

    public Map<String, Integer> getClassesHandles() {
        return handlesMap;
    }
    
    public VMClass getClassByName(String name) {
        return getClassByHandle(getClassHandle(name));
    }
}
