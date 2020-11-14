/*
    José Vitor Novaes Santos                RA: 743556
    Marcus Vinicius Natrielli Garcia        RA: 743578
    Victor Fernandes de Oliveira Brayner    RA: 743600
*/

package trabalho01;

import AST.*;
import Lexer.*;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

public class Compiler {
    
    private Lexer lexer;
    private SymbolTable symbolTable;
    private CompilerError error;
    private boolean mainDefinida;
    private Func currentFunction;

    public Program compile(char m_input[], PrintWriter PW, String fileName) {
        symbolTable = new SymbolTable();
        error = new CompilerError( lexer, new PrintWriter(PW), fileName);
        lexer = new Lexer(m_input, error);
        error.setLexer(lexer);
        
        lexer.nextToken();

        Program p = null;
        
        try{
            p = program();
        } catch (Exception e){
           e.printStackTrace();
        }
        
        if ( error.wasAnErrorSignalled() )
            return null;
         else
            return p;
    }

    //Program ::= Func { Func }
    private Program program() {
        ArrayList<Func> funcs = new ArrayList<>();
        
        createDefinedFuncs();
        
        while(lexer.token != Symbol.EOF){
            Func f = func();
            if(f != null)
                funcs.add(f);
            else
                break;
        }

        if(lexer.token != Symbol.EOF)
            error.show("EOF esperado");
        
        if(symbolTable.getInGlobal("main") == null)
            error.show("código precisa de uma função main");
        
           
        return new Program(funcs);
    }
    
    private void createDefinedFuncs(){
        Func f = new Func("readInt");
        f.setParamList(new ParamList());
        f.setReturnType(Type.intType);
        symbolTable.putInGlobal("readInt", f);
        
        f = new Func("readString");
        f.setParamList(new ParamList());
        f.setReturnType(Type.StringType);
        symbolTable.putInGlobal("readString", f);
        
        ParamList p = new ParamList();
        Param param = new Param("expr", Type.undefinedType);
        p.addElement(param);
        
        f = new Func("print");
        f.setParamList(p);
        f.setReturnType(null);
        symbolTable.putInGlobal("print", f);
        
        p = new ParamList();
        param = new Param("expr", Type.undefinedType);
        p.addElement(param);
        
        f = new Func("println");
        f.setParamList(p);
        f.setReturnType(null);
        symbolTable.putInGlobal("println", f);
    }

    // Func ::= "def" Id [ "(" ParamList ")" ] [ ":" Type ] "{" StatList "}"
    private Func func(){
        Func f = null;

        if(lexer.token == Symbol.DEF){
            lexer.nextToken();
            if(lexer.token == Symbol.IDENT){
                String name = lexer.getStringValue();
                
                if(symbolTable.getInGlobal(name) != null)
                    error.show("Função " + name + " já foi declarada antes");

                f = new Func(name);
                currentFunction = f;
                
                symbolTable.putInGlobal(name, f);
                
                lexer.nextToken();
                
                if(lexer.token == Symbol.LEFTPAR){
                    lexer.nextToken();
                    
                    if(name.compareTo("main") == 0)
                        error.show("função main não deve possuir parâmetros");
                            
                    ParamList pl = paramList();
                    f.setParamList(pl);
                    
                    if(lexer.token == Symbol.RIGHTPAR){
                        lexer.nextToken();
                    }else
                        error.show(") esperado");
                }

                if(lexer.token == Symbol.COLON){
                    lexer.nextToken();
                    f.setReturnType(type());
                }
                
                if(lexer.token == Symbol.CURLYLEFTBRACE){
                    lexer.nextToken();
                    StatList st = statList();

                    if(lexer.token == Symbol.CURLYRIGHTBRACE){
                        lexer.nextToken();
                        f.setStatList(st);
                    }else
                        error.show("} esperado");
                }else
                    error.show("{ esperado");
            }else
                error.show("id esperado");
        }else
            error.show("def esperado");
        
        symbolTable.removeLocalIdent();
        return f;
    }
    
    // Expr ::= ExprAnd { "||" ExprAnd }
    private Expr expr() {
        Expr left, right;
        left = exprAnd();

        while(lexer.token == Symbol.OR){
            lexer.nextToken();
            right = exprAnd();
            if (!checkBoolean(left.getType(), right.getType()))
                error.show("esperado tipo booleano na expressão");
            
            left = new CompositeExpr(left, Symbol.OR, right);
        }
        
        return left;
    }

