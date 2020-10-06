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
        
        //ExprRel ::= ExprAdd [ RelOp ExprAdd ]
        private Expr ExprRel() {
            Expr left, right;
            left = ExprAdd();
            
            Symbol op = lexer.token;
            if ( op == Symbol.EQ || op == Symbol.NEQ || op == Symbol.LE || op == Symbol.LT || op == Symbol.GE || op == Symbol.GT ) {
                lexer.nextToken();
                right = ExprAdd();
            
                // semantic analysis
                if ( left.getType() != right.getType() )
                    error.signal("Type error in expression");
                
                left = new CompositeExpr( left, op, right );
            }
                return left;
        }
        
        private Expr ExprAdd(){
            Symbol op;
            Expr left, right;
            left = ExprMult();
            while ( (op = lexer.token) == Symbol.PLUS ||op == Symbol.MINUS ) {
                lexer.nextToken();
                right = ExprMult();
                
                // semantic analysis
                if ( ! checkMathExpr( left.getType(), right.getType() ) )
                error.show("Expression of type integer expected");
                left = new CompositeExpr( left, op, right );
            }
            return left;
        }

        private Expr ExprMult(){
            
            Expr left, right;
            left = simpleExpr(); //provavelmente ExprPrimary
            Symbol op;
            while( (op = lexer.token) == Symbol.MULT || op == Symbol.DIV || op == Symbol.REMAINDER){
                lexer.nextToken();
                right = simpleExpr();
                //semantic analysis
                if(left.getTyoe() != Type.integerType || right.getType() != Type.integerType)
                    error.signal("Expression of type integer expected");
                left = new CompositeExpr(left, op. right);
            }
            return left;
        }

        private ParamList paramList(){
        
            ParamList paramList = null;
            if(lexer.token == Symbol.IDENT){
                paramList = new ParamList();
                paramDec(paramList);
                while(lexer.token == Symbol.SEMICOLON){
                    lexer.nextToken();
                    paramDec(paramList);
                }
            }
            return paramList;
        }
        
        private void paramDec(ParamList paramList){
            // ParamDec ::= Ident { ’,’ Ident } ’:’ Type
            ArrayList<Parameter> lastVarList = new ArrayList<Parameter>();
            
            while ( true ) {
                if ( lexer.token != Symbol.IDENT )
                    error.signal("Identifier expected");
                    // name of the identifier
                String name = (String ) lexer.getStringValue();
                lexer.nextToken();
                    // semantic analysis
                    // if the name is in the symbol table and the scope of the name is local,
                    // the variable is been declared twice.
                if ( symbolTable.getInLocal(name) != null )
                    error.show("Parameter " + name + " has already been declared");
                
                    // variable does not have a type yet
                Parameter v = new Parameter(name);
                    // inserts the variable in the symbol table. The name is the key and an
                    // object of class Variable is the value. Hash tables store a pair (key, value)
                    // retrieved by the key.
                symbolTable.putInLocal( name, v );
                // list of the last variables declared. They don’t have types yet
                lastVarList.add(v);
                
                if ( lexer.token == Symbol.COMMA )
                    lexer.nextToken();
                else
                    break;
            }
            
            if ( lexer.token != Symbol.COLON ) { // :
                error.show(": expected");
                lexer.skipPunctuation();
            }else
                lexer.nextToken();
                // get the type
            Type typeVar = type();
            
            for ( Parameter v : lastVarList ) {
                // add type to the variable
                v.setType(typeVar);
                // add v to the list of parameter declarations
                paramList.addElement(v);
            }
        }
        
        private IfStat ifStat() {
            lexer.nextToken();
            Expr e = orExpr();
                // semantic analysis
                // check if expression has type boolean
            if ( e.getType() != Type.booleanType )
                error.signal("Boolean type expected in if expression");
            
            if ( lexer.token != Symbol.THEN )
                error.signal("then expected");
            
            lexer.nextToken();
            StatementList thenPart = statementList();
            StatementList elsePart = null;
            
            if ( lexer.token == Symbol.ELSE ) {
                lexer.nextToken();
                elsePart = statementList();
            }
            if ( lexer.token != Symbol.ENDIF )
                error.signal("\"endif\" expected");
            
            lexer.nextToken();
            return new IfStat( e, thenPart, elsePart );
        }
        
        private Stat whileStat(){
            lexer.nextToken();
            
            Expr expr = orExpr();
            
            if ( ! checkWhileExpr(expr.getType()) )
                error.show("Boolean expression expected");
            if ( lexer.token != Symbol.DO )
                error.show("do expected");
            else
                lexer.nextToken();
            
            return new WhileStatement(expr, statement() );
        }
        
        private FuncCall functionCall() {
            // we already know the identifier is a function. So we
            // need not to check it again.
            ExprList anExprList = null;
            String name = (String ) lexer.getStringValue();
            lexer.nextToken();
            Function p = (Function ) symbolTable.getInGlobal(name);
            
            if ( lexer.token != Symbol.LEFTPAR ) {
                error.show("( expected");
                lexer.skipBraces();
            }else
                lexer.nextToken();
            
            if ( lexer.token != Symbol.RIGHTPAR ) {
                // The parameter list is used to check if the arguments to the
                // procedure have the correct types
                anExprList = exprList( p.getParamList() );
                if ( lexer.token != Symbol.RIGHTPAR )
                    error.show("Error in expression");
                else
                    lexer.nextToken();
            } else {
            // semantic analysis
            // does the procedure has no parameter ?
            if ( p.getParamList() != null && p.getParamList().getSize() != 0 )
                error.show("Parameter expected");
                lexer.nextToken();
            }
            return new FuncCall(p,anExprList);
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

