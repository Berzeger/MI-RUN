/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm;

import vm.model.FieldType;
import vm.model.Heap;

/**
 *
 * @author Václav
 */
public class Utils {

    public static int createFieldType(int value, int t) {
        return (value << 1) | t;
    }

    public static int createPointer(int value) {
        return createFieldType(value, 1);
    }

    public static int createInt(int value) {
        return createFieldType(value, 0);
    }

    public static int fieldTypeToInt(int value) {
        return value >> 1;
    }

    public static boolean isPointer(byte code) {
        return ((code & 0x01) == 1);
    }

    public static final byte[] intToByteArray(int value) {
        return new byte[]{(byte) (value >> 24), (byte) (value >> 16), (byte) (value >> 8), (byte) value};
    }

    public static int byteArrayToInt(byte[] bytes, int from) {
        return bytes[from] << 24 | (bytes[from + 1] & 0xFF) << 16 | (bytes[from + 2] & 0xFF) << 8 | (bytes[from + 3] & 0xFF);
    }

    public static byte[] getValue(byte[] where, int from) {
        byte[] value = new byte[FieldType.TYPE_BYTE_SIZE];
        for (int i = 0; i < value.length; i++) {
            value[i] = where[i + from];
        }
        return value;
    }

    public static byte[] getField(byte[] where, int objectAddress, int fieldIndex) {
        byte[] value = new byte[FieldType.TYPE_BYTE_SIZE];
        int start = fieldIndex * FieldType.TYPE_BYTE_SIZE + objectAddress + Heap.OBJECT_HEADER_SIZE;

        System.arraycopy(where, start, value, 0, FieldType.TYPE_BYTE_SIZE);
        return value;
    }

    public static void setField(byte[] where, int objectAddress, int fieldIndex, byte[] value) {
        // field offset + object offset + object header
        int start = fieldIndex * FieldType.TYPE_BYTE_SIZE + objectAddress + Heap.OBJECT_HEADER_SIZE;

        // copy field value to heap
        System.arraycopy(value, 0, where, objectAddress, value.length);
    }

    // do we need this?
    public static int getIntField(byte[] where, int objectAddress, int fieldIndex) {
        byte[] value = getField(where, objectAddress, fieldIndex);
        return byteArrayToInt(value, 0);
    }

    public static void setIntField(byte[] where, int objectAddress, int fieldIndex, int value) {
        // na createInt a createPointer se musíme zeptat Jirky ještě
        byte[] byteValue = intToByteArray(createInt(value));
        setField(where, objectAddress, fieldIndex, byteValue);
    }

    public static void setPointerField(byte[] where, int objectAddress, int fieldIndex, int value) {
        byte[] byteValue = intToByteArray(createPointer(value));
        setField(where, objectAddress, fieldIndex, byteValue);
    }

    public static byte[] subArray(byte[] where, int from, int size) {
        byte[] val = new byte[size];
        for (int i = 0; i < size; i++) {
            val[i] = where[from + i];
        }
        return val;
    }
    
    public static String getStringValue(VM virtualMachine, int pointer) {
        return new String(getStringBytes(virtualMachine, pointer));
    }

    public static byte[] getStringBytes(VM virtualMachine, int pointer) {
        byte[] field = Utils.getField(virtualMachine.getHeap().getSpace(), pointer, 0);
        // how many bytes there are in the strings
        int byteCount = Utils.fieldTypeToInt(Utils.byteArrayToInt(field, 0));
        
        // where to start reading the string
        int start = Utils.fieldTypeToInt(Utils.byteArrayToInt(Utils.getField(virtualMachine.getHeap().getSpace(), pointer, 1), 0));

        start += Heap.OBJECT_HEADER_SIZE;

        return Utils.subArray(virtualMachine.getHeap().getSpace(), start, byteCount);
    }
}
