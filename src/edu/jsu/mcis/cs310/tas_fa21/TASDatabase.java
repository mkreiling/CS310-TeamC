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
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.util.GregorianCalendar;


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
    
    public Badge getBadge(String id){
        
        Badge badge= null;
        
        try{
            
            PreparedStatement pstSelect = conn.prepareStatement("SELECT * FROM badge where id = ?");
            
            pstSelect.setString(1, id);
            
            boolean hasresults = pstSelect.execute();
            
            if (hasresults) {
            
                ResultSet resultset = pstSelect.getResultSet();
                resultset.next();


                badge = new Badge(resultset.getString("id"), resultset.getString("description"));
                
            }

        }
        
        //catch(Exception e){
            //System.err.println("** getBadge: " + e.toString());
            catch(SQLException e){ System.out.println("Error in getBadge() " + e); 
        }
        
        return badge;
        
    }
    
    public Punch getPunch(int id) {
       // DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE MM-dd-yyyy HH:mm:ss");
        Punch punch = null;
        
        try {
            PreparedStatement pstSelect = conn.prepareStatement("select * from punch where id=?");
            pstSelect.setInt(1, id);
            
            boolean hasresult = pstSelect.execute();
            
            if (hasresult) {
                
                System.err.println("Getting punch data ...");

                ResultSet resultset = pstSelect.getResultSet();

                resultset.next();
              
                //int id = resultset.getInt("id");                    
                int terminalid = resultset.getInt("terminalid");
                String badgeid = resultset.getString("badgeid");
                LocalDateTime originalTimeStamp = resultset.getTimestamp("originaltimestamp").toLocalDateTime(); 
                int punchtypeid = resultset.getInt("punchtypeid");
                int punchid = resultset.getInt("id");
                //PunchType punchtype = PunchType.values()[resultset.getInt("punchtypeid")];

                //  Punch (int id, int terminalID, Badge badge, LocalDateTime originalTimeStamp, PunchType punchTypeID)
                punch = new Punch(terminalid, getBadge(badgeid),punchtypeid,  originalTimeStamp, punchid);
                
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
    
    

    
    public Shift getShift(Badge badge) {
        
        Shift shift = null;
        PreparedStatement pstSelect = null;
        
        try {
            pstSelect = conn.prepareStatement("select * from employee where badgeid= ?");
            pstSelect.setString(1, badge.getId());

            boolean hasResults = pstSelect.execute();
            
          
           
            if(hasResults){
                ResultSet resultset = pstSelect.getResultSet();
                resultset.next();
                
                int shiftid = resultset.getInt("shiftid");
                shift = getShift(shiftid);
                
            }
       
        }
        catch (Exception ex) { ex.printStackTrace(); }
        finally {
            if ( pstSelect != null ) try { pstSelect.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        
        return shift;
        
        
        
    }
    

    
    
    
        public int insertPunch(Punch p){

        int results = 0;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime originalTime = p.getOriginaltimestamp();
        String otsString = originalTime.format(dtf);
        System.err.println("New Punch Timestamp (from insertPunch(): " + otsString);
        Badge badge = p.getBadge(); 
        int terminalid = p.getTerminalid(); 
        PunchType punchtypeid = p.getPunchType(); 

        try{
            String query = "INSERT INTO tas_fa21_v1.punch (terminalid, badgeid, originaltimestamp, punchtypeid) VALUES (?, ?, ?, ?)"; 
            PreparedStatement pstUpdate = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS); 
             
            pstUpdate.setInt(1, terminalid);
            pstUpdate.setString(2, badge.getId());
            pstUpdate.setString(3, otsString);
            pstUpdate.setInt(4, punchtypeid.ordinal());
             
            int updateCount = pstUpdate.executeUpdate();
             
            if(updateCount > 0){
                 
                ResultSet resultset = pstUpdate.getGeneratedKeys(); 
                 
                if (resultset.next()){
                    results = resultset.getInt(1);
                }
            }
        }
        catch(SQLException e){ System.err.println("insertPunch: " + e);}
        //System.err.println("New Punch ID: " + results);
        
        return results;    
    }
        
        

    public ArrayList<Punch> getDailyPunchList(Badge badge, LocalDate date) {
        
       ArrayList<Punch> output = null;
                Punch obj; 
        output = new ArrayList<>(); 
        String strbadge = badge.getId();
        try {
            String query = "SELECT * FROM punch WHERE badgeid=? AND DATE(originalTimeStamp)=?";
            PreparedStatement pstSelect = conn.prepareStatement(query);
            pstSelect.setString(1, strbadge);
            pstSelect.setDate(2, java.sql.Date.valueOf(date));
            pstSelect.setString(1, badge.getId());
            pstSelect.setDate(2, java.sql.Date.valueOf(date));
            
            boolean hasResults = pstSelect.execute();
            
            
            if(hasResults){
                
                //output = new ArrayList<>();
                
                ResultSet resultsSet = pstSelect.getResultSet();
                while(resultsSet.next()) {
                    
                    int id = resultsSet.getInt("id");
                    int terminalid = resultsSet.getInt("terminalid");
                    String badgeid = resultsSet.getString("badgeid");
                    LocalDateTime originalTimeStamp = resultsSet.getTimestamp("originalTimeStamp").toLocalDateTime();
                    int punchTypeid = resultsSet.getInt("punchTypeId");
                   
                     int punchid = resultsSet.getInt("id");
                    obj = getPunch(punchid);

                    output.add(obj);

                    
                }
            }
                  
        }
        catch (Exception e) { e.printStackTrace(); }
       return output;

        
    }
    

   

    
        
    }




        






