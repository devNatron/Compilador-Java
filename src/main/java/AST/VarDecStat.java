package AST;

public class VarDecStat extends Stat {
    private Type type;
    private String id;

    public VarDecStat(Type type, String id) {
        this.type = type;
        this.id = id;
    }

    public void genC(PW pw) {
        // pw.print("var ");
        // pw.print(type.getName());
        // pw.out.println(" "+ id +";");
    }
}
