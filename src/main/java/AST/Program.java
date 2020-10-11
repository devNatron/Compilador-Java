/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST;

import java.util.ArrayList;

/**
 *
 * @author super
 */
public class Program {

    private ArrayList<Func> arrayFunc;

    public Program(ArrayList<Func> arrayFunc) {
        this.arrayFunc = arrayFunc;
    }

    public void genC() {
        // System.out.println("#include <stdio.h>\n");
        // if (arrayFunc != null) {
        // for (Func f : arrayFunc)
        // f.genC();
        // }
    }
}
