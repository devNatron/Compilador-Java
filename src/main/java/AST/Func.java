package AST;

public class Func {
    private String name;
    private ParamList paramList;
    private StatList statList;
    private Type returnType;

    public Func(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setParamList(ParamList paramList) {
        this.paramList = paramList;
    }

    public ParamList getParamList() {
        return paramList;
    }

    public Type getReturnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    public void setStatList(StatList statList) {
        this.statList = statList;
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
        // pw.out.print("def " + name + "(");
        // if (paramList != null)
        // paramList.genC(pw);
        // pw.out.println(") : " + returnType.getCname() + "{");
        // pw.add();
        // pw.out.println();
        // statList.getStatementList().genC(pw);
        // pw.sub();
        // pw.out.println("}");
    }
}
