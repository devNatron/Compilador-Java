/*
    José Vitor Novaes Santos                RA: 743556
    Marcus Vinicius Natrielli Garcia        RA: 743578
    Victor Fernandes de Oliveira Brayner    RA: 743600
*/

package AST;

import java.util.*;
import java.io.*;

public class CompositeStatement extends Stat {
    private StatList statementList;

    public CompositeStatement(StatList statementList) {
        this.statementList = statementList;
    }

    public void genC(PW pw) {
        pw.println("{");
        if (statementList != null) {
            pw.add();
            statementList.genC(pw);
            pw.sub();
        }
        pw.println("}");
    }

    public StatList getStatementList() {
        return statementList;
    }
}
