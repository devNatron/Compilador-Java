/*
    José Vitor Novaes Santos                RA: 743556
    Marcus Vinicius Natrielli Garcia        RA: 743578
    Victor Fernandes de Oliveira Brayner    RA: 743600
*/

package AST;

import java.io.*;

public class UndefinedType extends Type {
    
    public UndefinedType() { 
        super("undefined"); 
    }
    
    public String getCname() {
        return "int";
    }
}