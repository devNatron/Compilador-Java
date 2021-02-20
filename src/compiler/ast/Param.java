/*
    José Vitor Novaes Santos
    Marcus Vinicius Natrielli Garcia
    Victor Fernandes de Oliveira Brayner
*/

package compiler.ast;

public class Param extends Variable {
    public Param( String name, Type type ) {
        super(name, type);
    }
    public Param( String name ) {
        super(name);
    }
}