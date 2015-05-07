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
        byte[] weights = new byte[] {0, 1, 1, 1, 0, 0, 1, 1, 1, 0};
        
        int sum = state.CalculateWeight(weights);
        vm.system.System.println(sum);
    }
}
