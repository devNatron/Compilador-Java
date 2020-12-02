/*
    José Vitor Novaes Santos                RA: 743556
    Marcus Vinicius Natrielli Garcia        RA: 743578
    Victor Fernandes de Oliveira Brayner    RA: 743600
*/
package AST;

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