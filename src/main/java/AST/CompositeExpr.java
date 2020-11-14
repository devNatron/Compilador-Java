/*
    José Vitor Novaes Santos                RA: 743556
    Marcus Vinicius Natrielli Garcia        RA: 743578
    Victor Fernandes de Oliveira Brayner    RA: 743600
*/

package AST;

import Lexer.*;
import java.io.*;

public class CompositeExpr extends Expr {
    private Expr left, right;
    private Symbol oper;
    
    public CompositeExpr(Expr pleft, Symbol poper, Expr pright) {
        left = pleft;
        oper = poper;
        right = pright;
    }

    public void genC(PW pw) {
        // left.genC(pw);
        // pw.out.print(" " + oper.toString() + " ");
        // right.genC(pw);
    }

    public Type getType() {
        // left and right must be the same type
        if (oper == Symbol.EQ || oper == Symbol.NEQ || oper == Symbol.LE || oper == Symbol.LT || oper == Symbol.GE
                || oper == Symbol.GT || oper == Symbol.AND || oper == Symbol.OR)
            return Type.booleanType;
        else
            return Type.intType;
    }

    @Override
    public void setType(Type type) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
