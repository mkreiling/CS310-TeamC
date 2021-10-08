//Team C Project Authors
//Matthew Kreiling
//Daniel Edberg
//Jacob Davis
//Tajuddin Idrisa Mwijage
//Stephen Littlefield

package edu.jsu.mcis.cs310.tas_fa21;
import java.sql.*;
//additional imports, once this class is done, delete unused, added these to help clear possible errors.
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;


//This will be the file we'll be using to connect to the Database. I'll go ahead and get it started just so we can have it -Jacob "Sandy" Davis
public class TASDatabase {
    private Connection conn;
    
//This is just a rudamentary connection to the database. I also added to the main project MySQL library because NetBeans hates me and would not connect otherwise.
    public static void main(String args[]){
        try {
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost/tas_fa21_v1","root","CS488"); //PLEASE REMBER TO USE CS488 AS PASSWORD EVERYONE
        
            if (conn.isValid(0)){
                System.out.println("Connection established successfully!");
                }
        }
    catch(SQLException e){
    //You'll know if you mess up and hopefully get some useful information on the error
    System.out.println("SQLException: " + e.getMessage());
    System.out.println("SQLState: " + e.getSQLState());
    System.out.println("VendorError: " + e.getErrorCode()); 
        }
    
    }

    //This will allow us to close the connection when we are done using the data base
    public void close() {
        
        try{
            
            conn.close();
        }
        catch(SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode()); 
        }
        
    }
}

