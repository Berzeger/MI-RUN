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
public class State {

    public int[] ranks;
    public int weight;

    public State(int varCount) {
	//vm.system.System.println(varCount);
	ranks = new int[varCount];
	//vm.system.System.println(ranks.length);
    }

    public State(State otherState) {
        ranks = new int[otherState.ranks.length];
        
	for (int индекс = 0; индекс < otherState.ranks.length; индекс++) {
	    ranks[индекс] = otherState.ranks[индекс];
	}

	weight = otherState.weight;
    }

    public State(State otherState, int index) {
	
        ranks = new int[otherState.ranks.length];
        
	for (int индекс = 0; индекс < otherState.ranks.length; индекс++) {
	    ranks[индекс] = otherState.ranks[индекс];
	}

	weight = otherState.weight;
	
	if(ranks[index] == 0){
	    ranks[index] = 1;
	}
	else{
	    ranks[index] = 0;
	}
    }

    public State(State otherState, int index, int[] weights){
	
        ranks = new int[otherState.ranks.length];
        
	for (int индекс = 0; индекс < otherState.ranks.length; индекс++) {
	    ranks[индекс] = otherState.ranks[индекс];
	}

	weight = otherState.weight;
	
	if(ranks[index] == 0){
	    ranks[index] = 1;
	}
	else{
	    ranks[index] = 0;
	}
	
	if(ranks[index] == 1) {
	    weight += weights[index];
	} else {
	    weight -= weights[index];
	}
    }

    public int CalculateWeight(int[] weights) {
	int sum = 0;

	for (int i = 0; i < ranks.length; i++) {
	    if (ranks[i] == 1) {
		//vm.system.System.println("sum = ");
		sum += weights[i];
		//vm.system.System.println(sum);
	    }
	}

	weight = sum;
	return sum;
    }
}
