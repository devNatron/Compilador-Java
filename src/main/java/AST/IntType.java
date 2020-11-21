/*
    José Vitor Novaes Santos                RA: 743556
    Marcus Vinicius Natrielli Garcia        RA: 743578
    Victor Fernandes de Oliveira Brayner    RA: 743600
*/

package AST;

public class IntType extends Type {
    public IntType() {
        super("int");
    }
    
    public String getCname() {
        return "int";
    }
}