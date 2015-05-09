/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.example.sat;

/**
 *
 * @author Filip
 */
public class Main {

    public static void main(String[] args) {
	State state = new State(10);
	int[] weights = new int[]{87 ,46, 76, 16, 35, 17, 26, 10, 67, 54, 62, 62, 12, 17, 30, 19, 25, 64, 95, 28};

	Clause[] clauses = new Clause[10];

	clauses[0] = new Clause(15, 30, 1, 16, 19, 0, 2, 46, 1);
	clauses[1] = new Clause(15, 30, 1, 3, 76, 1, 6, 17, 0);
	clauses[2] = new Clause(12, 62, 0, 6, 17, 0, 13, 12, 1);
	clauses[3] = new Clause(2, 46, 1, 16, 19, 0, 1, 87, 1);
	clauses[4] = new Clause(17, 25, 0, 19, 95, 0, 1, 87, 1);
	clauses[5] = new Clause(16, 19, 1, 6, 17, 1, 14, 17, 0);
	clauses[6] = new Clause(8, 10, 0, 15, 30, 0, 16, 19, 0);
	clauses[7] = new Clause(3, 76, 1, 8, 10, 1, 20, 28, 0);
	clauses[8] = new Clause(9, 67, 1, 13, 12, 1, 8,10,1);
	clauses[9] = new Clause(8, 10, 0, 13, 12, 0, 16, 19, 1);
	
	Formula formula = new Formula(clauses);
	
	Solver solver = new Solver(20, weights, formula);
	
	solver.Solve();
	
    }
}
