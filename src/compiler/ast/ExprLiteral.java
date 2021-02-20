/*
    José Vitor Novaes Santos
    Marcus Vinicius Natrielli Garcia
    Victor Fernandes de Oliveira Brayner
*/

package compiler.ast;

import compiler.lexer.Symbol;

public class ExprLiteral extends Expr {

    Type t;
    String literal;
    
    public ExprLiteral(Type t, String literal) {
        this.t = t;
        this.literal = literal;
    }

    public void genC(PW pw) {
        if("String".equals(t.getName()))
            pw.out.print("\"" + literal + "\"");
        else
            pw.out.print(literal);
    }

    public Type getType() {
        return t;
    }

    @Override
    public void setType(Type type) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}