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
    public MyMethod actualMethod;

    public StackFrame(int contentSize) {

	content = new byte[contentSize];

    }

    public StackFrame(int contentSize, int returnAddress, MyLookupReturn lookupReturn, int objectPointer) {

	this(contentSize);
	actualMethod = lookupReturn.method;

	// push return address
	pushInt(returnAddress);

	// push locals
	// 0 - object reference
	pushPointer(objectPointer);

	// 1 - n (args + other locals)
	offset += (lookupReturn.method.arguments.length + lookupReturn.method.locals.length) * FieldType.TYPE_BYTE_SIZE;

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

}