    // ExprAnd ::= ExprRel { "&&" ExprRel }
    private Expr exprAnd() {
        Expr left, right;
        left = exprRel();
        
        while (lexer.token == Symbol.AND) {
            lexer.nextToken();
            right = exprRel();
            
            if (!checkBoolean(left.getType(), right.getType()))
                error.show("esperado tipo booleano na expressão");
            
            left = new CompositeExpr(left, Symbol.AND, right);
        }
        return left;
    }
    
    private boolean checkBoolean(Type left, Type right){
        if("undefined".equals(left.getName()) || "undefined".equals(right.getName()))
            return true;
        else
            return "boolean".equals(left.getName()) && "boolean".equals(right.getName());
    }

    // ExprRel ::= ExprAdd [ RelOp ExprAdd ]
    private Expr exprRel() {
        Expr left, right;
        left = exprAdd();

        Symbol op = lexer.token;
        if (op == Symbol.EQ || op == Symbol.NEQ || op == Symbol.LE || op == Symbol.LT || op == Symbol.GE
                || op == Symbol.GT) {
            lexer.nextToken();
            right = exprAdd();

            if (!checkAssign(left.getType(), right.getType()))
                error.show("conflito de tipos na expressão");
                
            left = new CompositeExpr(left, op, right);
        }
        return left;
    }

    // ExprAdd ::= ExprMult { ( "+" | "-" ) ExprMult }
    private Expr exprAdd() {
        Symbol op;
        Expr left, right;
        left = exprMult();
        
        while ((op = lexer.token) == Symbol.PLUS || op == Symbol.MINUS) {
            lexer.nextToken();
            right = exprMult();

            if (!checkMathExpr(left.getType(), right.getType()))
                error.show("esperado tipo inteiro na expressão");
            
            left = new CompositeExpr(left, op, right);
        }
        return left;
    }
    
    private boolean checkMathExpr(Type left, Type right){
        boolean orLeft = "int".equals(left.getName()) || "undefined".equals(left.getName());
        
        boolean orRight = "int".equals(right.getName()) || "undefined".equals(right.getName());
        
        return orLeft && orRight;
    }

    // ExprMult ::= ExprUnary { ( "*" | "/" ) ExprUnary }
    private Expr exprMult() {
        Expr left, right;
        left = exprUnary();
        
        Symbol op;
        while ((op = lexer.token) == Symbol.MULT || op == Symbol.DIV) {
            lexer.nextToken();
            right = exprUnary();

            if (!checkMathExpr(left.getType(), right.getType()))
                error.show("esperado tipo inteiro na expressão");
            
            left = new CompositeExpr(left, op, right);
        }
        return left;
    }
    
    //ExprUnary ::= [ ( "+" | "-" ) ] ExprPrimary
    private Expr exprUnary() {
        Symbol op;
        if((op = lexer.token) == Symbol.PLUS || op == Symbol.MINUS){
            lexer.nextToken();
        } 
        
        return exprPrimary();
    }
    
    //ExprPrimary ::= Id | FuncCall | ExprLiteral
    private Expr exprPrimary() {
        Expr e = null;
        if(lexer.token == Symbol.IDENT){
            String name = (String) lexer.getStringValue();
            lexer.nextToken();

            if(lexer.token == Symbol.LEFTPAR){
                lexer.nextToken();
                
                if(symbolTable.getInGlobal(name) == null)
                    error.show("função usada ainda não declarada");

                e = funcCall(name);
            }else{
                Variable v = (Variable) symbolTable.getInLocal(name);
                e = new VariableExpr(name);
                
                if(v == null)
                    error.show("variável usada ainda não declarada");
                else
                    e.setType(v.getType());
            }
        }else
            e = exprLiteral();
        
        return e; 
    }
    
