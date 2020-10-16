package trabalho01;

import AST.*;
import Lexer.*;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;

public class Compiler {
    
    private Lexer lexer;

    public Program compile(char m_input[], PrintWriter PW) {
        lexer = new Lexer(m_input);
        lexer.nextToken();

        // symbolTable = new Hashtable();

        Program p = program();
        if (lexer.tokenPos != lexer.input.length)
            error();

        return p;
    }

    //Program ::= Func { Func }
    private Program program() {
        // ArrayList<variable> esq = varDecList();

        // Expr dir = null;

        if (lexer.token == Symbol.COLON) {
            lexer.nextToken();
            // dir = expr();
        } else
            error();

        // return new Program(esq, dir);
        return null;
    }

    // Expr ::= ExprAnd { "||" ExprAnd }
    private Expr expr() {
        Expr left, right;
        left = exprAnd();
        if (lexer.token == Symbol.OR) {
            lexer.nextToken();
            right = exprAnd();
            // // semantic analysis
            // if (left.getType() != Type.booleanType || right.getType() !=
            // Type.booleanType)
            // error.signal("Expression of boolean type expected");
            left = new CompositeExpr(left, Symbol.OR, right);
        }
        return left;
    }

    // ExprAnd ::= ExprRel { "&&" ExprRel }
    private Expr exprAnd() {
        Expr left, right;
        left = exprRel();
        if (lexer.token == Symbol.AND) {
            lexer.nextToken();
            right = exprRel();
            // // semantic analysis
            // if (left.getType() != Type.booleanType || right.getType() !=
            // Type.booleanType)
            // error.signal("Expression of boolean type expected");
            left = new CompositeExpr(left, Symbol.AND, right);
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
            if (left.getType() != Type.intType || right.getType() != Type.intType)
                error.signal("Expression of type integer expected");
            left = new CompositeExpr(left, op, right);
        }
        return left;
    }

    // ParamList ::= ParamDec { "," ParamDec }
    private ParamList paramList() {
        ParamList paramList = null;
        if (lexer.token == Symbol.IDENT) {
            paramList = new ParamList();
            paramDec(paramList);
            while (lexer.token == Symbol.COMMA) {
                lexer.nextToken();
                paramDec(paramList);
            }
        }
        return paramList;
    }

