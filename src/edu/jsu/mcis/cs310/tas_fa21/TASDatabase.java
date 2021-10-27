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
import java.time.LocalDate;
import java.util.ArrayList;



public class TASDatabase {
    
    Connection conn = null;
    
    boolean hasresults;
    int columnCount = 0;
    
    public TASDatabase(){
        
        try{

            /* Identifying the Server */
        
            String server = ("jdbc:mysql://localhost/tas_fa21_v1?serverTimezone=America/Chicago");
            String username = "CS310C";
            String password = "CS488";
            System.out.println("Connecting to " + server + "...");
        
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
    }
    
    public Badge getBadge(String badgeid){
        
        Badge badge = null;
        
        try{
            
            PreparedStatement pstSelect = conn.prepareStatement("SELECT * FROM badge where id = ?");
            
            pstSelect.setString(1, badgeid);
            
            boolean hasresults = pstSelect.execute();
            
            if (hasresults) {
            
                ResultSet resultset = pstSelect.getResultSet();
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
            PreparedStatement pstSelect = conn.prepareStatement("select * from punch where id=?");
            pstSelect.setInt(1, punchid);
            
            boolean hasresult = pstSelect.execute();
            
            if (hasresult) {
                
                System.err.println("Getting punch data ...");

                ResultSet resultset = pstSelect.getResultSet();

                resultset.first();
                int id = resultset.getInt("id");
                int terminalID = resultset.getInt("terminalid");
                String badgeID = resultset.getString("badgeid");
                Badge badge = getBadge(badgeID);
                LocalDateTime originalTimeStamp = resultset.getTimestamp("originaltimestamp").toLocalDateTime();

                PunchType punchtype = PunchType.values()[resultset.getInt("punchtypeid")];


                //  Punch (int id, int terminalID, Badge badge, LocalDateTime originalTimeStamp, PunchType punchTypeID)
                punch = new Punch(id, terminalID, badge, originalTimeStamp, punchtype);
                
            }
            
        }
        
        catch(Exception e) {
            e.printStackTrace();
        }
        
        return punch;
    }
    
    
    public Shift getShift(int shiftID) {
        
        Shift shift = null;
        
        try {
            PreparedStatement pstSelect = conn.prepareStatement("select * from shift where id = ?");
            
            pstSelect.setInt(1, shiftID);
            
            pstSelect.execute();
            
            ResultSet resultset = pstSelect.getResultSet();
            resultset.first();
            
            //Results
            int idNum = resultset.getInt("id");
            LocalTime start = resultset.getTime("start").toLocalTime();
            // continue for all the other fields in the "Shift" table
            
            //shift = new Shift(idNum);
        }
        
        catch(Exception e) {
            System.err.println("** getShift: " + e.toString());
        }
        
        return shift;
        
    }
    
    public Shift getShift(Badge badge) {
        
        Shift shift = null;
        
        try {
            PreparedStatement pstSelect = conn.prepareStatement("select * from employee where id=1");
            
            
            
            pstSelect.execute();
            ResultSet resultset = pstSelect.getResultSet();
            resultset.first();
            
            //Results
            String idNum = resultset.getString(1);
            
         
        }
        
        catch(Exception e) {
            System.err.println("** getShift: " + e.toString());
        }
        
        return shift;
        
    }

   
        public int insertPunch(Punch p){
            
            int results = 0;
            
            
           
            LocalDateTime time = p.getOriginaltimestamp();
          
            String badgeid = p.getBadge().getId(); 
            int terminalid = p.getTerminalid(); 
            PunchType punchtypeid = p.getPunchtype(); 
          

         try{
             String query = "INSERT INTO tas_fa21_v1.punch (terminalid, badgeid, originaltimestamp, punchtypeid) VALUES (?, ?, ?, ?)"; 
             PreparedStatement pstUpdate = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS); 
             
             pstUpdate.setInt(1, terminalid);
             pstUpdate.setString(2, badgeid);
             pstUpdate.setTimestamp(3, java.sql.Timestamp.valueOf(time)); // this is now a set to timestamp instead of a string
             pstUpdate.setInt(4, punchtypeid.ordinal());
             
             int updateCount = pstUpdate.executeUpdate();
             
             if(updateCount > 0){
                 
                 ResultSet resultset = pstUpdate.getGeneratedKeys(); 
                 
                 if (resultset.next()){
                     results = resultset.getInt(1);
                 }
             }
                
         }
         catch(Exception e){ e.printStackTrace();} // changed these to output differently and just print the full stack
         return results;    
    }

    public ArrayList<Punch> getDailyPunchList(Badge badge, LocalDate date) {
        
       ArrayList<Punch> alist = null;
        
        try {
            String query = "SELECT * FROM punch WHERE badgeid=? AND DATE(originalTimeStamp)=?";
            PreparedStatement pstSelect = conn.prepareStatement(query);
            pstSelect.setString(1, badge.getId());
            pstSelect.setDate(2, java.sql.Date.valueOf(date));
            
            boolean hasResults = pstSelect.execute();
            
            
            if(hasResults){
                
                alist = new ArrayList<>();
                
                ResultSet resultsSet = pstSelect.getResultSet();
                while(resultsSet.next()) {
                    int id = resultsSet.getInt("id");
                    int terminalID = resultsSet.getInt("terminalid");
                    String badgeID = resultsSet.getString("badgeid");
                    LocalDateTime originalTimeStamp = resultsSet.getTimestamp("originalTimeStamp").toLocalDateTime();
                    int punchTypeID = resultsSet.getInt("punchTypeId");
                    
                    //  Punch (int id, int terminalID, Badge badge, LocalDateTime originalTimeStamp, PunchType punchTypeID)
                   
                    Punch punch = new Punch (id, terminalID, getBadge(badgeID), originalTimeStamp, PunchType.values()[punchTypeID]);
                    alist.add(punch);
                    
                }
            }
                  
        }
        catch (Exception e) { e.printStackTrace(); }
        
        return alist;   
        
    }

   

    
        
    }




        






