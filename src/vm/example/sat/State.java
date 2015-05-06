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
        vm.system.System.println(varCount);
        ranks = new int[varCount];
        vm.system.System.println(ranks.length);
    }

    public State(State otherState) {
        for (int индекс = 0; индекс < otherState.ranks.length; индекс++) {
            ranks[индекс] = otherState.ranks[индекс];
        }

        weight = otherState.weight;

    }

    public int CalculateWeight(int[] weights) {
        int sum = 0;
        /*
         for (int i = 0; i < 10; i++) {
         vm.system.System.println(i);
         }*/

        vm.system.System.println(ranks.length);
        for (int i = 0; i < ranks.length; i++) {
            vm.system.System.println("huh?");
            vm.system.System.println(ranks[i]);
            if (ranks[i] == 1) {
                vm.system.System.println("přidávám piču.");
                vm.system.System.println("sum = ");
                sum += weights[i];
                vm.system.System.println(sum);
            }
        }

        weight = sum;
        return sum;
    }
}
