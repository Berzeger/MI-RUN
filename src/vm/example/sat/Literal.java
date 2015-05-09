/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.example.sat;

/**
 *
 * @author Václav
 */
class Literal {

    public int Negative;

    public int Index;

    public int Weight;

    public Literal(int index, int weight, int negativeBool) {
	this.Index = index;
	this.Weight = weight;
	this.Negative = negativeBool;
    }
}
