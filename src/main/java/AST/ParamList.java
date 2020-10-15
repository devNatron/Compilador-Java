package AST;

import java.io.*;
import java.util.*;

public class ParamList {

    ArrayList<Type> v;

    public ParamList() {
        v = new ArrayList<Type>();
    }

    public void addElement(Type parameter) {
        v.add(parameter);
    }

    public int getSize() {
        return v.size();
    }

    public Enumeration elements() {
        return v.elements();
    }

    public ArrayList<Type> getParamList() {
        return v;
    }

    public void genC(PW pw) {
        // Parameter p;
        // Iterator e = v.iterator();
        // int size = v.size();
        // while(e.hasNext()){
        // p = (Parameter) e.next();
        // pw.out.print(p.getType().getCname() + " " + p.getName());

        // if(--size > 0)
        // pw.out.print(", ");
        // }
    }

}