/*
    José Vitor Novaes Santos                RA: 743556
    Marcus Vinicius Natrielli Garcia        RA: 743578
    Victor Fernandes de Oliveira Brayner    RA: 743600
*/

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
        pw.out.print("while ( ");
        expr.genC(pw);
        pw.out.println(" ){");
        pw.out.print("\t");
        sl.genC(pw);
        pw.out.println("\t}\n");
        
    }

}