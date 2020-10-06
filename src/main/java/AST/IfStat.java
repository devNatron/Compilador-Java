/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST;

/**
 *
 * @author super
 */
public class IfStat extends Stat{
    
    private Expr expr;
    private StatList thenPart, elsePart;
    
    public IfStat( Expr expr, StatList thenPart, StatList elsePart ) {
        this.expr = expr;
        this.thenPart = thenPart;
        this.elsePart = elsePart;
    }
    
    public void genC( PW pw ) {
        pw.print("if ( ");
        expr.genC(pw);
        pw.out.println(" ) { ");
        if ( thenPart != null ) {
            pw.add();
            thenPart.genC(pw);
            pw.sub();
            pw.println("}");
        }
        if ( elsePart != null ) {
            pw.println("else {");
            pw.add();
            elsePart.genC(pw);
            pw.sub();
            pw.println("}");
        }
    }
    
}
