//Team C Project Authors
//Matthew Kreiling
//Daniel Edberg
//Jacob Davis
//Tajuddin Idrisa Mwijage
//Stephen Littlefield


/*
import edu.jsu.mcis.cs310.tas_fa21.Badge;
import edu.jsu.mcis.cs310.tas_fa21.ShiftParameters;
import java.sql.*;
import java.time.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
*/
package edu.jsu.mcis.cs310.tas_fa21;

import java.sql.*;
import java.sql.Connection;
import java.time.*;
import java.util.ArrayList;



public class TASDatabase {
    
    Connection conn = null;
    PreparedStatement pstSelect = null, pstUpdate = null;
    private String query;
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
                int terminalID = resultset.getInt("terminalid");
                String badgeID = resultset.getString("badgeid");
                Badge badge = getBadge(badgeID);
                LocalDateTime originalTimeStamp = resultset.getTimestamp("originalTimeStamp").toLocalDateTime();

                
                originalTimeStamp = resultset.getTimestamp("originalTimeStamp").toLocalDateTime();

                PunchType punchtype = PunchType.values()[resultset.getInt("punchtypeid")];

               
               //Punch punch = new Punch(id, terminalID, badge, originalTimeStamp, punchtype);
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
            
            pstSelect.setString(1, shiftID);
            
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
    
    public Shift getShift(Badge badge) {
        
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

    int insertPunch(Punch p1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<Punch> getDailyPunchList(Badge badge, LocalDate date) {
        
       ArrayList<Punch> alist = null;
        
        try {
            query = "SELECT * FROM punch WHERE badgeid=? AND DATE(originalTimeStamp)=?";
            pstSelect = conn.prepareStatement(query);
            pstSelect.setString(1, badge.getId());
            pstSelect.setDate(2, java.sql.Date.valueOf(date));
            
            boolean hasResults = pstSelect.execute();
            
            
            if(hasResults){
                
                alist = new ArrayList<>();
                
                ResultSet resultsSet = pstSelect.getResultSet();
                while(resultsSet.next()) {
                    int terminalID = resultsSet.getInt("terminalid");
                    String badgeID = resultsSet.getString("badgeid");
                    LocalDateTime originalTimeStamp = resultsSet.getTimestamp("originalTimeStamp").toLocalDateTime();
                    int PunchTypeID = resultsSet.getInt("punchTypeId");
                   
                    Punch punch = new punch (terminalID, badge, originalTimeStamp, PunchTypeID);
                    alist.add(punch);
                    
                }
            }
                  
        }
        catch (Exception e) { e.printStackTrace(); }
        
        return alist;   
        
    }

    
        
    }




        






