/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pti.kpi.login;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 *
 * @author denis
 */
public class UserList {
    
    int tryNumber;
    User currentUser;
    ArrayList <User> users;
    
    UserList(){
        tryNumber = 0;
        currentUser = null;
        users = new <User> ArrayList();
    }

    
    public void addUser(String name, String password){
        User user = new User(name, password);
        users.add(user);
    }
    
    public void addUser(String name){
        User user = new User(name, "1");
        users.add(user);
    }
    
    public User searchUser(String name){
        for (User user : users) {
            if (user.name.equals(name)){
                return user;
            }
        }
        return null;
    }
    
    public boolean logIn(String name, String password){
        for (User user : users) {
            if (user.name.equals(name) && user.password.equals(password)){
                this.currentUser = user;
                return true;
            }
        }
        return false;
    }
    
    public void logOut(){
        this.currentUser = null;
    }
    
    public void setCurrentUser(User user){
        this.currentUser = user;
    }
    
    public void showUserList(){
        for (User user : users) {
            System.out.println(user.name + "    —    blocked: " + user.isBlocked + ",    limitations: " + user.restrictions);
        }
    }
    
    public void blockUser(String name){
        if("admin".equals(currentUser.name)){    
            for (User user : users) {
                if (user.name.equals(name)){
                    if(!user.isBlocked){
                        user.isBlocked = true;
                    } else user.isBlocked = false;
                }
            }
        }
    }
    
    public void setRestrictions(String name){
        if("admin".equals(currentUser.name)){     
            for (User user : users) {
                if (user.name.equals(name)){
                    if(!user.restrictions){
                        user.restrictions = true;
                    } else user.restrictions = false;
                }
            }
        }
    }
    
    public String showInfo(){
        return "\n===========" + "name: " + this.currentUser.name + ". password: " + this.currentUser.password;
    }
    
    public void readFromDb() throws IOException{
 
        //File file = new File("users.txt");
        File file = new File("users.txt");
 
        if(!file.exists()){
            file.createNewFile();
            addUser("admin", "admin");
            writeInDb();
            
        }else{
        
            BufferedReader in = new BufferedReader(new FileReader( file.getAbsoluteFile()));
            int beginIndex;
            int endIndex;
            boolean isBlocked, restrictions;

            String s, name, password;

            while ((s = in.readLine()) != null) {

                endIndex = s.indexOf(" ");
                name = s.substring(0, endIndex);

                beginIndex = endIndex + 1;
                endIndex = s.indexOf(" ", beginIndex);
                password = s.substring(beginIndex, endIndex);

                beginIndex = endIndex + 1;
                endIndex = s.indexOf(" ", beginIndex);
                if ("true".equals(s.substring(beginIndex, endIndex))){
                    isBlocked = true;
                } else isBlocked = false;

                beginIndex = endIndex + 1;
                endIndex = s.length();
                if ("true".equals(s.substring(beginIndex, endIndex))){
                    restrictions = true;
                } else restrictions = false;

                User user = new User(name, password, isBlocked, restrictions);
                users.add(user);

            }

            in.close();
        }
    }
    
    public void writeInDb() throws FileNotFoundException, IOException{
        File file = new File("users.txt");
 
        if(!file.exists()){
            file.createNewFile();
        }
        
        PrintWriter out = new PrintWriter(file.getAbsoluteFile());
         
        for (User user : users) {
            out.println(user.name + " " + user.password + " " + user.isBlocked + " " + user.restrictions);
        }
        
        out.close();
    }
    
    
    
}
