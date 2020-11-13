/*
    José Vitor Novaes Santos                RA: 743556
    Marcus Vinicius Natrielli Garcia        RA: 743578
    Victor Fernandes de Oliveira Brayner    RA: 743600
*/

package AST;

import java.io.*;

public class VariableExpr extends Expr {
    private String name;
    private Type type;
    
    public VariableExpr( String name, Type type ) {
        this.name = name;
        this.type = type;
    }
    
    public VariableExpr( String name ) {
        this.name = name;
    }
    
    public void setType( Type type ) {
        this.type = type;
    }
    
    public String getName() { 
        return name; 
    }
    
    public Type getType() {
        return type;
    }

    @Override
    public void genC(PW pw) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}