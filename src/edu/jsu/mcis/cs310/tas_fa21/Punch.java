//Team C Project Authors
//Matthew Kreiling
//Daniel Edberg
//Jacob Davis
//Tajuddin Idrisa Mwijage
//Stephen Littlefield





package edu.jsu.mcis.cs310.tas_fa21;


import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 

/**
 *
 * @TeamC
 */
public class Punch {

    private Badge badge;
    private int terminalID;
    private String PunchType;
    private PunchType punchTypeID;
    private int id;
    private LocalDateTime originalTimeStamp;
    private LocalDateTime adjustedTimeStamp;
    private String adjustmentype;
    private String adjustMessage;
    private Timestamp LocalDateTime;
    
    public Punch (int id, int terminalID, Badge badge, LocalDateTime originalTimeStamp, PunchType punchTypeID) {
        //Punch punch = new Punch(terminalid, getBadge(badgeid), punchtypeid, originaltimestamp);
        if(id >= 0){this.id = id;}
        this.terminalID = terminalID;
        //this.BadgeID = badge.getID();
        this.badge = badge;
        this.originalTimeStamp = originalTimeStamp;
        this.punchTypeID = punchTypeID;
        
    }

    public Punch(int terminalID,Badge badge, PunchType punchTypeID, LocalDateTime originalTimeStamp){
        this.terminalID=terminalID;
       this.badge= badge;
       this.originalTimeStamp = originalTimeStamp;
       this.punchTypeID=punchTypeID;
       

    }

    Punch(int i, Badge badge, int i0) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        this.badge= badge;
    }

    
    public Badge getBadge() {
        return badge;
    }
   
    public Badge getBadgeid() {
        Badge badgeid = null;
        return badgeid;
    }

    public int getTerminalid() {
        return terminalID;
    }

    public PunchType getPunchtype() {
        return punchTypeID;
    }

    public int getId() {
        return id;
    }
    
  
    
    public LocalDateTime getOriginaltimestamp() {
        return originalTimeStamp;
    }
    
    public LocalDateTime getAdjustedTimeStamp() {
        return adjustedTimeStamp;
    }
        public void setOriginalTimeStamp(Timestamp originalTimeStamp) {
        this.LocalDateTime = originalTimeStamp;
    }
    
    public void setBadgeID(Badge badgeID) {
        this.badge = badgeID;
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

    
    public String printOriginal(){
        
        // #D2C39273 CLOCK IN: WED 09/05/2018 07:00:07
        
        StringBuilder s = new StringBuilder();
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        
        s.append('#').append(badge.getId()).append(' ');
        s.append(punchTypeID).append(": ").append(dtf.format(originalTimeStamp).toUpperCase());
        
        return (s.toString());
        
        
    }

   
    
}




