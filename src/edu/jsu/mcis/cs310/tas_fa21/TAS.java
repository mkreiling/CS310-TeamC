//Team C Project Authors
//Matthew Kreiling
//Daniel Edberg
//Jacob Davis
//Tajuddin Idrisa Mwijage
//Stephen Littlefield

package edu.jsu.mcis.cs310.tas_fa21;
import java.sql.*;

public class TAS {
    
    public static void main(String[] args) { 
       Badge b = new Badge("Sephen", "D.", "Littlefield", "198" );
       System.out.println(b.toString());
       b.setFname("Coolman");
       System.out.println(b.toString());
       System.out.println(b.getBadgeId());  

    }
    
}//TODO add connection to database
