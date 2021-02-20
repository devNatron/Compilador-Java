/*
    José Vitor Novaes Santos
    Marcus Vinicius Natrielli Garcia
    Victor Fernandes de Oliveira Brayner
*/

package compiler.ast;

public class BooleanType extends Type {
    public BooleanType() { 
        super("boolean"); 
    }
    
    public String getCname() {
        return "int";
    }
}