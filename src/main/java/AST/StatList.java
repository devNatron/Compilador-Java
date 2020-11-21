/*
    José Vitor Novaes Santos                RA: 743556
    Marcus Vinicius Natrielli Garcia        RA: 743578
    Victor Fernandes de Oliveira Brayner    RA: 743600
*/

package AST;

import java.util.ArrayList;

public class StatList {
    private ArrayList<Stat> v;

    public StatList(ArrayList<Stat> v) {
        this.v = v;
    }

    public ArrayList<Stat> getStatementList() {
        return v;
    }

    public void genC(PW pw) {
        if ( v != null ) {
            v.forEach(s -> {
                pw.out.print( "\t");
                s.genC(pw);
            });
        }
    }
}