/*
    José Vitor Novaes Santos                RA: 743556
    Marcus Vinicius Natrielli Garcia        RA: 743578
    Victor Fernandes de Oliveira Brayner    RA: 743600
*/

package Lexer;

import trabalho.CompilerError;
import java.util.Hashtable;


public class Lexer {
    
    static private Hashtable<String, Symbol> keywordsTable;
    public Symbol token;
    public String stringValue;
    private int numberValue;
    public int tokenPos;
    private int lastTokenPos;
    public char input [];
    private int lineNumber;
    private int beforeLastTokenPos;
    
    static{
        keywordsTable = new Hashtable<String, Symbol>();
        keywordsTable.put("var", Symbol.VAR);
        keywordsTable.put("def", Symbol.DEF);
        keywordsTable.put("if", Symbol.IF);
        keywordsTable.put("else", Symbol.ELSE);
        keywordsTable.put("then", Symbol.THEN);
        keywordsTable.put("endif", Symbol.ENDIF);
        keywordsTable.put("while", Symbol.WHILE);
        keywordsTable.put("do", Symbol.DO);
        keywordsTable.put("endw", Symbol.ENDW);
        keywordsTable.put("read", Symbol.READ);
        keywordsTable.put("write", Symbol.WRITE);
        keywordsTable.put("begin", Symbol.BEGIN);
        keywordsTable.put("end", Symbol.END);
        keywordsTable.put("true", Symbol.TRUE);
        keywordsTable.put("false", Symbol.FALSE);
        keywordsTable.put("int", Symbol.INTEGER);
        keywordsTable.put("boolean", Symbol.BOOLEAN);
        keywordsTable.put("String", Symbol.STRING);
        keywordsTable.put("return", Symbol.RETURN);
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
    
    public int getNumberValue() {
        return numberValue;
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
            return;
        }
        
        String buffer = "";

        if(Character.isLetter(input[tokenPos])){
            while(Character.isLetter(input[tokenPos]) || Character.isDigit(input[tokenPos])){
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
            
            try {
                numberValue = Integer.parseInt(buffer);
            } catch( NumberFormatException e){
                error.show("número fora dos limites");
            }
            if(numberValue > 2147483647 || numberValue < 0)
                error.show("número fora dos limites");
            
            token = Symbol.LITERALINT;
        } else if(input[tokenPos] == '"'){
            tokenPos++;

            while(input[tokenPos] != '"'){
                buffer += input[tokenPos];
                tokenPos++;
            }
            token = Symbol.LITERALSTRING;
            tokenPos++;
            stringValue = buffer;
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
                case '{':
                    token = Symbol.CURLYLEFTBRACE;
                break;
                case '}':
                    token = Symbol.CURLYRIGHTBRACE;
                break;
                case '[':
                    token = Symbol.LEFTSQBRACKET;
                break;
                case ']':
                    token = Symbol.RIGHTSQBRACKET;
                break;
                case ':':
                    token = Symbol.COLON;
                break;
                case '|':
                    if(input[tokenPos+1] == '|'){
                        token = Symbol.OR;
                        tokenPos++;
                    }else
                        error.show("inválido na gramática");
                break;
                case '&':
                    if(input[tokenPos+1] == '&'){
                        token = Symbol.AND;
                        tokenPos++;
                    }else
                        error.show("inválido na gramática");
                break;
                default:
                    error.show("inválido na gramática");
                break;
            }
            tokenPos++;
            beforeLastTokenPos = lastTokenPos;
            lastTokenPos = tokenPos - 1;
        }
    }
}
