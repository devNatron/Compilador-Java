/*
    José Vitor Novaes Santos                RA: 743556
    Marcus Vinicius Natrielli Garcia        RA: 743578
    Victor Fernandes de Oliveira Brayner    RA: 743600
*/

package trabalho01;

import Lexer.*; 
import java.io.*;

public class CompilerError {
    
    private Lexer lexer;
    PrintWriter out;
    private boolean thereWasAnError;
    String fileName;
    
    public CompilerError( PrintWriter out ) {
        this.out = out;
    }
    
    public CompilerError(Lexer lexer, PrintWriter out , String fileName) {
        this.out = out;
        this.lexer = lexer;
        this.fileName = fileName;
    }
    
    public void setLexer( Lexer lexer ) {
        this.lexer = lexer;
    }
    
    public void show( String strMessage ) {
        show( strMessage, false );
    }
    
    public void show( String strMessage, boolean goPreviousToken ) {
        if ( goPreviousToken ) {
            out.println("\n " + fileName + ":" + lexer.getLineNumberBeforeLastToken() + ":" + strMessage);
            out.println( lexer.getLineBeforeLastToken() );
        }
        else {
            out.println("\n" + fileName + ":" + lexer.getLineNumber() + ":" + strMessage);
            out.println(lexer.getCurrentLine());
        }
        out.flush();
        if ( out.checkError() )
            System.out.println("Erro ao printar erro");
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
