package vm;

import static vm.Bytecode.*;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class Main {
    /*public static int[] test = {
      ICONST, 99, 
      GSTORE, 0,
      GLOAD, 0,
      PRINT, //ICONST, 1, ICONST, 3, IADD, PRINT, HALT  
      HALT
    };*/
    
    public static int[] test = {
      //ICONST_M1,
      //ICONST_M1,
      //ICONST_3,
      //IADD,
      //ISUB
    };
    
    public static void main(String[] args) {
        VM vm = new VM(test, 0, 1);
        vm.debug = true;
        vm.run();
    }
}
