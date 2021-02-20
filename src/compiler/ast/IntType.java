/*
    José Vitor Novaes Santos
    Marcus Vinicius Natrielli Garcia
    Victor Fernandes de Oliveira Brayner
*/

package compiler.ast;

public class IntType extends Type {
    public IntType() {
        super("int");
    }
    
    public String getCname() {
        return "int";
    }
}