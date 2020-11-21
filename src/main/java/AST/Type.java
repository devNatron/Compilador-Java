/*
    José Vitor Novaes Santos                RA: 743556
    Marcus Vinicius Natrielli Garcia        RA: 743578
    Victor Fernandes de Oliveira Brayner    RA: 743600
*/

package AST;

abstract public class Type {
    
    abstract public String getCname();
    private String name;
    
    public Type( String name ) {
        this.name = name;
    }
    
    public static Type booleanType = new BooleanType();
    public static Type intType = new IntType();
    public static Type StringType = new StringType();
    public static Type undefinedType = new UndefinedType();
    
    public String getName() {
        return name;
    }
}