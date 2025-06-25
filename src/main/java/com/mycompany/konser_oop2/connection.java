package com.mycompany.konser_oop2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class connection {
    public static Statement stm;
    public static Connection con;
    
    public static Connection getConnection(){
        if(con == null ){
             try {
            String url = "jdbc:mysql://localhost/konser";
            String user="root";
            String pass="";
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url,user,pass);
            stm = con.createStatement();
            System.out.println("Koneksi berhasil");
            }catch(Exception e){
                System.out.println("Koneksi gagal " + e.getMessage());

            }
        }
         return con;
    }
        
}
