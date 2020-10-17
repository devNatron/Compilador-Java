/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexer;

/**
 *
 * @author super
 */
public enum Symbol {
    EOF("eof"),
    IDENT("identificador"),
    NUMBER("number"),
    PLUS("+"),
    MINUS("-"),
    MULT("*"),
    DIV("/"),
    LT("<"),
    LE("<="),
    GT(">"),
    GE(">="),
    NEQ("!="),
    EQ("=="),
    ASSIGN("="),
    LEFTPAR("("),
    RIGHTPAR(")"),
    SEMICOLON(";"),
    VAR("var"),
    BEGIN("begin"),
    END("end"),
    IF("if"),
    THEN("then"),
    ELSE("else"),
    ENDIF("endif"),
    COMMA(","),
    READ("read"),
    WRITE("write"),
    AND("&&"),
    OR("||"),
    RETURN("return"),
    WHILE("while"),
    INTEGER("integer"),
    BOOLEAN("boolean"),
    STRING("string"),
    COLON(":"),
    READINT("readInt"),
    READSTRING("readString"),
    PRINT("print"),
    PRINTLN("println"),
    ENDW("endw"),
    DO("do"),
    CURLYLEFTBRACE("{"),
    CURLYRIGHTBRACE("}"),
    LEFTSQBRACKET("["),
    RIGHTSQBRACKET("]"),
    DEF("def"),
    TRUE("true"),
    FALSE("false"),
    LITERALINT("literalInt"),
    LITERALSTRING("literalstring"),
    LITERALBOOLEAN("literalboolean");
        
    public String nome;
    
    Symbol(String nome){
        this.nome = nome;
    }
    
    public String toString(){
        return nome;
    }
}
