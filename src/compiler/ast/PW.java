/*
    José Vitor Novaes Santos
    Marcus Vinicius Natrielli Garcia
    Victor Fernandes de Oliveira Brayner
*/

package compiler.ast;

import java.lang.System;
import java.io.*;

public class PW {
    public void add() {
        currentIndent += step;
    }

    public void sub() {
        currentIndent -= step;
    }

    public void set(PrintWriter out) {
        this.out = out;
        currentIndent = 0;
    }

    public void set(int indent) {
        currentIndent = indent;
    }

    public void print(String s) {
        out.print(space.substring(0, currentIndent));
        out.print(s);
    }

    public void println(String s) {
        out.print(space.substring(0, currentIndent));
        out.println(s);
    }

    int currentIndent = 0;

    static public final int green = 0, java = 1;
    int mode = green;
    public int step = 3;
    public PrintWriter out;

    static final private String space = " ";
}
