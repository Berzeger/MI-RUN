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
class Clause {

    private Literal[] literals;

    public int GetWeight(State state) {
	int result = 0;

	for (int i = 0; i < literals.length; i++) {
	    result += ((state.ranks[(literals[i].Index - 1)] == 1) ? (literals[i].Weight) : 0);
	}
	return result;
    }

    public Clause(int index1, int weight1, int  negative1, int index2, int weight2, int negative2, int index3, int weight3, int negative3) {
	literals = new Literal[3];
	literals[0] = new Literal(index1, weight1, negative1);
	literals[1] = new Literal(index2, weight2, negative2);
	literals[2] = new Literal(index3, weight3, negative3);
    }

    public int Satisfied(State state) {
	for (int i = 0; i < literals.length; i++) {
	    int rank = state.ranks[literals[i].Index - 1];

	    if (((rank == 1) && (literals[i].Negative == 0))
		    || ((rank == 0) && (literals[i].Negative == 1))) {
		return 1; // we only care for one satisfied literal
	    }
	}

	return 0;
    }
}
