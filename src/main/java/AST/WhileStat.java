package AST;

import java.io.*;

public class WhileStat extends Stat {

    private Expr expr;
    private StatList sl;

    public WhileStat(Expr expr, StatList sl) {
        this.expr = expr;
        this.sl = sl;
    }

    public void genC(PW pw) {
        // pw.print("while ( ");
        // expr.genC(pw);
        // pw.out.println(" )");
        // if ( statement instanceof CompositeStatement )
        // statement.genC(pw);
        // else {
        // pw.add();
        // statement.genC(pw);
        // pw.sub();
        // }
    }

}