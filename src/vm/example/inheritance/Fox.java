/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.example.inheritance;

/**
 *
 * @author Filip
 */
public class Fox extends Animal {
    @Override
    public void makeSound() {
        vm.system.System.println("Ringdingdingding");
    }
}
