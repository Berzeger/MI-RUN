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
    public VMField[] arguments;
    public VMField[] locals;
    public FieldType returnType;
    public int instructionPointer;
    public VMClass classs;
    public boolean isStatic;
    public boolean isNative;
}
