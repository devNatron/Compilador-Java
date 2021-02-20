/*
    José Vitor Novaes Santos
    Marcus Vinicius Natrielli Garcia
    Victor Fernandes de Oliveira Brayner
*/

package compiler.ast;

public class ReturnStat extends Stat {
    private Expr expr;

    public ReturnStat(Expr expr) {
        this.expr = expr;
    }

    public void genC(PW pw) {
        pw.out.print("return ");
        expr.genC(pw);
        pw.out.println(";");
    }
}
