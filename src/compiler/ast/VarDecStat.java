/*
    José Vitor Novaes Santos
    Marcus Vinicius Natrielli Garcia
    Victor Fernandes de Oliveira Brayner
*/

package compiler.ast;

public class VarDecStat extends Stat {
    private Type type;
    private String id;

    public VarDecStat(Type type, String id) {
        this.type = type;
        this.id = id;
    }

    public void genC(PW pw) {
        pw.out.print(type.getCname() + " ");
        pw.out.println(id +";");
    }
}
