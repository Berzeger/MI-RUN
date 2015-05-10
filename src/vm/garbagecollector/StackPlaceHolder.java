/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.garbagecollector;

import vm.model.StackFrame;

/**
 *
 * @author Filip
 */
public class StackPlaceHolder {

    // On which Stackframe is the object?
    public StackFrame stackFrame;
    
    // Where exactly is the object?
    public int pointerPosition;
    
    // Which object is it?
    public int pointer;
}
