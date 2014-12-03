/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.down;

/**
 *
 * @author Cleber
 */
public class Down {
    
    public Down(){}
    
    public static void main(String args[]){
        FrmPrincipal frmPrincipal = new FrmPrincipal();
        
        frmPrincipal.setVisible(true);
    }
    
    public static String getSystemName(){
        return "Down!";
    }
    
    public static String getSystemPath(){
        return System.getProperty("user.dir");
    }
    
}
