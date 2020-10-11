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

    public void genC() {
        // if ( v != null ) {
        // for ( Stat s : v )
        // s.genC();
        // }
    }
}