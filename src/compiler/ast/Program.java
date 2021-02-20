/*
    José Vitor Novaes Santos
    Marcus Vinicius Natrielli Garcia
    Victor Fernandes de Oliveira Brayner
*/
package compiler.ast;

import java.util.ArrayList;

public class Program {

    private ArrayList<Func> arrayFunc;

    public Program(ArrayList<Func> arrayFunc) {
        this.arrayFunc = arrayFunc;
    }

    public void genC(PW pw) {
        pw.out.println("#include <stdio.h>\n");
        
        if (arrayFunc != null) {
            arrayFunc.forEach(f -> {
                    f.genC(pw);
            });
        }
    }
}