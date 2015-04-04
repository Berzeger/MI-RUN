/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.model;

import vm.Utils;

/**
 *
 * @author Václav
 */
public class StackFrame {

    private final byte[] content;
    private int offset = 0;
    public VMMethod actualMethod;

    public StackFrame(int contentSize) {

	content = new byte[contentSize];

    }

    public StackFrame(int contentSize, int returnAddress, MethodLookup lookupReturn) {

	this(contentSize);
	actualMethod = lookupReturn.method;

	// push return address
	pushInt(returnAddress);

	// 1 - n (args + other locals)
	offset += (lookupReturn.method.arguments.size() + lookupReturn.method.locals.size()) * FieldType.TYPE_BYTE_SIZE;

	// current frame is the caller frame
	for (int i = 0; i < lookupReturn.args.length; i++) {
	    setLocalValue(1 + i, lookupReturn.args[i]);
	}

    }
    public StackFrame(int contentSize, int returnAddress, MethodLookup lookupReturn, int objectPointer) {

	this(contentSize);
	actualMethod = lookupReturn.method;

	// push return address
	pushInt(returnAddress);

	// push locals
	// 0 - object reference
	pushPointer(objectPointer);

	// 1 - n (args + other locals)
	offset += (lookupReturn.method.arguments.size() + lookupReturn.method.locals.size()) * FieldType.TYPE_BYTE_SIZE;

	// current frame is the caller frame
	for (int i = 0; i < lookupReturn.args.length; i++) {
	    setLocalValue(1 + i, lookupReturn.args[i]);
	}

    }

    public void pushInt(int value) {
	push(Utils.intToByteArray(Utils.createInt(value)));
    }

    public void pushPointer(int pointer) {
	push(Utils.intToByteArray(Utils.createPointer(pointer)));
    }

    public void push(byte[] bytes) {
	for (byte b : bytes) {
	    content[offset++] = b;
	}
    }

    public void setLocalValue(int index, byte[] value) {
	int valueStart = index * FieldType.TYPE_BYTE_SIZE + FieldType.TYPE_BYTE_SIZE;
	for (int i = 0; i < value.length; i++) {
	    content[i + valueStart] = value[i];
	}
    }

}
