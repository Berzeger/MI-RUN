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
public class VMMethod {

    public String name;
    public List<VMField> arguments = new ArrayList<>();
    public List<VMField> locals = new ArrayList<>();
    public FieldType returnType;
    public int instructionPointer;
    public VMClass clazz;
    public boolean isStatic;
    public boolean isNative;
}