    // ParamDec ::= Type Id
    private void paramDec(ParamList paramList) {
        Param v;
        Type typeVar = type();
        v.setType(typeVar);

        lexer.nextToken(); // não sei precisa

        if (lexer.token != Symbol.IDENT)
            error.signal("Identifier expected");
        String name = (String) lexer.getStringValue();
        lexer.nextToken();

        // // semantic analysis
        // if (symbolTable.getInLocal(name) != null)
        // error.show("Parameter " + name + " has already been declared");

        v = new Param(name);

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
            case STRING:
                result = Type.StringType;
                break;
            default:
                error.show("Type expected");
                result = Type.intType;
        }
        lexer.nextToken();
        return result;
    }

    // StatList ::= { Stat }
    private StatList statList() {
        Symbol s;
        Stat stat;
        ArrayList<Stat> v = new ArrayList<Stat>();
        while ((s = lexer.token) != Symbol.ELSE && s != Symbol.ENDIF && s != Symbol.ENDW) {
            stat = Stat();

            if (stat != null) {
                v.add(stat);
                if (lexer.token != Symbol.SEMICOLON) {
                    error.show("; expected", true);
                    lexer.skipPunctuation();
                } else
                    lexer.nextToken();
            }
        }
        return new StatList(v);
    }

    // Stat ::= AssignExprStat | ReturnStat | VarDecStat | IfStat | WhileStat
    private Stat stat() {
        switch (lexer.token) {
            case IDENT:
                if (symbolTable.get(lexer.getStringValue()) instanceof Func)
                    return functionCall();
                else
                    return assignExprStat();
            case RETURN:
                return returnStat();
            case VAR:
                return varDecStat();
            case IF:
                return ifStat();
            case WHILE:
                return whileStat();
            default:
                error.show("Statement expected");
                // throw new StatementException();
        }
    }

    //IfStat ::= "if" Expr "then" StatList [ "else" StatList ] "endif"
    private IfStat ifStat() {
        lexer.nextToken();
        Expr e = expr();
        // semantic analysis
        // check if expression has type boolean
        if (e.getType() != Type.booleanType)
            error.signal("Boolean type expected in if expression");

        if (lexer.token != Symbol.THEN)
            error.signal("then expected");

        lexer.nextToken();
        StatList thenPart = statList();
        StatList elsePart = null;

        if (lexer.token == Symbol.ELSE) {
            lexer.nextToken();
            elsePart = statList();
        }
        if (lexer.token != Symbol.ENDIF)
            error.signal("\"endif\" expected");

        lexer.nextToken();
        return new IfStat(e, thenPart, elsePart);
    }

    //WhileStat ::= "while" Expr "do" StatList "endw"
    private Stat whileStat() {
        lexer.nextToken();

        Expr expr = expr();

        if (!checkWhileExpr(expr.getType()))
            error.show("Boolean expression expected");
        if (lexer.token != Symbol.DO)
            error.show("do expected");
        else
            lexer.nextToken();

        return new WhileStat(expr, Stat());
    }

    //ReturnStat ::= "return" Expr ";"
    private ReturnStat returnStat() {
        lexer.nextToken();
        Expr e = expr();
        // // semantic analysis
        // // Are we inside a function ?
        // if (currentFunction == null)
        // error.show("return statement inside a procedure");
        // else if (!checkAssignment(currentFunction.getReturnType(), e.getType()))
        // error.show("Return type does not match function type");
        return new ReturnStat(e);
    }

    //VarDecStat ::= "var" Type Id ";"
    private VarDecStat varDecStat() {
        lexer.nextToken();
        Type typeVar = type();

        if (lexer.token != Symbol.IDENT)
            error.signal("Identifier expected");
        String name = (String) lexer.getStringValue();
        lexer.nextToken();

        VarDecStat v = new VarDecStat(typeVar, name);

        // // semantic analysis
        if (symbolTable.putInLocal(name, v) != null) {
            error.show("Variable " + name + " has already been declared");
        }

        return v;
    }

    //FuncCall ::= Id "(" [ Expr { "," Expr } ] ")"
    private FuncCall funcCall() {
        ExprList anExprList = null; //não faço ideia do q seja
        String name = (String) lexer.getStringValue();
        lexer.nextToken();
        Func p = (Func) symbolTable.getInGlobal(name);

        if (lexer.token != Symbol.LEFTPAR) {
            error.show("( expected");
            lexer.skipBraces();
        } else
            lexer.nextToken();

        if (lexer.token != Symbol.RIGHTPAR) {
            // The parameter list is used to check if the arguments to the
            // procedure have the correct types
            anExprList = exprList(p.getParamList());
            if (lexer.token != Symbol.RIGHTPAR)
                error.show("Error in expression");
            else
                lexer.nextToken();
        } else {
            // semantic analysis
            // does the procedure has no parameter ?
            if (p.getParamList() != null && p.getParamList().getSize() != 0)
                error.show("Parameter expected");
            lexer.nextToken();
        }
        return new FuncCall(p, anExprList);
    }

    public void error() {
        if (lexer.tokenPos == 0)
            lexer.tokenPos = 1;
        else if (lexer.tokenPos >= lexer.input.length)
            lexer.tokenPos = lexer.input.length;

        String stInput = new String(lexer.input, lexer.tokenPos - 1, lexer.input.length - lexer.tokenPos + 1);
        String stError = "Error at \"" + stInput + "\"";
        System.out.println(stError);
        throw new RuntimeException(stError);
    }

}
