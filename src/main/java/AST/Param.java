/*
    José Vitor Novaes Santos                RA: 743556
    Marcus Vinicius Natrielli Garcia        RA: 743578
    Victor Fernandes de Oliveira Brayner    RA: 743600
*/

package AST;

public class Param extends Variable {
    public Param( String name, Type type ) {
        super(name, type);
    }
    public Param( String name ) {
        super(name);
    }
}