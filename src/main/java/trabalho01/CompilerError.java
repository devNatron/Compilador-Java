
package trabalho01;

import Lexer.*; 
import java.io.*;

public class CompilerError {
    
    private Lexer lexer;
    PrintWriter out;
    private boolean thereWasAnError;
    
    public CompilerError( PrintWriter out ) {
        this.out = out;
    }
    
    public CompilerError(Lexer lexer, PrintWriter out ) {
        this.out = out;
        this.lexer = lexer;
    }
    
    public void setLexer( Lexer lexer ) {
        this.lexer = lexer;
    }
    
    public void show( String strMessage ) {
        show( strMessage, false );
    }
    
    public void show( String strMessage, boolean goPreviousToken ) {
        if ( goPreviousToken ) {
            out.println("Error at line " + lexer.getLineNumberBeforeLastToken() + ": ");
            out.println( lexer.getLineBeforeLastToken() );
        }
        else {
            out.println("Error at line " + lexer.getLineNumber() + ": ");
            out.println(lexer.getCurrentLine());
        }
        out.println( strMessage );
        out.flush();
        if ( out.checkError() )
            System.out.println("Error in signaling an error");
        thereWasAnError = true;
    }
    
    public boolean wasAnErrorSignalled() {
        return thereWasAnError;
    }
    
    public void signal( String strMessage ) {
        out.println("Error at line " + lexer.getLineNumber() + ": ");
        out.println(lexer.getCurrentLine());
        out.println( strMessage );
        if ( out.checkError() )
            System.out.println("Error in signaling an error");
        throw new RuntimeException(strMessage);
    }

}