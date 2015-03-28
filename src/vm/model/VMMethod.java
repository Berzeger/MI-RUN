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
public class VMMethod {

    public String name;
    public MyField[] arguments;
    public MyField[] locals;
    public FieldType returnType;
    public int instructionPointer;
    public MyClass classs;
    public boolean isStatic;
    public boolean isNative;
}
