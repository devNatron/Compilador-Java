/*
    José Vitor Novaes Santos
    Marcus Vinicius Natrielli Garcia
    Victor Fernandes de Oliveira Brayner
*/

package compiler.ast;

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