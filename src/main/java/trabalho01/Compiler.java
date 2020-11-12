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
import java.util.Hashtable;

public class Compiler {
    
    private Lexer lexer;
    private Hashtable<String, Variable> symbolTable;
    private CompilerError error;

    public Program compile(char m_input[], PrintWriter PW) {

        error = new CompilerError( lexer, new PrintWriter(PW) );
        lexer = new Lexer(m_input, error);
        symbolTable = new Hashtable();
        error.setLexer(lexer);
        
        lexer.nextToken();

        Program p = program();
        
        if ( error.wasAnErrorSignalled() )
            return null;
         else
            return p;
    }

    //Program ::= Func { Func }
    private Program program() {
        ArrayList<Func> funcs = new ArrayList<>();
        
        funcs.add(func());
        
        while(lexer.token != Symbol.EOF){
            Func f = func();
            if(f != null)
                funcs.add(f);
            else
                break;
        }

        return new Program(funcs);
    }

    // Func ::= "def" Id [ "(" ParamList ")" ] [ ":" Type ] "{" StatList "}"
    private Func func(){
        Func f = null;

        if(lexer.token == Symbol.DEF){
            lexer.nextToken();
            if(lexer.token == Symbol.IDENT){
                f = new Func(lexer.getStringValue());
                lexer.nextToken();
                
                if(lexer.token == Symbol.LEFTPAR){
                    lexer.nextToken();
                    ParamList pl = paramList();

                    if(lexer.token == Symbol.RIGHTPAR){
                        lexer.nextToken();
                        f.setParamList(pl);
                    }else
                        error.show(") esperado");
                }

                if(lexer.token == Symbol.COLON){
                    lexer.nextToken();
                    Type t = type();
                    f.setReturnType(t);
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
        //Por algum motivo, este nextToken evita loop eterno
        //lexer.nextToken();
        return f;
    }
    
    // Expr ::= ExprAnd { "||" ExprAnd }
    private Expr expr() {
        Expr left, right;
        left = exprAnd();
        
        while(lexer.token == Symbol.OR){
            lexer.nextToken();
            right = exprAnd();
            left = new CompositeExpr(left, Symbol.OR, right);
        }
            // // semantic analysis
            // if (left.getType() != Type.booleanType || right.getType() !=
            // Type.booleanType)
            // error.signal("Expression of boolean type expected");;
        return left;
    }

    // ExprAnd ::= ExprRel { "&&" ExprRel }
    private Expr exprAnd() {
        Expr left, right;
        left = exprRel();
        
        while (lexer.token == Symbol.AND) {
            lexer.nextToken();
            right = exprRel();
            left = new CompositeExpr(left, Symbol.AND, right);
            // // semantic analysis
            // if (left.getType() != Type.booleanType || right.getType() !=
            // Type.booleanType)
            // error.signal("Expression of boolean type expected");
        }
        return left;
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

            // // semantic analysis
            // if (left.getType() != right.getType())
            // error.signal("Type error in expression");

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

            // // semantic analysis
            // if (left.getType() != right.getType())
            // error.signal("Type error in expression");
            left = new CompositeExpr(left, op, right);
        }
        return left;
    }

    // ExprMult ::= ExprUnary { ( "*" | "/" ) ExprUnary }
    private Expr exprMult() {
        Expr left, right;
        left = exprUnary();
        Symbol op;
        while ((op = lexer.token) == Symbol.MULT || op == Symbol.DIV) {
            lexer.nextToken();
            right = exprUnary();
            // semantic analysis
            //if (left.getType() != Type.intType || right.getType() != Type.intType)
                //error.signal("Expression of type integer expected");
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
                e = funcCall(name);
            }else{
                //lexer.nextToken();
                //trocar pra variableExpr()
                //ver composite expr (a + b e etc)
                e = expr();
            }
        }else
            e = exprLiteral();
        
        return e;
    }
    
    //ExprLiteral ::= LiteralInt | LiteralBoolean | LiteralString
    private Expr exprLiteral() {
        Expr e = null;
        if(lexer.token == Symbol.LITERALINT){
            e = new ExprLiteral(new IntType());
        }else if(lexer.token == Symbol.LITERALSTRING){
            e = new ExprLiteral(new StringType());
        }else if (lexer.token == Symbol.LITERALBOOLEAN){
            e = new ExprLiteral(new BooleanType());
        }else{
            System.out.println("Token: ");
            error.show("literal errado");
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
        v = new Param(name);
        v.setType(typeVar);
        
        // // semantic analysis
        // if (symbolTable.getInLocal(name) != null)
        // error.show("Parameter " + name + " has already been declared");

        //symbolTable.putInLocal(name, v);
        paramList.addElement(v);
        lexer.nextToken();
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
            case STRING:
                result = Type.StringType;
                break;
            default:
                error.show("Type esperado");
                result = Type.undefinedType;
        }
        lexer.nextToken();
        return result;
    }

    // StatList ::= { Stat }
    private StatList statList() {
        ArrayList<Stat> v = new ArrayList<Stat>();
        
        Symbol s;
        Stat stat;
        //while ((s = lexer.token) != Symbol.ELSE && s != Symbol.ENDIF && s != Symbol.ENDW) {
        while ((s = lexer.token) == Symbol.IF || s == Symbol.WHILE || s == Symbol.IDENT || s == Symbol.RETURN || s == Symbol.VAR) {
            stat = stat();

            if (stat != null) {
                v.add(stat);
                //lexer.nextToken();
            }
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
                error.show("stat errado");
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
        }
        
        if (lexer.token != Symbol.SEMICOLON)
            error.show("; esperado");
        else
            lexer.nextToken();
        
        return new AssignExprStat( left, right );
    }
    
    //IfStat ::= "if" Expr "then" StatList [ "else" StatList ] "endif"
    private IfStat ifStat() {
        lexer.nextToken();
        Expr e = expr();
       // if (e.getType() != Type.booleanType)
            //error.signal("Boolean type expected in if expression");

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

        //if (!checkWhileExpr(expr.getType()))
           // error.show("Boolean expression expected");
        if (lexer.token != Symbol.DO)
            error.show("do esperado");
        
        lexer.nextToken();
        
        StatList doPart = statList();
        
        if (lexer.token != Symbol.ENDW)
            error.show("\"endw\" esperado");
        
        lexer.nextToken();

        return new WhileStat(expr, doPart);
    }

    //ReturnStat ::= "return" Expr ";"
    private ReturnStat returnStat() {
        lexer.nextToken();
        Expr e = expr();
        // // semantic analysis
        // if (currentFunction == null)
        // error.show("return statement inside a procedure");
        // else if (!checkAssignment(currentFunction.getReturnType(), e.getType()))
        // error.show("Return type does not match function type");
        if (lexer.token != Symbol.SEMICOLON)
            error.show("; esperado");
        
        lexer.nextToken();
        
        return new ReturnStat(e);
    }

    //VarDecStat ::= "var" Type Id ";"
    private VarDecStat varDecStat() {
        lexer.nextToken();

        Type typeVar = type();

        if (lexer.token != Symbol.IDENT)
            error.show("id esperado");
        String name = (String) lexer.getStringValue();
        lexer.nextToken();

        // // semantic analysis
        /*
        if (symbolTable.putInLocal(name, v) != null) {
            error.show("Variable " + name + " has already been declared");
        }
        */
        
        if (lexer.token != Symbol.SEMICOLON)
            error.show("; esperado");
        
        lexer.nextToken();
        
        VarDecStat v = new VarDecStat(typeVar, name);

        return v;
    }

    //FuncCall ::= Id "(" [ Expr { "," Expr } ] ")"
    private FuncCall funcCall(String id) {
        
        ExprList anExprList = null;
        Func f = new Func(id);
        //Func p = (Func) symbolTable.getInGlobal(name);

        if (lexer.token != Symbol.RIGHTPAR) {
            // The parameter list is used to check if the arguments to the
            // procedure have the correct types
            anExprList = new ExprList();
            
            anExprList.addElement(expr());
            
            while(lexer.token == Symbol.COMMA){
                lexer.nextToken();
                anExprList.addElement(expr());
            }
            
            if (lexer.token != Symbol.RIGHTPAR){
                error.show(") faltando");
            }else
                lexer.nextToken();
        } else {
            // semantic analysis
            // does the procedure has no parameter ?
            /*
            if (p.getParamList() != null && p.getParamList().getSize() != 0)
                error.show("Parameter expected");
            */
            lexer.nextToken();
        }
        return new FuncCall(f, anExprList);
    }
}
