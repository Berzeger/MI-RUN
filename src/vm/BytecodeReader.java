package vm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.commons.io.FilenameUtils;
import vm.model.VMClass;
import vm.model.VMMethod;

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
    
    public BytecodeReader(VM vm) {
        try {
            // TODO: Not hardcoded path
            // For each file in directory
            Files.walk(Paths.get("./examples")).forEach(filePath -> {
                // Is it a class file?
                System.out.println(filePath.toString());
               if (FilenameUtils.getExtension(filePath.toString()).equals("class")) {
                   try {
                       cParser = new ClassParser(filePath.toString());
                       clazz = cParser.parse();
                       VMClass classToSave = new VMClass();
                       // int address - dafuq is this?
                       classToSave.name = clazz.getClassName();
                       // VMField List fields
                       // VMMethod List methods
                       // VMClass superclass
                       classToSave.superClassName = clazz.getSuperclassName();
                       // VMConstantPool constant pool
                       
                       for (Method method : clazz.getMethods()) {
                           VMMethod meth = new VMMethod();
                           meth.name = method.getName();
                           // VMField List arguments
                           // VMField List locals
                           // FieldType return type
                           meth.instructionPointer = 0;
                           meth.clazz = classToSave;
                           meth.isStatic = method.isStatic();
                           meth.isNative = method.isNative();
                           classToSave.methods.add(meth);
                           
                           vm.getMethodsTable().add(meth);
                       }
                       
                       vm.getClassesTable().add(classToSave);
                   } catch (IOException ex) {
                       Logger.getLogger(BytecodeReader.class.getName()).log(Level.SEVERE, null, ex);
                   }
               }
            });
            
            // I'll keep this here just for future references
            // as how to read method bytecode
            /*
            for (Method method : clazz.getMethods()) {
                System.out.println(method);
                //System.out.println(-79 & 0xFF);
                // TODO: Figure out why there are negative integers in the code
                System.out.println(Arrays.toString(method.getCode().getCode()));
   
                // Prints out one byte
                System.out.println(method.getCode().getCode()[0]);
            }
                    */
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
