/*
    José Vitor Novaes Santos
    Marcus Vinicius Natrielli Garcia
    Victor Fernandes de Oliveira Brayner
*/

package compiler.ast;

import compiler.lexer.*;
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
        left.genC(pw);
        pw.out.print(" " + oper.toString() + " ");
        right.genC(pw);
    }

    public Type getType() {
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
