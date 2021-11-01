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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



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
    
    public Badge getBadge(String ID){
        
        Badge badge = null;
        
        try{
            
            PreparedStatement pstSelect = conn.prepareStatement("SELECT * FROM badge where id = ?");
            
            pstSelect.setString(1, ID);
            
            boolean hasresults = pstSelect.execute();
            
            if (hasresults) {
            
                ResultSet resultset = pstSelect.getResultSet();
                resultset.next();


                badge = new Badge(resultset.getString("id"), resultset.getString("description"));
                
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
                String badgeid = resultset.getString("badgeid");
                Badge badge = getBadge(badgeid);
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
            PreparedStatement pstSelect = conn.prepareStatement("SELECT * from shift where id = ?");
            pstSelect.setInt(1, shiftID);
            
            boolean hasresult = pstSelect.execute();
             
            if(hasresult) {
                
                System.err.println("Getting shift data ...");
                 
                ResultSet resultset = pstSelect.getResultSet();
                
                resultset.first();

                //Results
                 ShiftParameters params = new ShiftParameters();
                    
                params.setDescription(resultset.getString("description"));
                params.setStart(LocalTime.parse(resultset.getString("start")));
                params.setStop(LocalTime.parse(resultset.getString("stop")));
                params.setInterval(resultset.getInt("interval"));
                params.setGraceperiod(resultset.getInt("graceperiod"));
                params.setDock(resultset.getInt("dock"));
                params.setLunchstart(LocalTime.parse(resultset.getString("lunchstart")));
                params.setLunchstop(LocalTime.parse(resultset.getString("lunchstop")));
                params.setLunchdeduct(resultset.getInt("lunchdeduct"));
                params.setId(shiftID);
               
                shift = new Shift(params);



            }
        }
        
        catch(Exception e) {
            e.printStackTrace();
        }
        return shift;
    }
    
    

    
    public Shift getShift(Badge badge) {//PERSONAL
        
        Shift Shift = null;
        
        try {
            PreparedStatement pstSelect = conn.prepareStatement("select * from employee where badgeid= ?");

            boolean hasResults = pstSelect.execute();
            
            pstSelect.execute();
            ResultSet resultset = pstSelect.getResultSet();
            resultset.first();
            
            //Results
            String badgeid = resultset.getString(1);
            if(hasResults){
                resultset = pstSelect.getResultSet();
                resultset.next();
                
                      
                ShiftParameters params = new ShiftParameters();
                    
                params.setDescription(resultset.getString("description"));
                params.setStart(LocalTime.parse(resultset.getString("start")));
                params.setStop(LocalTime.parse(resultset.getString("stop")));
                params.setInterval(resultset.getInt("interval"));
                params.setGraceperiod(resultset.getInt("graceperiod"));
                params.setDock(resultset.getInt("dock"));
                params.setLunchstart(LocalTime.parse(resultset.getString("lunchstart")));
                params.setLunchstop(LocalTime.parse(resultset.getString("lunchstop")));
                params.setLunchdeduct(resultset.getInt("lunchdeduct"));
                params.setId(resultset.getInt("id"));
            Shift = new Shift(params);
       
        }
        
        
        
}       catch (SQLException ex) {
            
            System.err.println("** getShift: " + ex.toString());
        }
        return Shift;
    }
    

        public int insertPunch(Punch p){
            
            int results = 0;
            
            
           
            LocalDateTime time = p.getOriginaltimestamp();
          
            String badgeid = p.getBadge().getId(); 
            int terminalid = p.getTerminalid(); 
            PunchType punchtypeid = p.getPunchType(); 
          

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
         catch(Exception e){ e.printStackTrace();} 
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
                    String badgeid = resultsSet.getString("badgeid");
                    LocalDateTime originalTimeStamp = resultsSet.getTimestamp("originalTimeStamp").toLocalDateTime();
                    int punchTypeID = resultsSet.getInt("punchTypeId");
                    
                    //  Punch (int id, int terminalID, Badge badge, LocalDateTime originalTimeStamp, PunchType punchTypeID)
                   
                    Punch punch = new Punch (id, terminalID, getBadge(badgeid), originalTimeStamp, PunchType.values()[punchTypeID]);
                    alist.add(punch);
                    
                }
            }
                  
        }
        catch (Exception e) { e.printStackTrace(); }
        
        return alist;   
        
    }
    

   

    
        
    }




        






