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
    private int pointer = 0;
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
        pointer += (lookupReturn.method.arguments.size() + lookupReturn.method.locals.size()) * FieldType.TYPE_BYTE_SIZE;

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
        pointer += (lookupReturn.method.arguments.size() + lookupReturn.method.locals.size()) * FieldType.TYPE_BYTE_SIZE;

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
            content[pointer++] = b;
        }
    }

    public void setLocalInt(int index, int value) {
        setLocalValue(index, Utils.intToByteArray(Utils.createInt(value)));
    }
    
    public void setLocalValue(int index, byte[] value) {
        int valueStart = index * FieldType.TYPE_BYTE_SIZE + FieldType.TYPE_BYTE_SIZE;
        System.arraycopy(value, 0, content, valueStart, value.length);
    }

    public byte[] getLocalValue(int index) {
        return Utils.subArray(content, index * FieldType.TYPE_BYTE_SIZE + FieldType.TYPE_BYTE_SIZE, FieldType.TYPE_BYTE_SIZE);
    }
    
    public int getLocalInt(int index) {
        return Utils.fieldTypeToInt(Utils.byteArrayToInt(content, index * FieldType.TYPE_BYTE_SIZE + FieldType.TYPE_BYTE_SIZE));
    }

    private byte[] pop() {
        pointer -= 4; // constant - fix it
        return Utils.getValue(content, pointer);
    }

    public int popInt() {
        return Utils.fieldTypeToInt(Utils.byteArrayToInt(pop(), 0));
    }

    public int popPointer() {
        return popInt();
    }

    void setLocalPointer(int index, int value) {
        setLocalValue(index, Utils.intToByteArray(Utils.createPointer(value)));
    }

    int getLocalPointer(int index) {
        return getLocalInt(index);
    }
}
