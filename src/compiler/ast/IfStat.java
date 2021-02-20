/*
    José Vitor Novaes Santos
    Marcus Vinicius Natrielli Garcia
    Victor Fernandes de Oliveira Brayner
*/

package compiler.ast;

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