    //ExprLiteral ::= LiteralInt | LiteralBoolean | LiteralString
    private Expr exprLiteral() {
        Expr e = null;
        if(lexer.token.equals(Symbol.LITERALINT)){
            e = new ExprLiteral(new IntType());
        }else if(lexer.token.equals(Symbol.LITERALSTRING)){
            e = new ExprLiteral(new StringType());
        }else if (lexer.token.equals(Symbol.LITERALBOOLEAN) || lexer.token.equals(Symbol.TRUE) || lexer.token.equals(Symbol.FALSE)){
            e = new ExprLiteral(new BooleanType());
        }else{
            error.show("literal não identificado");
            e = new ExprLiteral(new UndefinedType());
        }
        
        lexer.nextToken();
        return e;
    }
    
    // ParamList ::= ParamDec { "," ParamDec }
    private ParamList paramList() {
        ParamList paramList = new ParamList();
        paramDec(paramList);
        
        while (lexer.token == Symbol.COMMA) {
            lexer.nextToken();
            paramDec(paramList);
        }
        
        return paramList;
    }

    // ParamDec ::= Type Id
    private void paramDec(ParamList paramList) {
        Param v = null;
        Type typeVar = type();

        if (lexer.token != Symbol.IDENT)
            error.show("Id esperado");
        
        String name = (String) lexer.getStringValue();
        
        lexer.nextToken();
        
        if (symbolTable.getInLocal(name) != null)
            error.show("Parâmetro " + name + " já existe");

        v = new Param(name);
        v.setType(typeVar);
        symbolTable.putInLocal(name, v);
        paramList.addElement(v);
    }

    //Type ::= "int" | "boolean" | "String"
    private Type type() {
        Type result;
        switch (lexer.token) {
            case INTEGER:
                result = Type.intType;
                break;
            case BOOLEAN:
                result = Type.booleanType;
                break;
            case TRUE:
                result = Type.booleanType;
                break;
            case FALSE:
                result = Type.booleanType;
                break;
            case STRING:
                result = Type.StringType;
                break;
            default:
                error.show("tipo esperado");
                result = Type.undefinedType;
        }
        lexer.nextToken();
        return result;
    }

    // StatList ::= { Stat }
    private StatList statList() {
        ArrayList<Stat> v = new ArrayList<>();
        
        Symbol s;
        Stat stat;
        
        while ((s = lexer.token) == Symbol.IF || s == Symbol.WHILE || s == Symbol.IDENT || s == Symbol.RETURN || s == Symbol.VAR) {

            stat = stat();

            if (stat != null) {
                v.add(stat);
            }
        }

        if(lexer.token != Symbol.CURLYRIGHTBRACE && lexer.token != Symbol.ENDIF && lexer.token != Symbol.ENDW && lexer.token != Symbol.ELSE){
            error.show("statement inválido");
            lexer.nextToken();
        }
        
        return new StatList(v);
    }

    // Stat ::= AssignExprStat | ReturnStat | VarDecStat | IfStat | WhileStat
    private Stat stat() {
        switch (lexer.token) {
            case RETURN:
                return returnStat();
            case VAR:
                return varDecStat();
            case IF:
                return ifStat();
            case WHILE:
                return whileStat();
            case IDENT:
                return assignExprStat();
            default:
                error.show("statement inválido");
                lexer.nextToken();
                return null;
        }
    }

    //AssignExprStat ::= Expr [ "=" Expr ] ";"
    private AssignExprStat assignExprStat() {

        Expr left = expr();
        Expr right = null;

        if ( lexer.token == Symbol.ASSIGN ){
            lexer.nextToken();
            right = expr();
            
            if (!checkAssign(left.getType(), right.getType()))
                error.show("tipos das expressões são diferentes: " + left.getType().getName() + " e " + right.getType().getName());
                
        }
        
        if (lexer.token != Symbol.SEMICOLON){
            error.show("; esperado");
        }else
            lexer.nextToken();
        
        return new AssignExprStat( left, right );
    }
    
    //IfStat ::= "if" Expr "then" StatList [ "else" StatList ] "endif"
    private IfStat ifStat() {
        lexer.nextToken();
        Expr e = expr();
        
       if (e.getType() != Type.booleanType)
            error.show("expressão não é do tipo booleana");

        if (lexer.token != Symbol.THEN)
            error.show("then esperado");

        lexer.nextToken();
        StatList thenPart = statList();
        StatList elsePart = null;

        if (lexer.token == Symbol.ELSE) {
            lexer.nextToken();
            elsePart = statList();
        }
        if (lexer.token != Symbol.ENDIF)
            error.show("\"endif\" esperado");

        lexer.nextToken();
        return new IfStat(e, thenPart, elsePart);
    }

