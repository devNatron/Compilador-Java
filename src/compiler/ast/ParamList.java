/*
    José Vitor Novaes Santos
    Marcus Vinicius Natrielli Garcia
    Victor Fernandes de Oliveira Brayner
*/

package compiler.ast;

import java.io.*;
import java.util.*;

public class ParamList {

    ArrayList<Param> v;

    public ParamList() {
        v = new ArrayList<Param>();
    }

    public void addElement(Param parameter) {
        v.add(parameter);
    }

    public int getSize() {
        return v.size();
    }

    public Enumeration elements() {
        Enumeration e = Collections.enumeration(v);
        return e;
    }

    public ArrayList<Param> getParamList() {
        return v;
    }

    public void genC(PW pw) {
        Param p;
        Iterator e = v.iterator();
        int size = v.size();
        
        while(e.hasNext()){
            p = (Param) e.next();
            pw.out.print(p.getType().getCname() + " " + p.getName());

            if(--size > 0)
                pw.out.print(", ");
        }
    }

}