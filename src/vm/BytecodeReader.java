package vm;

import java.io.IOException;
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
    
    private Method[] methods;
    private ConstantPool cPool;
    
    
    public BytecodeReader() {
        try {
            // TODO: Not hardcoded path
            cParser = new ClassParser("Foo.class");
            clazz = cParser.parse();
            methods = clazz.getMethods();
            cPool = clazz.getConstantPool();
            
            for (Method method : methods) {
                System.out.println(method);
            }
        } catch (IOException ex) {
            System.err.println("Could not locate or read class file.");
        }
    }
    
    public Method[] getMethods() {
        return methods;
    }
    
    public ConstantPool getConstantPool() {
        return cPool;
    }
}
