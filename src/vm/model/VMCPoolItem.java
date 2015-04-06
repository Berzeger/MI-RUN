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
public class VMCPoolItem {

    public enum CPType {
	STRING, INT
    }

    private CPType type;
    private String value;

    public VMCPoolItem(CPType type, String value) {
	this.type = type;
	this.value = value;
    }

    public CPType getType() {
	return type;
    }

    public void setType(CPType type) {
	this.type = type;
    }

    public String getValue() {
	return value;
    }

    public void setValue(String value) {
	this.value = value;
    }
}
