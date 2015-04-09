package vm.model;

/**
 *
 * @author Václav
 */
public class VMField {

    public String name;
    public FieldType type;
    public String className;

    public VMField(String name, FieldType type, String className) {
	this.name = name;
	this.type = type;
	this.className = className;
    }
}
