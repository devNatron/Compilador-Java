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

    @Override
    public void genC(PW pw) {
        Type t;
        char format = ' ';
        if(exprList != null && exprList.getList() != null){
            t = exprList.getList().get(0).getType();
        
            if("int".equals(t.getName()) || "boolean".equals(t.getName()))
                format = 'd';
            else
                format = 's';
        }
        
        switch(function.getName()){
            case "print":
                pw.out.print( "printf(\"%" + format +"\", ");
            break;
            case "println":
                pw.out.print( "printf(\"%" + format + "\\r\\n\", ");
            break;
            case "readInt":
            break;
            case "readString":
            break;
            default:
                pw.out.print( function.getName() + "(" );
        }
        
        if ( exprList != null )
            exprList.genC(pw);
        pw.out.print( ")");
    }

    @Override
    public Type getType() {
        return function.getReturnType();
    }

    @Override
    public void setType(Type type) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}