/*
    José Vitor Novaes Santos
    Marcus Vinicius Natrielli Garcia
    Victor Fernandes de Oliveira Brayner
*/

package compiler.ast;

import java.io.*; 
import java.util.*;

public class ExprList {
    
    private ArrayList<Expr> v;
    
    public ExprList() {
        v = new ArrayList<Expr>();
    }
    
    public void addElement( Expr expr ) {
        v.add(expr);
    }
    
    public ArrayList<Expr> getList(){
        return v;
    }
    
    public void genC( PW pw ) {
        
        int size = v.size();
        Iterator e = v.iterator();
        
        while ( e.hasNext() ) {
            ((Expr ) e.next()).genC(pw);
            if ( --size > 0 )
                pw.out.print(", ");
        }
    }
}