/*
    José Vitor Novaes Santos                RA: 743556
    Marcus Vinicius Natrielli Garcia        RA: 743578
    Victor Fernandes de Oliveira Brayner    RA: 743600
*/

package AST;

import java.io.*;

public class FuncCall extends Expr {
    Func function;
    ExprList exprList;

    public FuncCall( Func function, ExprList exprList ) {
        this.function = function;
        this.exprList = exprList;
    }

    //public void genC(PW pw, boolean putParenthesis) {
        // pw.out.print( function.getName() + "(" );
        // if ( exprList != null )
        // exprList.genC(pw);
        // pw.out.print( ")");
    //}

    public Type getType() {
        return function.getReturnType();
    }

    @Override
    public void genC(PW pw) {
        
    }
}