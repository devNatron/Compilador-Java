/*
    José Vitor Novaes Santos
    Marcus Vinicius Natrielli Garcia
    Victor Fernandes de Oliveira Brayner
*/

package compiler.ast;

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
        pw.out.print(name);
    }
}