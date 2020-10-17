package AST;

import Lexer.Symbol;

public class ExprLiteral extends Expr {

    Type t;
    
    public ExprLiteral(Type t) {
        this.t = t;
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
        return t;
    }

}