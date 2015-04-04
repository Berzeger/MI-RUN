/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Václav
 */
public class VMClass {
    
    public int address;
    public String name;
    public List<VMField> fields = new ArrayList<>();
    public List<VMMethod> methods = new ArrayList<>();
    public VMClass superClass;
    public String superClassName;
    public VMConstantPool constantPool;

}
