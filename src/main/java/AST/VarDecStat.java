/*
    José Vitor Novaes Santos                RA: 743556
    Marcus Vinicius Natrielli Garcia        RA: 743578
    Victor Fernandes de Oliveira Brayner    RA: 743600
*/

package AST;

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