    //WhileStat ::= "while" Expr "do" StatList "endw"
    private Stat whileStat() {
        lexer.nextToken();

        Expr expr = expr();

        if (!checkWhileExpr(expr.getType()))
           error.show("expressão não é do tipo booleana");
            
        if (lexer.token != Symbol.DO)
            error.show("do esperado");
        
        lexer.nextToken();
        
        StatList doPart = statList();
        
        if (lexer.token != Symbol.ENDW)
            error.show("\"endw\" esperado");
        
        lexer.nextToken();

        return new WhileStat(expr, doPart);
    }
    
    private boolean checkWhileExpr(Type t){
        return "undefined".equals(t.getName()) || "boolean".equals(t.getName());
    }

    //ReturnStat ::= "return" Expr ";"
    private ReturnStat returnStat() {
        lexer.nextToken();
        Expr e = expr();

        if (!checkAssign(currentFunction.getReturnType(), e.getType()))
            error.show("Retorno é diferente do tipo de retorno da função");
        
        if (lexer.token != Symbol.SEMICOLON)
            error.show("; esperado");
        
        lexer.nextToken();
        
        return new ReturnStat(e);
    }
    
    private boolean checkAssign(Type left, Type right){
        return left.getName().equals(right.getName()) || "undefined".equals(left.getName()) || "undefined".equals(right.getName());
    }

    //VarDecStat ::= "var" Type Id ";"
    private VarDecStat varDecStat() {
        lexer.nextToken();

        Type typeVar = type();

        if (lexer.token != Symbol.IDENT)
            error.show("id esperado");
        
        String name = (String) lexer.getStringValue();
        
        if(symbolTable.getInLocal(name) != null)
            error.show("variável já foi declarada");

        lexer.nextToken();

        Variable va = new Variable(name);
        va.setType(typeVar);
        symbolTable.putInLocal(name, va);
        
        if (lexer.token != Symbol.SEMICOLON)
            error.show("; esperado");
        
        lexer.nextToken();
        
        return new VarDecStat(typeVar, name);
    }

    //FuncCall ::= Id "(" [ Expr { "," Expr } ] ")"
    private FuncCall funcCall(String id) {
        ExprList anExprList = null;
        Func p = (Func) symbolTable.getInGlobal(id);

        //Estamos verificando se p != null pois ha risco de ocorrer uma NULLPOINTEREXCEPTION
        
        if (lexer.token != Symbol.RIGHTPAR) {
            if(p != null)
                anExprList = exprList(p.getParamList());

            if (lexer.token != Symbol.RIGHTPAR){
                error.show(") faltando");
            }else
                lexer.nextToken();
        } else {
            if(p != null)
                if (p.getParamList() != null && p.getParamList().getSize() != 0)
                    error.show("parâmetro esperado na chamada de função");
            
            lexer.nextToken();
        }
        return new FuncCall(p, anExprList);
    }
    
    private ExprList exprList(ParamList paramList){
    
        ExprList anExprList;
        boolean firstErrorMessage = true;
        
        if(lexer.token == Symbol.RIGHTPAR)
            return null;
        else{
            Param param;
            int sizeParamList = paramList.getSize();
            Iterator e = paramList.getParamList().iterator();
            anExprList = new ExprList();
            
            while(true){

                if(e.hasNext())
                    param = (Param) e.next(); 
                else
                    param = null;

                if(sizeParamList < 1 && firstErrorMessage){
                    error.show("número de parâmetros errado na chamada da função");
                    firstErrorMessage = false;
                }
                sizeParamList--;
                Expr anExpr = expr();

                if(!checkAssign(param.getType(), anExpr.getType())){
                    error.show("tipo errado na passagem do parâmetro");
                }
                anExprList.addElement(anExpr);
                if(lexer.token == Symbol.COMMA){
                    lexer.nextToken();
                }else
                    break;
        }
        if((sizeParamList > 0 || sizeParamList < 0) && firstErrorMessage)
            error.show("número de parâmetros errado na chamada da função");
        
        return anExprList;
        }
    }
}
