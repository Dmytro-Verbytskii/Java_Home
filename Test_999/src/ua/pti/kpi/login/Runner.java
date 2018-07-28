/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pti.kpi.login;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import static java.lang.System.exit;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.IOUtils;

/**
 *
 * @author denis
 */
public class Runner {
    
    public static final String OS_NAME = System.getProperty("os.name");
    public static final String OS_VERSION = System.getProperty("os.version");
    public static final String USER_NAME = System.getProperty("user.name");
    public static final double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    
    public static String md2(String st) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            // тут можно обработать ошибку
            // возникает она если в передаваемый алгоритм в getInstance(,,,) не существует
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md2Hex = bigInt.toString(16);

        while( md2Hex.length() < 32 ){
            md2Hex = "0" + md2Hex;
        }

        return md2Hex;
    }
    
    
    
    public static void main(String []argc) throws IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException{
        Scanner scan = new Scanner(System.in);
        
        String hostname = "Unknown";
        
       // System.out.print("Heutd\n");
       // System.out.println(md2("Heutd"));
        
        System.out.println("Enter session key:");
        Scanner scankey = new Scanner(System.in);
        String secretKey = scankey.next();
        
        
        
        
        byte[] b = secretKey.getBytes(Charset.forName("UTF-8"));
        SecretKey firstKey = new SecretKeySpec(b, 0, b.length, "DES");
        
        DesEncrypter encrypter = new DesEncrypter(firstKey);

        String contentCrypt = new Scanner(new File("cipher.txt")).useDelimiter("\\Z").next();
        
        String content = encrypter.decrypt(contentCrypt);
        
        File tempfile = new File("users.txt");
        if(!tempfile.exists()){
            tempfile.createNewFile();
        }
        
        PrintWriter out = new PrintWriter("users.txt");
        
        out.println(content);
        out.close();

       /* InetAddress addr;
        addr = InetAddress.getLocalHost();
        hostname = addr.getHostName();
        //System.out.println("Введите ваш ключ:");
        //String userKey = scan.next();
        String globalInfo = OS_NAME + OS_VERSION + USER_NAME + WIDTH + hostname + userKey;
        String hashCode = "" + globalInfo.hashCode();
        
        
        WinRegistry.createKey(WinRegistry.HKEY_CURRENT_USER, "HmExample");
        String value = WinRegistry.readString (
            WinRegistry.HKEY_CURRENT_USER,                            
            "Software\\JavaSoft\\Prefs\\/Kochubey",                           
            "/S/I/G/N/A/T/U/R/E"
        );                                             
        
        */
        
        if(/*value.equals(hashCode)*/ true){
        
        
        UserList userList = new UserList();
        userList.readFromDb();
        
        Scanner in = new Scanner(System.in);
        int choose;
        String name;
        String password;
        String newpassword;
        
    
        while(true){
            System.out.println("\n1. Add user\n2. Sign in\n3. About\n4. Exit");
            choose = in.nextInt();
            
            switch(choose){
              
                case 1:
                    System.out.println("Login: ");
                    name = in.next();
                    System.out.println("Pass: ");
                    password = in.next();
                    if(userList.searchUser(name) == null){
                        userList.addUser(name, password);
                        userList.writeInDb();
                    }else {
                        System.out.println("User already exist");
                    }
                    break;
                
                case 2:
                    System.out.println("Login: ");
                    name = in.next();
                    System.out.println("Pass: ");
                    password = in.next();
                    if(userList.searchUser(name) != null){
                        if(!userList.searchUser(name).isBlocked()){
                            if(userList.logIn(name, password)){

                                System.out.println("Hello, " + name);
                                if("admin".equals(name)){
                                    while(choose != 6){
                                        System.out.println("\n1. Change pass \n2. Show users list\n3. Add user\n4. Block/unblock user\n5. Turn on limitations\n6. Exit");
                                        choose = in.nextInt();

                                        switch(choose){

                                            case 1:
                                                System.out.println("Old pass: ");
                                                password = in.next();
                                                System.out.println("New pass: ");
                                                newpassword = in.next();
                                                if(userList.currentUser.restrictions == false || userList.currentUser.checkPassword(newpassword)){
                                                    if(userList.currentUser.changePassword(password, newpassword)){
                                                        System.out.println("\nPass changed\n");
                                                    } else{
                                                        System.out.print("\nINCORRECT old pass\n");
                                                    }
                                                } else {
                                                    System.out.println("Pass MUST contain A-Z, a-z, 0-9");
                                                }
                                                userList.writeInDb();
                                                break;

                                            case 2:
                                                System.out.println();
                                                userList.showUserList();
                                                break;

                                            case 3:
                                                System.out.println("Login: ");
                                                name = in.next();
                                                if(userList.searchUser(name) == null){
                                                    userList.addUser(name);
                                                    userList.writeInDb();
                                                }else {
                                                     System.out.println("User already exist");
                                                }
                                                break;

                                            case 4:
                                                System.out.println("Enter user's login: ");
                                                name = in.next();
                                                userList.blockUser(name);
                                                userList.writeInDb();
                                                break;

                                            case 5:
                                                System.out.println("Enter user's login: ");
                                                name = in.next();
                                                userList.setRestrictions(name);
                                                userList.writeInDb();
                                                break;

                                            case 6:
                                                userList.logOut();
                                                break;
                                        }
                                    }
                                }else{
                                    do{
                                        System.out.println("\n1. Change pass \n2. Exit");
                                        choose = in.nextInt();

                                        switch(choose){
                                            case 1:
                                                System.out.println("Old pass: ");
                                                password = in.next();
                                                System.out.println("New pass: ");
                                                newpassword = in.next();
                                                if(userList.currentUser.restrictions == false || userList.currentUser.checkPassword(newpassword)){
                                                    if(userList.currentUser.changePassword(password, newpassword)){
                                                        System.out.println("\nPass changed\n");
                                                    } else{
                                                        System.out.print("\nINCORRECT old pass\n");
                                                    }
                                                } else {
                                                    System.out.println("Pass MUST contain A-Z, a-z, 0-9");
                                                }
                                                userList.writeInDb();
                                                break;

                                            case 2:
                                                userList.logOut();
                                                break;
                                        }
                                    } while(choose != 2);
                                }
                            }else{
                                userList.tryNumber++;
                                if(userList.tryNumber == 3){
                                    exit(1);
                                }
                                System.out.println("INCORRECT data");
                            }
                        }else{
                            System.out.println("User is blocked");
                        }
                    } else{
                            System.out.println("User doesn't exist");
                    }  
                    break;
                case 3:
                    System.out.println("Creator: Yuzhakov Nikita, FB-21\n Pass MUST contain A-Z, a-z, 0-9");
                    break;
                case 4:
                    File file = new File("users.txt");
                    System.out.println("Enter NEW session key: ");
                    secretKey = scankey.next();
                     byte[] e = secretKey.getBytes(Charset.forName("UTF-8"));
                        SecretKey finalKey = new SecretKeySpec(e, 0, e.length, "DES");

                        DesEncrypter encrypterFinal = new DesEncrypter(finalKey);

                        String plainText = new Scanner(new File("users.txt")).useDelimiter("\\Z").next();
                        
                        
                        

                        String cipherText = encrypterFinal.encrypt(plainText);

                        PrintWriter outCrypt = new PrintWriter("cipher.txt");

                        outCrypt.println(cipherText);
                        outCrypt.close();
                        tempfile.delete();
                        file.setWritable(true);
                        System.gc();
                        file.delete();
                    exit(1);
            }
            
        }
        }else{
            System.out.println("INCORRECT key. Exit");
        }
    }
}
