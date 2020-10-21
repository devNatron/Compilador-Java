/*
    José Vitor Novaes Santos                RA: 743556
    Marcus Vinicius Natrielli Garcia        RA: 743578
    Victor Fernandes de Oliveira Brayner    RA: 743600
*/

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
