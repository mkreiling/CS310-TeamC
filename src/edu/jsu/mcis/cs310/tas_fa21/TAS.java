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
       Badge b = new Badge("T-800","Carl C. Dyne");
       System.out.println("This is the current badge. " + b.toString());
    }
    
}//TODO add connection to database
