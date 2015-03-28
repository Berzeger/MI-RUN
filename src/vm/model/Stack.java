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
public class Stack {
    
    private final StackFrame[] stackFrames;
    private final int stackFrameSize;
    private int currentFrameIndex = 0;
    
    

    public Stack(int stackFramesCount, int stackFrameSize) {
	this.stackFrames = new StackFrame[stackFramesCount];
	this.stackFrameSize = stackFrameSize;
	
	this.stackFrames[0] = new StackFrame(stackFrameSize);
	
    }
    
    public void beginStackFrame(int returnAddress, MyLookupReturn lr) {
	return beginStackFrame(returnAdress, lr, -1);
    }
    
    public void beginStackFrame(int returnAddress, MyLookupReturn lr, int objectPointer) {
	
	if(objectPointer > -1){
	    StackFrame frame = new StackFrame(getCurrentStackFrame(), stackFrameSize, returnAddress, lr, objectPointer);
	}
	else{
	    StackFrame frame = new StackFrame(getCurrentStackFrame(), stackFrameSize, returnAddress, lr);
	}
        stackFrames[++currentFrameIndex] = frame;
	
    }
    
    
    private StackFrame getCurrentStackFrame() {
        if (currentFrameIndex < 0) {
            return null;
        }
        return stackFrames[currentFrameIndex];
    }
    
   
    
}
