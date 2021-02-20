/*
    José Vitor Novaes Santos
    Marcus Vinicius Natrielli Garcia
    Victor Fernandes de Oliveira Brayner
*/

package compiler.ast;

abstract public class Expr {
    abstract public void genC(PW pw);
    abstract public Type getType();
    abstract public void setType(Type type);
}