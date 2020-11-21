/*
    José Vitor Novaes Santos                RA: 743556
    Marcus Vinicius Natrielli Garcia        RA: 743578
    Victor Fernandes de Oliveira Brayner    RA: 743600
*/

package AST;

public class StringType extends Type {
    public StringType() {
        super("String");
    }
    
    @Override
    public String getCname() {
        return "char *";
    }
    
}