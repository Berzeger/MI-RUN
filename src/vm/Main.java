package vm;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class Main {
    
    public static void main(String[] args) {
        VM vm = new VM();
        vm.debug = false;
        vm.run();
    }
}
