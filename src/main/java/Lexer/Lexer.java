/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexer;

import java.util.Hashtable;

/**
 *
 * @author super
 */
public class Lexer {
    
    static private Hashtable<String, Symbol> keywordsTable;
    private Symbol token;
    private String stringValue;
    private int numberValue;
    private int tokenPos;
    private char input [];
    private int lineNumber;
    
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
    }
    
    public Lexer(char input[], CompilerError error){
    
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
            
            if(numberValue > MaxValueInteger)
                error("overflow int");
            
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
                        error("falta =");
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
                    error("inválido na gramática");
                break;
            }
            tokenPos++;
        }
    }
}
