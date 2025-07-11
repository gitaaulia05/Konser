package com.mycompany.konser_oop2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class connection {
    public static Statement stm;
    public static Connection con;
    public static final String url = "jdbc:mysql://localhost/konser";
     public static final String user="root";
     public static final String pass="";
    public static Connection getConnection(){
        if(con == null ){
             try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, pass);
           
            }catch(Exception e){
                System.out.println("Koneksi gagal " + e.getMessage());
                return null;
            }
        }
         return con;
    }
        
}
