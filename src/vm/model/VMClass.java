/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.model;

import java.util.List;
import vm.Utils;
import vm.VM;

/**
 *
 * @author Václav
 */
public class VMClass {

    public int handle;
    public String name;
    public List<VMField> fields;
    public List<VMMethod> methods;
    public VMClass superVMClass;
    public String superClassName;
    public VMConstantPool constantPool;

    public void setSuperClass(VMClass superVMClass) {
	this.superVMClass = superVMClass;
    }

    public String getSuperVMClassName() {

	return this.superVMClass.name;

    }

    public MethodLookup lookupMethod(String className, String methodName, VM vm, byte[][] args) {

	return lookupMethod(className, methodName, vm, args, false);

    }

    public MethodLookup lookupMethod(String className, String methodName, VM vm, byte[][] args, boolean special) {

	String[] currentArgsClassNames = new String[args.length];
	int bestRate = Integer.MAX_VALUE;
	MethodLookup lr = new MethodLookup();

	for (int i = args.length - 1; i >= 0; i--) {
	    byte[] value = args[i];
	    if (Utils.isPointer(value[3])) {
		int pointer = Utils.fieldTypeToInt(Utils.byteArrayToInt(value, 0));
		if (pointer == 0) {
		    currentArgsClassNames[i] = "";
		} else {
		    currentArgsClassNames[i] = vm.getHeap().loadClass(pointer).name;
		}
	    } else {
		currentArgsClassNames[i] = "Integer";
	    }
	}

	lr.args = args;

	VMClass svmClass = this;
	while (svmClass != null) {
	    VMClass superClass = this;
	    boolean foundSuper = false;
	    if (className != null && !special) {
		while (superClass != null) {
		    if (className.equals(superClass.name)) {
			foundSuper = true;
			break;
		    }
		    superClass = superClass.superVMClass;
		}
	    }

	    if (className == null || className.equals(svmClass.name) || foundSuper) {
		className = null;
		for (VMMethod method : svmClass.methods) {
		    if (method.name.equals(methodName)) {
			int rating = rateVMMethod(method, currentArgsClassNames, vm);
			if (rating == -1) {
			    continue;
			}
			if (rating == 0) {
			    lr.method = method;
			    return lr;
			}
			if (rating < bestRate) {
			    bestRate = rating;
			    lr.method = method;
			}
		    }
		}
	    }
	    svmClass = svmClass.superVMClass;
	}

	return lr;

    }
    private static boolean isClassNameSuperClass(String className){
	return (className.equals("") || className.equals("java.lang.Object"));
    }
    
    
      private static int rateVMMethod(VMMethod method, String[] argsNames, VM vm) {
        int rate = 0;
        if (argsNames.length != method.arguments.size()) return -1;
        for (int i = 0; i < method.arguments.size(); i++) {
            String argClassName = method.arguments.get(i).className;
            if (!argClassName.equals(argsNames[i])) {
                if (isClassNameSuperClass(argClassName)) {
                    continue;
                } else {
                    VMClass clazz;
                    try {
                        clazz = vm.getClassesTable().getClassByName(argClassName);
                    } catch (Exception e) {
                        return -1;
                    }
                    int p = 0;
                    boolean found = false;
                    while (clazz != null) {
                        p++;
                        if (clazz.name.equals(argClassName)) {
                            found = true;
                            break;
                        }
                        clazz = clazz.superVMClass;
                    }
                    if (!found) {
                        // argument not passed
                        return -1;
                    }
                    rate += p;
                }
            }
        }
        return rate;
    }


}
