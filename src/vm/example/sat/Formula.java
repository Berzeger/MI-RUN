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
public class Formula {

    private Clause[] clauses;

    public Formula(Clause[] clauses) {
	this.clauses = clauses;
    }

    public int GetClausesCount() {
	return clauses.length;
    }

    public int Satisfied(State state) {

	for (int i = 0; i < clauses.length; i++) {
	    if (clauses[i].Satisfied(state) == 0) {
		return 0;
	    }
	}
	return 1;
    }


}
