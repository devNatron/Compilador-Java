/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexer;

import java.util.Hashtable;
import trabalho01.*;

/**
 *
 * @author super
 */
public class Lexer {
    
    static private Hashtable<String, Symbol> keywordsTable;
    public Symbol token;
    private String stringValue;
    private int numberValue;
    public int tokenPos;
    private int lastTokenPos;
    public char input [];
    private int lineNumber;
    private int beforeLastTokenPos;
    
    static{
        keywordsTable = new Hashtable<String, Symbol>();
        keywordsTable.put("var", Symbol.VAR);
        keywordsTable.put("if", Symbol.IF);
        keywordsTable.put("else", Symbol.ELSE);
        keywordsTable.put("then", Symbol.THEN);
        keywordsTable.put("endif", Symbol.ENDIF);
        keywordsTable.put("read", Symbol.READ);
        keywordsTable.put("write", Symbol.WRITE);
        keywordsTable.put("begin", Symbol.BEGIN);
        keywordsTable.put("end", Symbol.END);
        keywordsTable.put("readInt", Symbol.READINT);
        keywordsTable.put("readString", Symbol.READSTRING);
        keywordsTable.put("print", Symbol.PRINT);
        keywordsTable.put("println", Symbol.PRINTLN);
    }
    
    CompilerError error;
    
    public Lexer(char input[], CompilerError error){
        this.input = input;
        input[input.length - 1] = '\0';
        tokenPos = 0;
        lineNumber = 1;
        lastTokenPos = 0;
        beforeLastTokenPos = 0;
        this.error = error;
    }
    
    public void skipPunctuation() {
        while ( token != Symbol.EOF && ( token == Symbol.COLON || token == Symbol.COMMA || token == Symbol.SEMICOLON) )
            nextToken();
        if ( token == Symbol.EOF )
            error.signal("Unexpected EOF");
    }
    
    public void skipBraces() {
        // skip any of the symbols [ ] { } ( )
        if ( token == Symbol.CURLYLEFTBRACE || token == Symbol.CURLYRIGHTBRACE ||
        token == Symbol.LEFTSQBRACKET || token == Symbol.RIGHTSQBRACKET )
            nextToken();
        if ( token == Symbol.EOF )
            error.signal("Unexpected EOF");
    }
    
    public int getLineNumber() {
        return lineNumber;
    }
    
    public String getStringValue() {
        return stringValue;
    }
    
    private String getLine( int index ) {
        int i = index;
        if ( i == 0 )
            i = 1;
        else
            if ( i >= input.length )
            i = input.length;
        StringBuffer line = new StringBuffer();

        while ( i >= 1 && input[i] != '\n' )
            i--;
        if ( input[i] == '\n' )
            i++;
        // go to the end of the line putting it in variable line
        while ( input[i] != '\0' && input[i] != '\n' && input[i] != '\r' ) {
            line.append( input[i] );
            i++;
        }
        return line.toString();
    }
    
    public String getCurrentLine() {
        return getLine(lastTokenPos);
    }
    
    public String getLineBeforeLastToken() {
        return getLine(beforeLastTokenPos);
    }
    
    public int getLineNumberBeforeLastToken() {
        return getLineNumber( beforeLastTokenPos );
    }
    
    private int getLineNumber( int index ) {
        int i, n, size;
        n = 1;
        i = 0;
        size = input.length;
        while ( i < size && i < index ) {
            if ( input[i] == '\n' )
                n++;
            i++;
        }
        return n;
    }
    
    public void nextToken(){
        while(tokenPos < input.length && (input[tokenPos] == ' '  || input[tokenPos] == '\n'
            || input[tokenPos] == '\t' || input[tokenPos] == '\r')){
                if(input[tokenPos] == '\n')
                    lineNumber++;
                    
                tokenPos++;
		}
		
		if(tokenPos >= input.length - 1){
            token = Symbol.EOF;
			return;
        }
                
        if(input[tokenPos] == '/' && input[tokenPos+1] == '/'){
            while(tokenPos < input.length && input[tokenPos] != '\n'){
                tokenPos++;
            }
            if(tokenPos == input.length){
                token = Symbol.EOF;
                return;
            }
            lineNumber++;
            nextToken();
        }
                
        String buffer = "";
        
        if(Character.isLetter(input[tokenPos])){
            while(Character.isLetter(input[tokenPos])){
                buffer += input[tokenPos];
                tokenPos++;
            }
            
            if(buffer != ""){
                token = keywordsTable.get(buffer);
                if(token == null){
                    token = Symbol.IDENT;
                    stringValue = buffer;
                }
            }
        }else if (Character.isDigit(input[tokenPos])){
            while(Character.isDigit(input[tokenPos])){
                buffer += input[tokenPos];
                tokenPos++;
            }
            
            numberValue = Integer.parseInt(buffer);
            
            //if(numberValue > MaxValueInteger)
                //error("overflow int");
            
            token = Symbol.NUMBER;
        } else {
            switch(input[tokenPos]){
                case '+':
                    token = Symbol.PLUS;
                break;
                case '-':
                    token = Symbol.MINUS;
                break;
                case '*':
                    token = Symbol.MULT;
                break;
                case '/':
                    token = Symbol.DIV;
                break;
                case '<':
                    if(input[tokenPos+1] == '='){
                        token = Symbol.LE;
                        tokenPos++;
                    }else
                        token = Symbol.LT;
                break;
                case '>':
                    if(input[tokenPos+1] == '='){
                        token = Symbol.GE;
                        tokenPos++;
                    }else
                        token = Symbol.GT;
                break;
                case '!':
                    if(input[tokenPos+1] == '='){
                        token = Symbol.NEQ;
                        tokenPos++;
                    }else
                        error.show("falta =");
                break;
                case '=':
                    if(input[tokenPos+1] == '='){
                        token = Symbol.EQ;
                        tokenPos++;
                    }else
                        token = Symbol.ASSIGN;                           
                break;
                case ')':
                    token = Symbol.RIGHTPAR;
                break;
                case '(':
                    token = Symbol.LEFTPAR;
                break;
                case ',':
                    token = Symbol.COMMA;
                break;
                case ';':
                    token = Symbol.SEMICOLON;
                break;
                default:
                    error.show("inválido na gramática");
                break;
            }
            tokenPos++;
        }
    }
}
