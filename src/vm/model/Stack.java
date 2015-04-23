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

    public void beginStackFrame(int returnAddress, MethodLookup lr) {
	beginStackFrame(returnAddress, lr, -1);
    }

    public void beginStackFrame(int returnAddress, MethodLookup lr, int objectPointer) {
	StackFrame frame;

	if (objectPointer > -1) {
	    frame = new StackFrame(stackFrameSize, returnAddress, lr, objectPointer);
	} else {
	    frame = new StackFrame(stackFrameSize, returnAddress, lr);
	}
	stackFrames[++currentFrameIndex] = frame;
    }
    
    public int popInt() {
        return getCurrentStackFrame().popInt();
    }
    
    public void pushInt(int value) {
        getCurrentStackFrame().pushInt(value);
    }
    
    public int popPointer() {
        return popInt();
    }
    
    public void pushPointer(int value) {
        getCurrentStackFrame().pushPointer(value);
    }
    
    public void setLocalValue(int index, byte[] value) {
        getCurrentStackFrame().setLocalValue(index, value);
    }
    
    public void setLocalInt(int index, int value) {
        getCurrentStackFrame().setLocalInt(index, value);
    }
    
    public void setLocalPointer(int index, int value) {
        getCurrentStackFrame().setLocalPointer(index, value);
    }
    
    public byte[] getLocalValue(int index) {
        return getCurrentStackFrame().getLocalValue(index);
    }
    
    public int getLocalInt(int index) {
        return getCurrentStackFrame().getLocalInt(index);
    }
    
    public int getLocalPointer(int index) {
        return getCurrentStackFrame().getLocalPointer(index);
    }

    private StackFrame getCurrentStackFrame() {
	if (currentFrameIndex < 0) {
	    return null;
	}
	return stackFrames[currentFrameIndex];
    }    
}
