/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pti.kpi.login;


/**
 *
 * @author denis
 */
public class User {
    
    boolean isBlocked;
    boolean restrictions;
    String name;
    String password;
   
    User(String name, String password){
        this.name = name;
        this.password = password;
        this.isBlocked = false;
        this.restrictions = false; 
    }
    
    User(String name, String password, boolean isBlocked, boolean restrictions){
        this.name = name;
        this.password = password;
        this.isBlocked = isBlocked;
        this.restrictions = restrictions; 
    }
    
    User(String name){
        this.name = name;
        this.password = null;
        this.isBlocked = false;
        this.restrictions = false; 
    }
    
    public boolean isBlocked(){
        return this.isBlocked;
    }
    
    public boolean changePassword(String password, String newPassword){
        if(this.password.equals(password)){
            this.password = newPassword;
            return true;
        }else {
            
            return false;
        }
    }
    
    public boolean checkPassword(String password){
        if((password.equals(password.toUpperCase()) || password.equals(password.toLowerCase())) || password.matches("^\\D*$")){
            return false;
        }else {
            return true;
        }
    }
   
}
