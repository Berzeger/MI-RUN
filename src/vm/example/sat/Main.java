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
	int[] weights = new int[]{1, 1, 7, 3, 4, 2, 0, 8, 9, 2, 6, 8, 6, 2, 9, 9, 0, 6, 7, 3};
        Clause[] clauses = new Clause[10];
        
        // EMERGENCY CASE
        //int[] weights = new int[]{1,2,3,4};
	//Clause[] clauses = new Clause[1];
	//clauses[0] = new Clause(1, 1, 0, 2, 2, 0, 3, 3, 0);

	clauses[0] = new Clause(4, 3, 0, 18, 6, 1, 19, 7, 0);
	clauses[1] = new Clause(3, 7, 0, 18, 6, 0, 5, 4, 1);
	clauses[2] = new Clause(5, 4, 1, 8, 8, 1, 15, 9, 1);
	clauses[3] = new Clause(20, 3, 1, 7, 0, 0, 16, 9, 1);
	clauses[4] = new Clause(10, 2, 0, 13, 6, 1, 7, 0, 1);
	clauses[5] = new Clause(12, 8, 1, 9, 9, 1, 17, 0, 0);
	clauses[6] = new Clause(17, 0, 0, 19, 7, 0, 5, 4, 0);
	clauses[7] = new Clause(16, 9, 1, 9, 9, 1, 15, 9, 0);
	clauses[8] = new Clause(11, 6, 0, 5, 4, 1, 14, 2, 1);
	clauses[9] = new Clause(18, 6, 0, 10, 2, 1, 13, 6, 0);

	Formula formula = new Formula(clauses);
	
	//Solver solver = new Solver(4, weights, formula);
	Solver solver = new Solver(20, weights, formula);
        
	State result = solver.Solve();
        
        vm.system.System.println("Best state weight: ");
        vm.system.System.println(result.weight);
	
    }
}
