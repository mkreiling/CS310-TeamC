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
import java.util.GregorianCalendar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @TeamC
 */
public class Punch {
    private int badgeID;
    private int terminalID;
    private Badge badge;
    private PunchType punchTypeID;
    private int id;
    private LocalDateTime originalTimeStamp;
    private LocalDateTime adjustedTimeStamp;
    private String adjustMessage;
    private Timestamp LocalDateTime;
    
    public Punch (int id, int terminalID, Badge badge, LocalDateTime originalTimeStamp, PunchType punchTypeID) {
        
        if(id >= 0){this.id = id;}
        this.terminalID = terminalID;
        this.badge = badge;
        this.originalTimeStamp = originalTimeStamp;
        this.punchTypeID = punchTypeID;
        
    }

    public Punch(Badge badge, int terminalID, PunchType punchTypeID, LocalDateTime originalTimeStamp){
        this.terminalID=terminalID;
       this.badge= badge;
       this.originalTimeStamp = originalTimeStamp;
       this.punchTypeID=punchTypeID;
    }
    
   
    public Badge getBadgeid() {
        return badge;
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
    
  
    
    public LocalDateTime getOriginalTimestamp() {
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
        return "#" + badgeID + " " + punchTypeID + ": " + originalTimeStamp;
    }
    
}
