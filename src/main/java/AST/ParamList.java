package AST;

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
        return v.elements();
    }

    public ArrayList<Param> getParamList() {
        return v;
    }

    public int readInt(){
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        in.close();
        return num;
    }

    public String readString(){
        Scanner in = new Scanner(System.in);
        String string = in.nextLine();
        in.close();
        return string;
    }

    public void print(String expr){
        System.out.print(expr);
    }

    public void println(Int expr){
        System.out.println(expr);
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