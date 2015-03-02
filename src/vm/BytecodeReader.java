package vm;

import java.io.IOException;
import java.util.Arrays;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

/**
 *
 * @author Filip Vondrášek (filip at vondrasek.net)
 */
public class BytecodeReader {
    /**
     * Class file contents:
     * Magic number CAFEBABE (4 bytes)
     * Class file minor version (2 bytes)
     * Class file major version (2 bytes)
     * Constant pool count (2 bytes) - note: This count is at least one greater than the actual number of entries.
     * Constant pool table (variable size) - note: Indexed starting at 1.
     * Access flags, a bitmask (2 bytes)
     * This class identifier (2 bytes)
     * Super class identifier (2 bytes)
     * Interface count (2 bytes)
     * Interface table (variable size)
     * Field count (2 bytes)
     * Field table (variable size)
     * Method count (2 bytes)
     * Method table (variable size)
     * Attribute count (2 bytes)
     * Attribute table (variable size)
     */
    
    private ClassParser cParser;
    private JavaClass clazz;    
    
    public BytecodeReader() {
        try {
            // TODO: Not hardcoded path
            cParser = new ClassParser("Foo.class");
            clazz = cParser.parse();
            
            for (Method method : clazz.getMethods()) {
                System.out.println(method);
                //System.out.println(-79 & 0xFF);
                // TODO: Figure out why there are negative integers in the code
                System.out.println(Arrays.toString(method.getCode().getCode()));
   
                // Prints out one byte
                System.out.println(method.getCode().getCode()[0]);
            }
        } catch (IOException ex) {
            System.err.println("Could not locate or read class file.");
        }
    }
    
    public Method[] getMethods() {
        return clazz.getMethods();
    }
    
    public ConstantPool getConstantPool() {
        return clazz.getConstantPool();
    }
    
    
}
