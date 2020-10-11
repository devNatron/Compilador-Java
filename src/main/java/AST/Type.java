package AST;

abstract public class Type {
    
    //abstract public String getCname();
    private String name;
    
    public Type( String name ) {
        this.name = name;
    }
    
    public static Type booleanType = new BooleanType();
    public static Type intType = new IntType();
    public static Type StringType = new StringType();
    
    public String getName() {
        return name;
    }
}