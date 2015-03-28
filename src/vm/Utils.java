/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm;

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

}
