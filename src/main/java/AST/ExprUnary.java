package AST;

import Lexer.Symbol;

public class ExprUnary extends Expr {

    private Expr expr;
    private Symbol op;

    public ExprUnary(Expr expr, Symbol op) {
        this.expr = expr;
        this.op = op;
    }

    public void genC(PW pw) {
        // switch(op){
        // case Symbol.PLUS:
        // pw.out.print("+");
        // break;
        // case Symbol.MINUS:
        // pw.out.print("-");
        // break;
        // case Symbol.NOT:
        // pw.out.print("!");
        // break;
        // }
        // expr.genC(pw);
    }

    public Type getType() {
        return expr.getType();
    }

}