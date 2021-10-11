//Team C Project Authors
//Matthew Kreiling
//Daniel Edberg
//Jacob Davis
//Tajuddin Idrisa Mwijage
//Stephen Littlefield

package edu.jsu.mcis.cs310.tas_fa21;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class Punch {
    private String badgeID;
    private int terminalID;
    private PunchType punchTypeID;
    private int id;
    private LocalDateTime originalTimeStamp;
    private LocalDateTime adjustedTimeStamp;
    private String adjustMessage;
    
    public Punch (int id, int terminalID, String badgeID, LocalDateTime originalTimeStamp, PunchType punchTypeID) {
        
        if(id >= 0){this.id = id;}
        this.terminalID = terminalID;
        this.badgeID = badgeID;
        this.originalTimeStamp = originalTimeStamp;
        this.punchTypeID = punchTypeID;
        
    }

    public Punch(Badge b, int terminalID, PunchType punchTypeID){
        this(-1, terminalID, b.getID(), LocalDateTime.now(), punchTypeID);
    }
    
    public String printOriginalTimestamp() {
        
        String punchResults = "";
        //V Fix me V
        LocalDateTime cal = new LocalDateTime();
        cal.setTimeInMillis(originalTimeStamp.getTime());

        String pattern = "EEE MM/dd/yyyy HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String formattedDate = sdf.format(cal.getTime()).toUpperCase();
        
        String originalTimestamptoString = "#" + getBadgeid() + " " + punchResults + " " + formattedDate;
            
        return originalTimestamptoString;
         
    }
    
    public String printAdjustedTimestamp() {
        String punchResults = "";
        LocalDateTime cal = new LocalDateTime.();
        cal.setTimeInMillis(adjustedTimeStamp.getTime());

        
        String pattern = "EEE MM/dd/yyyy HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String formattedDate = sdf.format(cal.getTime()).toUpperCase();

        String originalTimestamptoString = "#" + getBadgeid() + " " + punchResults + " " + formattedDate + " (" + adjustMessage + ")";
            
        return originalTimestamptoString;
    }
        
    public String getBadgeid() {
        return badgeID;
    }

    public int getTerminalid() {
        return terminalID;
    }

    public PunchType getPunchtypeid() {
        return punchTypeID;
    }

    public int getId() {
        return id;
    }

    public long getOriginaltimestamp() {
        return originalTimeStamp.getTime();

    }
    
    public String getAdjustMessage(){
        return adjustMessage;

    }
    
    public Timestamp getOriginaltimestamp2() {
        return originalTimeStamp;
    }
    
    public Timestamp getAdjustedTimeStamp() {
        return adjustedTimeStamp;
    }
    
    public void setBadgeID(String badgeID) {
        this.badgeID = badgeID;
    }

    public void setTerminalID(int terminalID) {
        this.terminalID = terminalID;
    }

    public void setPunchTypeID(PunchType punchTypeID) {
        this.punchTypeID = punchTypeID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOriginalTimeStamp(Timestamp originalTimeStamp) {
        this.originalTimeStamp = originalTimeStamp;
    }
    
    //V Fix Me V
    private Timestamp helperMethod1(LocalDateTime punch, int totalminutes) {
        punch = punch.withHour(totalminutes/60);
        punch = punch.withMinute(totalminutes%60);
        punch = punch.withSecond(0);
        //V Fix Me V
        Timestamp t = Timestamp.valueOf(punch);
        
        return t;
    }
    
    private Timestamp helperMethod2(LocalDateTime punch, LocalTime time) {
        punch = punch.withHour(time.getHour());
        punch = punch.withMinute(time.getMinute());
        punch = punch.withSecond(0);
        //V Fix Me V
        Timestamp t = Timestamp.valueOf(punch);
        
        return t;
    }

    Object printOriginal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    

    /**
     *
     * @param s a Shift that represents the shift that should be adjusted
     */
  
}
