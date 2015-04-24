package vm;

import java.util.HashMap;
import java.util.Map;
import vm.instructions.aload;
import vm.instructions.astore;
import vm.instructions.iadd;
import vm.instructions.iconst;
import vm.instructions.idiv;
import vm.instructions.iload;
import vm.instructions.imul;
import vm.instructions.istore;
import vm.instructions.isub;
import vm.instructions.Instruction;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class Bytecode {

    public static final Map<String, Instruction> instructions = new HashMap<>();

    static {
        instructions.put("iadd", new iadd());
        instructions.put("isub", new isub());
        instructions.put("imul", new imul());
        instructions.put("idiv", new idiv());

        instructions.put("iconst_0", new iconst(0));
        instructions.put("iconst_1", new iconst(1));
        instructions.put("iconst_2", new iconst(2));
        instructions.put("iconst_3", new iconst(3));
        instructions.put("iconst_4", new iconst(4));
        instructions.put("iconst_5", new iconst(5));
        instructions.put("iconst_m1", new iconst(-1));

        instructions.put("iload", new iload());
        instructions.put("iload_0", new iload(0));
        instructions.put("iload_1", new iload(1));
        instructions.put("iload_2", new iload(2));
        instructions.put("iload_3", new iload(3));

        instructions.put("istore", new istore());
        instructions.put("istore_0", new istore(0));
        instructions.put("istore_1", new istore(1));
        instructions.put("istore_2", new istore(2));
        instructions.put("istore_3", new istore(3));

        instructions.put("astore", new astore());
        instructions.put("astore_0", new astore(0));
        instructions.put("astore_1", new astore(1));
        instructions.put("astore_2", new astore(2));
        instructions.put("astore_3", new astore(3));
        
        instructions.put("aload", new aload());
        instructions.put("aload_0", new aload(0));
        instructions.put("aload_1", new aload(1));
        instructions.put("aload_2", new aload(2));
        instructions.put("aload_3", new aload(3));        

    }
}
