package AST;

public class ReturnStat extends Stat {
    private Expr expr;

    public ReturnStat(Expr expr) {
        this.expr = expr;
    }

    public void genC(PW pw) {
        // pw.print("return ");
        // expr.genC(pw);
        // pw.out.println(";");
    }
}
