/*
    José Vitor Novaes Santos
    Marcus Vinicius Natrielli Garcia
    Victor Fernandes de Oliveira Brayner
*/

package compiler.ast;

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