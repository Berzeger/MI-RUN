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

    public byte[] ranks;
    public int weight;

    public State(int varCount) {
        vm.system.System.println(varCount);
        ranks = new byte[varCount];
        vm.system.System.println(ranks.length);
    }

    public State(State otherState) {
        for (int индекс = 0; индекс < otherState.ranks.length; индекс++) {
            ranks[индекс] = otherState.ranks[индекс];
        }

        weight = otherState.weight;

    }

    public int CalculateWeight(byte[] weights) {
        int sum = 0;
        
        for (int i = 0; i < weights.length; i++) {
            if (weights[i] == 1) {
                vm.system.System.println("sum = ");
                sum += weights[i];
                vm.system.System.println(sum);
            }
        }

        weight = sum;
        return sum;
    }
}
