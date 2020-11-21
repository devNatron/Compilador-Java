/*
    José Vitor Novaes Santos                RA: 743556
    Marcus Vinicius Natrielli Garcia        RA: 743578
    Victor Fernandes de Oliveira Brayner    RA: 743600
*/

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

    public void print(String expr){
        System.out.print(expr);
    }

    public void println(String expr){
        System.out.println(expr);
    }

    public void genC(PW pw) {
        if(returnType == null)
            pw.out.print("void");
        else
            pw.out.print(returnType.getCname());
        
        pw.out.print(" " + name + "(");
        
        if (paramList != null)
            paramList.genC(pw);
        
        pw.out.print("){");
        pw.add();
        pw.out.println();
        
        statList.genC(pw);

        pw.sub();
        pw.out.println("}");
    }
}
