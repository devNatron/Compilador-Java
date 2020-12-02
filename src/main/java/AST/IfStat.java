/*
    José Vitor Novaes Santos                RA: 743556
    Marcus Vinicius Natrielli Garcia        RA: 743578
    Victor Fernandes de Oliveira Brayner    RA: 743600
*/

package AST;


public class IfStat extends Stat {

    private Expr expr;
    private StatList thenPart, elsePart;

    public IfStat(Expr expr, StatList thenPart, StatList elsePart) {
        this.expr = expr;
        this.thenPart = thenPart;
        this.elsePart = elsePart;
    }

    public void genC(PW pw) {
        pw.out.print("if ( ");
        expr.genC(pw);
        pw.out.println(" ) { ");
        if ( thenPart != null ) {
            pw.out.print("\t");
            pw.add();
            thenPart.genC(pw);
            pw.sub();
            pw.out.println("\t}");
        }
        if ( elsePart != null ) {
            pw.out.println("\telse {");
            pw.add();
            elsePart.genC(pw);
            pw.sub();
            pw.out.println("\t}");
        }
    }

}
