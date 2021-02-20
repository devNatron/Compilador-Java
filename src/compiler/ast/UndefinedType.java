/*
    José Vitor Novaes Santos
    Marcus Vinicius Natrielli Garcia
    Victor Fernandes de Oliveira Brayner
*/

package compiler.ast;

import java.io.*;

public class UndefinedType extends Type {
    
    public UndefinedType() { 
        super("undefined"); 
    }
    
    public String getCname() {
        return "int";
    }
}