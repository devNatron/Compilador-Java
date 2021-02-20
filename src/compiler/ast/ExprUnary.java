/*
    José Vitor Novaes Santos
    Marcus Vinicius Natrielli Garcia
    Victor Fernandes de Oliveira Brayner
*/

package compiler.ast;

import compiler.lexer.Symbol;

public class ExprUnary extends Expr {

    private Expr expr;
    private Symbol op;

    public ExprUnary(Expr expr, Symbol op) {
        this.expr = expr;
        this.op = op;
    }

    public void genC(PW pw) {
        switch(op){
            case PLUS:
                pw.out.print("+");
            break;
            case MINUS:
                pw.out.print("-");
            break;
        }
        expr.genC(pw);
    }

    public Type getType() {
        return expr.getType();
    }

    @Override
    public void setType(Type type) {
       //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}