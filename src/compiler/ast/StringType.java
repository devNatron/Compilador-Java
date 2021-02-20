/*
    José Vitor Novaes Santos
    Marcus Vinicius Natrielli Garcia
    Victor Fernandes de Oliveira Brayner
*/

package compiler.ast;

public class StringType extends Type {
    public StringType() {
        super("String");
    }
    
    @Override
    public String getCname() {
        return "char *";
    }
    
}