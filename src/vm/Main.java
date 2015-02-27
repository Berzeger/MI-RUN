package vm;

import static vm.Bytecode.*;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class Main {
    public static int[] test = {
      ICONST, 99, PRINT, //ICONST, 1, ICONST, 3, IADD, PRINT, HALT  
        HALT
    };
    
    public static void main(String[] args) {
        VM vm = new VM(test, 0, 0);
        vm.debug = true;
        vm.run();
    }
}
