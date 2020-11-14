/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST;

import java.util.ArrayList;

/**
 *
 * @author super
 */
public class LocalVarList {
    
    ArrayList<Variable> v;
    
    public LocalVarList(){
        v = new ArrayList<Variable>();
    }
    
    public void addElement(Variable var){
        v.add(var);
    }
    
    //public void genc( Pw pw){}
    
}
