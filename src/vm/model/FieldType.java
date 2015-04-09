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
public enum FieldType {

    INT(4, (byte) 0),
    POINTER(4, (byte) 1),
    NULL(4, (byte) 2),
    VOID(0, (byte) 3);

    public static int TYPE_BYTE_SIZE = 4;

    private int size;
    private byte identByte;

    private FieldType(int size, byte identByte) {
	this.size = size;
	this.identByte = identByte;
    }

    public boolean isPointer() {
	return identByte == 1;
    }

    public int getSize() {
	return size;
    }

    public byte getIdentByte() {
	return identByte;
    }
}
