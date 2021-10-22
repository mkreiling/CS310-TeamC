//Team C Project Authors
//Matthew Kreiling
//Daniel Edberg
//Jacob Davis
//Tajuddin Idrisa Mwijage
//Stephen Littlefield

package edu.jsu.mcis.cs310.tas_fa21;

import java.sql.*;
import java.sql.Connection;
import java.time.*;



public class TASDatabase {
    
    Connection conn = null;
    PreparedStatement pstSelect = null, pstUpdate = null;
    ResultSet resultset = null;
    ResultSetMetaData metadata = null;
    
    boolean hasresults;
    int columnCount = 0;
    
    public TASDatabase(){
        
        try{

            /* Identifying the Server */
        
            String server = ("jdbc:mysql://localhost/tas_fa21_v1?serverTimezone=America/Chicago");
            String username = "CS310C";
            String password = "cs488";
            System.out.println("Connecting to " + server + "...");

            /* Load the MySQL JDBC Driver */
        
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        
            /* Open the Connection */

            conn = DriverManager.getConnection(server, username, password);
        
            /* Test Connection */

            if(conn.isValid(0)){
            System.out.println("Connection Successful");
            }
    
            
        }
        
        catch (Exception e) {
            System.err.println(e.toString());
        }
        
        // Close Database Objects
        
        finally {
            
            if (resultset != null) { try { resultset.close(); resultset = null;} catch (Exception e ) {} }
            
            if (pstSelect != null) { try { pstSelect.close(); pstSelect = null;} catch (Exception e ) {} }
            
            if (pstUpdate != null) { try { pstUpdate.close(); pstUpdate = null;} catch (Exception e ) {} }
            
        }
    }
    
    public Badge getBadge(String badgeid){
        
        Badge badge = null;
        
        try{
            
            pstSelect = conn.prepareStatement("SELECT * FROM badge where id = ?");
            
            pstSelect.setString(1, badgeid);
            
            boolean hasresults = pstSelect.execute();
            
            if (hasresults) {
            
                resultset = pstSelect.getResultSet();
                resultset.first();

                String id = resultset.getString("id");
                String description = resultset.getString("description");

                badge = new Badge(id, description);
                
            }

        }
        
        catch(Exception e){
            System.err.println("** getBadge: " + e.toString());
        }
        
        return badge;
        
    }
    
    public Punch getPunch(int punchid) {
        
        Punch punch = null;
        
        try {
            pstSelect = conn.prepareStatement("select * from punch where id=?");
            pstSelect.setInt(1, punchid);
            
            boolean hasresult = pstSelect.execute();
            
            if (hasresult) {
                
                System.err.println("Getting punch data ...");

                resultset = pstSelect.getResultSet();

                resultset.first();
                int id = resultset.getInt("id");
                int terminalid = resultset.getInt("terminalid");
                String badgeid = resultset.getString("badgeid");
                Badge badge = getBadge(badgeid);
                LocalDateTime originalTimeStamp = resultset.getTimestamp("originalTimeStamp").toLocalDateTime();

                
                java.time.LocalDateTime originaltimestamp = resultset.getTimestamp("originaltimestamp").toLocalDateTime();

                PunchType punchtype = PunchType.values()[resultset.getInt("punchtypeid")];

               
               Punch p = new Punch(id, terminalid, badge, originalTimeStamp, punchtype);
            }
            
        }
        
        catch(Exception e) {
            e.printStackTrace();
        }
        
        return punch;
    }
    
    
    public Shift getShift(String shiftID) {
        try {
            pstSelect = conn.prepareStatement("select * from employee where id=7");
            
            pstSelect.setString(7, shiftID);
            
            pstSelect.execute();
            resultset = pstSelect.getResultSet();
            resultset.first();
            
            //Results
            int idNum = resultset.getInt(7);
            
            Shift shift = new Shift(idNum);
   
            return shift;
        }
        
        catch(Exception e) {
            System.err.println("** getShift: " + e.toString());
        }
        return null;
    }
    
    public Shift getShift(Badge badge) {//need to change (Badge badge) but to what????????
        
        Shift shift = null;
        
        try {
            pstSelect = conn.prepareStatement("select * from employee where id=1");
            
            
            
            pstSelect.execute();
            resultset = pstSelect.getResultSet();
            resultset.first();
            
            //Results
            String idNum = resultset.getString(1);
            
         
        }
        
        catch(Exception e) {
            System.err.println("** getShift: " + e.toString());
        }
        
        return shift;
        
    }
        public Shift getShift(int Badge) {
        
        Shift shift = null;
        
        try {
            pstSelect = conn.prepareStatement("select * from employee where id=1");
            
            
            
            pstSelect.execute();
            resultset = pstSelect.getResultSet();
            resultset.first();
            
            //Results
            String idNum = resultset.getString(1);
            
         
        }
        
        catch(Exception e) {
            System.err.println("** getShift: " + e.toString());
        }
        
        return shift;
        
    }

    public Shift getShift(int badge) {//need to change (Badge badge) but to what????????
        
        Shift shift = null;
        
        try {
            pstSelect = conn.prepareStatement("select * from employee where id=1");
            
            //pstSelect.setString(1, badge);
            
            pstSelect.execute();
            resultset = pstSelect.getResultSet();
            resultset.first();
            
            //Results
            String idNum = resultset.getString(1);
            
            //b = new Badge(idNum);
            //int badgeId = badge.getId();
            //return ;
        }
        
        catch(Exception e) {
            System.err.println("** getShift: " + e.toString());
        }
        
        return shift;
        
    }
    
}


