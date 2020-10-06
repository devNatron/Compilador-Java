package trabalho01;
import AST.*;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;

public class Compiler {
	
	private char token;
	private int tokenPos;
	private char input [];
	
	public Program compile(char m_input [], PrintWriter PW){
		input = m_input;
		tokenPos = 0;
		nextToken();
                
                //symbolTable = new Hashtable();

                Program p = program();
                if(tokenPos != input.length)
                    error();
                
                return p;
	}
        
        private Program program(){
           // ArrayList<variable> esq = varDecList();
            
            //Expr dir = null;
            
            if(token == ':'){
                nextToken();
               // dir = expr();
            }else
                error();
            
            //return new Program(esq, dir);
            return null;
        }
        
	//Analisador lÃ©xico
	private void nextToken(){
		while(tokenPos < input.length && input[tokenPos] == ' '){
			tokenPos++;
		}
		
		if(tokenPos >= input.length)
			token = '\0';
		else{
			token = input[tokenPos];
			tokenPos++;
		}
	}

	
	public void error(){
		if(tokenPos == 0)
			tokenPos = 1;
		else
			if(tokenPos >= input.length)
				tokenPos = input.length;
				
		String stInput = new String(input, tokenPos - 1, input.length - tokenPos + 1);
		String stError = "Error at \"" + stInput + "\"";
		System.out.println(stError);
		throw new RuntimeException(stError);
	}

}

