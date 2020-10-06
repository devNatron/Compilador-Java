package AST;
import java.util.*;

public class StatList {
    private ArrayList<Stat> v;
    
    public StatList(ArrayList<Stat> v) {
        this.v = v;
    }
    
    public void genC() {
        if ( v != null ) {
            for ( Stat s : v )
                s.genC();
        }
    }
}