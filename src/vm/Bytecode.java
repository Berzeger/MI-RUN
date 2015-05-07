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
import vm.instructions.apush;
import vm.instructions.arraylength;
import vm.instructions.baload;
import vm.instructions.bastore;
import vm.instructions.dup;
import vm.instructions.getfield;
import vm.instructions.gotoInstruction;
import vm.instructions.iaload;
import vm.instructions.if_acmpeq;
import vm.instructions.if_acmpne;
import vm.instructions.if_icmpeq;
import vm.instructions.if_icmpge;
import vm.instructions.if_icmpgt;
import vm.instructions.if_icmple;
import vm.instructions.if_icmplt;
import vm.instructions.if_icmpne;
import vm.instructions.iinc;
import vm.instructions.intpush;
import vm.instructions.invokespecial;
import vm.instructions.invokestatic;
import vm.instructions.invokevirtual;
import vm.instructions.ireturn;
import vm.instructions.ldc;
import vm.instructions.newInstruction;
import vm.instructions.newarray;
import vm.instructions.putfield;
import vm.instructions.returnInstruction;

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
        instructions.put("iinc", new iinc());
        
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

        instructions.put("apush", new apush());
        instructions.put("bipush", new intpush());
        instructions.put("sipush", new intpush());
        instructions.put("bastore", new bastore());
        instructions.put("iastore", new bastore());
        
        instructions.put("aload", new aload());
        instructions.put("aload_0", new aload(0));
        instructions.put("aload_1", new aload(1));
        instructions.put("aload_2", new aload(2));
        instructions.put("aload_3", new aload(3));
        instructions.put("baload", new baload());
        instructions.put("iaload", new baload()); // iaload is flocked up
        
        instructions.put("ldc", new ldc());

        instructions.put("invokespecial", new invokespecial());
        instructions.put("invokestatic", new invokestatic());
        instructions.put("invokevirtual", new invokevirtual());

        instructions.put("new", new newInstruction());
        instructions.put("newarray", new newarray());
        instructions.put("dup", new dup());

        instructions.put("ireturn", new ireturn());
        instructions.put("return", new returnInstruction());
        
        instructions.put("putfield", new putfield());
        instructions.put("getfield", new getfield());
        instructions.put("arraylength", new arraylength());

        instructions.put("goto", new gotoInstruction());
        instructions.put("if_acmpne", new if_acmpne());
        instructions.put("if_acmpeq", new if_acmpeq());
        instructions.put("if_icmpeq", new if_icmpeq());
        instructions.put("if_icmpne", new if_icmpne());
        instructions.put("if_icmpge", new if_icmpge());
        instructions.put("if_icmpgt", new if_icmpgt());
        instructions.put("if_icmple", new if_icmple());
        instructions.put("if_icmplt", new if_icmplt());

    }
}
