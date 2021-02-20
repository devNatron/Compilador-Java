/*
    José Vitor Novaes Santos
    Marcus Vinicius Natrielli Garcia
    Victor Fernandes de Oliveira Brayner
*/

package compiler.ast;

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