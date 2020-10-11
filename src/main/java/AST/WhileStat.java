package AST;

import java.io.*;

public class WhileStat extends Stat {

    private Expr expr;
    private Stat statement;

    public WhileStat(Expr expr, Stat statement) {
        this.expr = expr;
        this.statement = statement;
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