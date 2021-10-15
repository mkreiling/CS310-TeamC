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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;


//This will be the file we'll be using to connect to the Database. I'll go ahead and get it started just so we can have it -Jacob "Sandy" Davis

public class TASDatabase {
    private Connection conn;
    private ResultSet result;
    public Statement sm;
    public java.security.Timestamp LocalDateTime;
    public LocalDateTime originalTimeStamp;
    public LocalDateTime adjustedTimeStamp;
    private Badge badge;
    
//This is just a rudamentary connection to the database. I also added to the main project MySQL library because NetBeans hates me and would not connect otherwise.
    public void main(String args[]){
        try {
            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost/tas_fa21_v1","root","CS488"); //PLEASE REMBER TO USE CS488 AS PASSWORD EVERYONE
        
            if (conn.isValid(0)){
                System.out.println("Connection established successfully!");
            }
            sm = conn.createStatement();
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
    public Punch getPunch(int id){
        Punch pnch = null;
        try{
            String query = "SELECT *, UNIX_TIMESTAMP(originaltimestamp) * 1000 AS ts From punch WHERE id= " + Integer.toString(id);
            try (Statement st = conn.createStatement()){
                ResultSet rs = st.executeQuery(query);
                while (rs.next()){
                    int terminalID = rs.getInt("terminalID");
                    String badgeID = rs.getString("badgeid");
                    int punchTypeId = rs.getInt("punchtypeid");
                    Badge bdg = getBadge(badgeID);
                    long time = rs.getLong("ts");
                    pnch.setId(id);
                    pnch.setBadgeID(badgeID);
                    pnch.setTerminalID(terminalID);
                    //V Fix this in accordance with punch V
                    pnch.setPunchTypeID(PunchType.values()[punchTypeId]);
                    Calendar.getInstance();
                    //LocalDateTime origTime; //edited out, may still be useful
                    LocalTime origTime = LocalTime.getInstance;
                    //origTime = new originalTimeStamp(); //edited out, may still be useful
                    origTime.setTimeInMillis(Time);//'Time' is a placeholder-- Need to replace with # of milliseconds
                    pnch.setOriginalTimeStamp(origTime);
                }
            }
        }
        catch(SQLException ex){
            Logger.getLogger(TASDatabase.class.getName()).log(Level.SEVERE,null,ex);  
        }
        return pnch;
    }
    public Badge getBadge(String id){
        Badge bdg = null;
        try(Statement sm = conn.createStatment()){
            String query = "SELECT * FROM badge WHERE id = " + "\" " + id +"\"";
            ResultSet rs = sm.executeQuery(query);
            while(rs.next()){
                String Id =rs.getString("id");
                String des = rs.getString("description");
                bdg = new Badge(Id,des);
            }
        }
        catch(SQLException ex){
            Logger.getLogger(TASDatabase.class.getName()).log(Level.SEVERE,null,ex);
            }
        return bdg;
    }
    public Shift getShift(int id){
        Shift shft = null;
        try(Statement st = conn.createStatement()){
        String query = "SELECT * FROM shift WHERE id = " + Integer.toString(id);
        ResultSet rs = st.executeQuery(query);
    
        while(rs.next()){
            int Id =rs.getInt("id");
            int interval = rs.getInt("interval");
            Time begin = rs.getTime("begin");
            int graceperiod = rs.getInt("graceperiod");
            int dock = rs.getInt("dock");
            Time lunchbegin = rs.getTime("lunchbegin");
            int lunchdeduct = rs.getInt("lunchdeduct");
            Time lunchend = rs.getTime("lunchend");
            Time end = rs.getTime("end");
            String des = rs.getString("description");
            shft = new Shift(Id,interval,begin,graceperiod,dock,lunchbegin,lunchdeduct,lunchend,end,des);
        }
    }
    catch(SQLException ex){
        Logger.getLogger(TASDatabase.class.getName()).log(Level.SEVERE,null,ex);
    }
    return shft;
}
    public Shift getShift(Badge badge){
    Shift shft = null;
    int shiftid = 0;
    try(Statement st = conn.createStatement()){
        String query = "SELECT  From employee WHERE badgeid = " + "\" " + badge.getID() + "\"";
        ResultSet rs = st.executeQuery(query);
        while(rs.next()) {
            shiftid = rs.getInt("shiftid");
        }
        query = "SELECT * FROM shift WHERE id = " + Integer.toString(shiftid);
        rs = st.executeQuery(query);
        while(rs.next()){
            int Id = rs.getInt("id");
            int interval = rs.getInt("interval");
            int graceperiod = rs.getInt("graceperiod");
            int dock = rs.getInt("dock");
            int lunchdeduct = rs.getInt("lunchdeduct");
            Time begin = rs.getTime("begin");
            Time end = rs.getTime("end");
            Time lunchbegin = rs.getTime("lunchbegin");
            Time lunchend = rs.getTime("lunchend");
            String des = rs.getString("description");
            shft = new Shift(Id,interval,graceperiod,dock,lunchdeduct,des,begin,end,lunchbegin,lunchend);
        }
    }
    catch(SQLException ex){
        Logger.getLogger(TASDatabase.class.getName()).log(Level.SEVERE, null, ex);
    }
    return shft;
}
    public int insertpunch(Punch pnch){
        int ID = pnch.getId();
	int terminalID = pnch.getTerminalid();
	PunchType punchTypeID = pnch.getPunchtypeid();
        LocalDateTime origTime = pnch.LocalDateTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.sql.Date date = new java.sql.Date(origTime.getTimeInMillis(time));//'time' is a placeholder
	Badge badgeID = pnch.getBadgeid();
        try{
            int punchID;
            int Results;
            ResultSet rst;		
            
            String query = " insert into punch (id, terminalid, badgeid, originalTimeStamp, punchTypeID) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstUpdate = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstUpdate.setInt(1, ID);
            pstUpdate.setInt(2, terminalID);
            pstUpdate.setString(3, badgeID);
            pstUpdate.setString(4, sdf.format(date));
            pstUpdate.setInt(5, punchTypeID);     
            Results = pstUpdate.executeUpdate();
            if(Results == 1){
                rst = pstUpdate.getGeneratedKeys();
                if(rst.next()){
                        punchID = rst.getInt(1);
                        pnch.setId(punchID);
                        return pnch.getId();
                }
            }
            conn.close();
        }
        catch(SQLException ex){
            System.out.println("Progressing");
            Logger.getLogger(TASDatabase.class.getName()).log(Level.SEVERE,null,ex);
        }
        return pnch.getId();
    }
    public ArrayList getDailyPunchList(Badge bdg, long ts){
        ArrayList<Punch> punchList = new ArrayList<>();
        GregorianCalendar dayInQuestion = new GregorianCalendar();
        dayInQuestion.setTimeInMillis(ts);
        GregorianCalendar queryTime = new GregorianCalendar();
        try {
            ResultSet rst;           
            String query = "SELECT *, UNIX_TIMESTAMP(originaltimestamp) * 1000 AS time FROM punch WHERE badgeid = ?";
            
            PreparedStatement preparedStmt = conn.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStmt.setString(1, bdg.getID());
            
            rst = preparedStmt.executeQuery();
            int counter = 0;
            while(rst.next()) {
                queryTime.setTimeInMillis(rst.getLong("time"));
                if(queryTime.get(Calendar.DAY_OF_YEAR) == dayInQuestion.get(Calendar.DAY_OF_YEAR) && queryTime.get(Calendar.YEAR) == dayInQuestion.get(Calendar.YEAR)) {
                    Punch pnch = getPunch(rst.getInt("id"));
                    punchList.add(pnch); 

                }
            }
        }
        catch(SQLException ex) {   
        }
        return punchList;
    }    
}


