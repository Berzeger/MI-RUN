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
public class MethodLookup {

    public VMMethod method;
    public byte[][] args;

    public MethodLookup() {
    }

    public MethodLookup(VMMethod method, byte[][] args) {
	this.method = method;
	this.args = args;
    }
}
