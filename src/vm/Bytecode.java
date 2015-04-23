package vm;

import java.util.HashMap;
import java.util.Map;
import vm.instructions.IADD;
import vm.instructions.ICONST;
import vm.instructions.IDIV;
import vm.instructions.ILOAD;
import vm.instructions.IMUL;
import vm.instructions.ISTORE;
import vm.instructions.ISUB;
import vm.instructions.Instruction;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class Bytecode {

    public static final Map<String, Instruction> instructions = new HashMap<>();
    
    static {
        instructions.put("iadd", new IADD());
        instructions.put("isub", new ISUB());
        instructions.put("imul", new IMUL());
        instructions.put("idiv", new IDIV());

        instructions.put("iconst_0", new ICONST(0));
        instructions.put("iconst_1", new ICONST(1));
        instructions.put("iconst_2", new ICONST(2));
        instructions.put("iconst_3", new ICONST(3));
        instructions.put("iconst_4", new ICONST(4));
        instructions.put("iconst_5", new ICONST(5));
        instructions.put("iconst_m1", new ICONST(-1));

        instructions.put("iload", new ILOAD());
        instructions.put("iload_0", new ILOAD(0));
        instructions.put("iload_1", new ILOAD(1));
        instructions.put("iload_2", new ILOAD(2));
        instructions.put("iload_3", new ILOAD(3));
        
        instructions.put("istore", new ISTORE());
        instructions.put("istore_0", new ISTORE(0));
        instructions.put("istore_1", new ISTORE(1));
        instructions.put("istore_2", new ISTORE(2));
        instructions.put("istore_3", new ISTORE(3));        
    }
}
    