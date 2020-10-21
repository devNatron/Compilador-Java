/*
    José Vitor Novaes Santos                RA: 743556
    Marcus Vinicius Natrielli Garcia        RA: 743578
    Victor Fernandes de Oliveira Brayner    RA: 743600
*/

package AST;

public class AssignExprStat extends Stat {
    private Expr exprLeft, exprRight;

    public AssignExprStat(Expr exprLeft, Expr exprRight) {
        this.exprLeft = exprLeft;
        this.exprRight = exprRight;
    }

    public void genC(PW pw) {
        // exprLeft.genC(pw);
        // pw.print(" = ");
        // if (exprRight != null)
        // exprRight.genC(pw);
        // pw.out.println(";");
    }
}
