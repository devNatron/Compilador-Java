/*
    José Vitor Novaes Santos
    Marcus Vinicius Natrielli Garcia
    Victor Fernandes de Oliveira Brayner
*/

package compiler.main;

import java.util.Hashtable;

public class SymbolTable {
    
    private Hashtable globalTable, localTable;
    
    public SymbolTable(){
        globalTable = new Hashtable();
        localTable = new Hashtable();
    }
    
    public Object putInGlobal( String key, Object value){
        return globalTable.put(key, value);
    }
    
    public Object putInLocal( String key, Object value){
        return localTable.put(key, value);
    }
    
    public Object getInLocal( Object key){
        return localTable.get(key);
    }
    
    public Object getInGlobal( Object key){
        return globalTable.get(key);
    }
    
    public Object get (String key){
        Object result;
        if( (result = localTable.get(key)) != null){
            return result;
        }else{
            return globalTable.get(key);
        }
    }
    
    public void removeLocalIdent(){
        localTable.clear();
    }
}
