package trabalho01;

import AST.*;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;

public class Compiler {

    private char token;
    private int tokenPos;
    private char input[];

    public Program compile(char m_input[], PrintWriter PW) {
        input = m_input;
        tokenPos = 0;
        nextToken();

        // symbolTable = new Hashtable();

        Program p = program();
        if (tokenPos != input.length)
            error();

        return p;
    }

    private Program program() {
        // ArrayList<variable> esq = varDecList();

        // Expr dir = null;

        if (token == ':') {
            nextToken();
            // dir = expr();
        } else
            error();

        // return new Program(esq, dir);
        return null;
    }

    // Expr ::= ExprAnd { "||" ExprAnd }
    private Expr expr() {
        Expr left, right;
        left = ExprAnd();
        if (lexer.token == Symbol.OR) {
            lexer.nextToken();
            right = ExprAnd();
            // // semantic analysis
            // if (left.getType() != Type.booleanType || right.getType() !=
            // Type.booleanType)
            // error.signal("Expression of boolean type expected");
            left = new CompositeExpr(left, Symbol.OR, right);
        }
        return left;
    }

    // ExprAnd ::= ExprRel { "&&" ExprRel }
    private Expr ExprAnd() {
        Expr left, right;
        left = ExprRel();
        if (lexer.token == Symbol.AND) {
            lexer.nextToken();
            right = ExprRel();
            // // semantic analysis
            // if (left.getType() != Type.booleanType || right.getType() !=
            // Type.booleanType)
            // error.signal("Expression of boolean type expected");
            left = new CompositeExpr(left, Symbol.AND, right);
        }
        return left;
    }

    // ExprRel ::= ExprAdd [ RelOp ExprAdd ]
    private Expr ExprRel() {
        Expr left, right;
        left = ExprAdd();

        Symbol op = lexer.token;
        if (op == Symbol.EQ || op == Symbol.NEQ || op == Symbol.LE || op == Symbol.LT || op == Symbol.GE
                || op == Symbol.GT) {
            lexer.nextToken();
            right = ExprAdd();

            // // semantic analysis
            // if (left.getType() != right.getType())
            // error.signal("Type error in expression");

            left = new CompositeExpr(left, op, right);
        }
        return left;
    }

    // ExprAdd ::= ExprMult { ( "+" | "-" ) ExprMult }
    private Expr ExprAdd() {
        Symbol op;
        Expr left, right;
        left = ExprMult();
        while ((op = lexer.token) == Symbol.PLUS || op == Symbol.MINUS) {
            lexer.nextToken();
            right = ExprMult();

            // // semantic analysis
            // if (left.getType() != right.getType())
            // error.signal("Type error in expression");
            left = new CompositeExpr(left, op, right);
        }
        return left;
    }

    // ExprMult ::= ExprUnary { ( "*" | "/" ) ExprUnary }
    private Expr ExprMult() {
        Expr left, right;
        left = ExprUnary();
        Symbol op;
        while ((op = lexer.token) == Symbol.MULT || op == Symbol.DIV) {
            lexer.nextToken();
            right = ExprUnary();
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
        Parameter v;
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

        v = new Parameter(name);

        symbolTable.putInLocal(name, v);
        paramList.add(v);
    }

    private Type type() {
        Type result;
        switch (lexer.token) {
            case INT:
                result = Type.intType;
                break;
            case BOOLEAN:
                result = Type.booleanType;
                break;
            case CHAR:
                result = Type.charType;
                break;
            default:
                error.show("Type expected");
                result = Type.intType;
        }
        lexer.nextToken();
        return result;
    }

    // StatList ::= { Stat }
    private StatList statementList() {
        Symbol s;
        Stat stat;
        ArrayList<Stat> v = new ArrayList<Stat>();
        while ((s = lexer.token) != Symbol.ELSE && s != Symbol.ENDIF && s != Symbol.ENDW) {
            stat = statement();

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
    private Stat statement() {
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
        StatList thenPart = statementList();
        StatList elsePart = null;

        if (lexer.token == Symbol.ELSE) {
            lexer.nextToken();
            elsePart = statementList();
        }
        if (lexer.token != Symbol.ENDIF)
            error.signal("\"endif\" expected");

        lexer.nextToken();
        return new IfStat(e, thenPart, elsePart);
    }

    private Stat whileStat() {
        lexer.nextToken();

        Expr expr = expr();

        if (!checkWhileExpr(expr.getType()))
            error.show("Boolean expression expected");
        if (lexer.token != Symbol.DO)
            error.show("do expected");
        else
            lexer.nextToken();

        return new WhileStat(expr, statement());
    }

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
    private FuncCall functionCall() {
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

    // Analisador lÃ©xico
    private void nextToken() {
        while (tokenPos < input.length && input[tokenPos] == ' ') {
            tokenPos++;
        }

        if (tokenPos >= input.length)
            token = '\0';
        else {
            token = input[tokenPos];
            tokenPos++;
        }
    }

    public void error() {
        if (tokenPos == 0)
            tokenPos = 1;
        else if (tokenPos >= input.length)
            tokenPos = input.length;

        String stInput = new String(input, tokenPos - 1, input.length - tokenPos + 1);
        String stError = "Error at \"" + stInput + "\"";
        System.out.println(stError);
        throw new RuntimeException(stError);
    }

}
