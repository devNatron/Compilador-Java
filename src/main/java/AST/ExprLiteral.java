/*
    José Vitor Novaes Santos                RA: 743556
    Marcus Vinicius Natrielli Garcia        RA: 743578
    Victor Fernandes de Oliveira Brayner    RA: 743600
*/

package AST;

import Lexer.Symbol;

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