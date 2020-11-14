/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho01;

import java.util.Hashtable;

/**
 *
 * @author super
 */
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
