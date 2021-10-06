//Team C Project Authors
//Matthew Kreiling
//Daniel Edberg
//Jacob Davis
//Tajuddin Idrisa Mwijage
//Stephen Littlefield

package edu.jsu.mcis.cs310.tas_fa21;
import java.sql.*;

//This will be the file we'll be using to connect to the Database. I'll go ahead and get it started just so we can have it -Jacob "Sandy" Davis
public class TASDatabase {
//This is just a rudamentary connection to the database. I also added to the main project MySQL library because NetBeans hates me and would not connect otherwise.
public static void main(String args[]){
    try {
        Connection conn=DriverManager.getConnection("jdbc:mysql://localhost/tas_fa21_v1","root","CS488"); //PLEASE REMBER TO USE CS488 AS PASSWORD EVERYONE
        
        if (conn.isValid(0)){
            System.out.println("Connection established successfully!");
            conn.close();
        }
   
}
    catch(Exception e){System.out.println(e);
}
}
}
