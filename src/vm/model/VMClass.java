/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.model;

/**
 *
 * @author Václav
 */
public class VMClass {
    
    public int address;
    public String name;
    public VMField[] fields;
    public VMMethod[] methods;
    public VMClass superClass;
    public String superClassName;
    public VMConstantPool constantPool;

}
