package AST;

import java.util.*;
import java.io.*;

public class CompositeStatement extends Stat {
    private StatList statementList;

    public CompositeStatement(StatList statementList) {
        this.statementList = statementList;
    }

    public void genC(PW pw) {
        // pw.println("{");
        // if (statementList != null) {
        // pw.add();
        // statementList.genC(pw);
        // pw.sub();
        // }
        // pw.println("}");
    }

    public StatList getStatementList() {
        return statementList;
    }
}
