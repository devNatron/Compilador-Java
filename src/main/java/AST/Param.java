package AST;

public class Param extends Variable {
    public Param( String name, Type type ) {
        super(name, type);
    }
    public Param( String name ) {
        super(name);
    }
}