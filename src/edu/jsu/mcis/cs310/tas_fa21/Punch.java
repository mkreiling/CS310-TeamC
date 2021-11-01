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
import java.time.*;

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
    private Badge badgeid;
    private int terminalID;
    private PunchType punchType;
    private PunchType punchTypeID;
    private int id;
    private LocalDateTime originalTimeStamp;
    private LocalDateTime adjustedTimeStamp;
    private String adjustmentype;
    private String adjustMessage;
    private Timestamp LocalDateTime;
    private LocalDateTime adjustedtimestamp;
    private String adjustmenttype;
    
    public Punch (int id, int terminalID, Badge badge, LocalDateTime originalTimeStamp, PunchType punchTypeID) {
       // Punch punch = new Punch(terminalID, getBadge(badgeid), punchTypeID, originalTimeStamp);
        if(id >= 0){this.id = id;}
        this.terminalID = terminalID;
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
       
        return badgeid;
    }

    public int getTerminalid() {
        return terminalID;
    }

    public PunchType getPunchType() {
        return punchType;
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
    
    public void setBadgeID(Badge badgeid) {
        this.badge = badgeid;
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
        
       
        StringBuilder s = new StringBuilder();
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        
        s.append('#').append(badge.getId()).append(' ');
        s.append(punchTypeID).append(": ").append(dtf.format(originalTimeStamp).toUpperCase());
        
        return (s.toString());
        
        
    }
    public String printAdjusted(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("EEE " + "LL/dd/uuuu HH:mm:ss");
        StringBuilder str = new StringBuilder();
        str.append("#").append(badge.getId()).append(" ").append(punchType).append(": ").
                append(adjustedtimestamp.format(format).toUpperCase()).
                append(" (").
                append(adjustmenttype).
                append(")");
                
        return str.toString();
    }
   
   public void adjust(Shift s){
        LocalDateTime punchTime = originalTimeStamp;
        final int ZERO = 0;
        LocalDateTime zeroPT = punchTime.withSecond(ZERO).withNano(ZERO);
        
        LocalDateTime adjustedPT = null;
        
        LocalDateTime shiftStart = s.getStart().atDate(originalTimeStamp.toLocalDate());
        LocalDateTime shiftStop = s.getStop().atDate(originalTimeStamp.toLocalDate());
        LocalDateTime lunchStart = s.getLunchstart().atDate(originalTimeStamp.toLocalDate());
        LocalDateTime lunchStop = s.getLunchstop().atDate(originalTimeStamp.toLocalDate());
        
        int interval = s.getInterval();
        int gracePeriod = s.getGraceperiod();
        int dock = s.getDock();

        int min = punchTime.getMinute();
        int sec = punchTime.getSecond();
       
        
       
        // punch on time
        if(zeroPT.equals(shiftStart) || zeroPT.equals(shiftStop) || zeroPT.equals(lunchStart) || zeroPT.equals(lunchStop)){
            if (zeroPT.equals(shiftStart)){
                adjustedPT = shiftStart;
                this.adjustmenttype = "None";
                
            }
            else if(zeroPT.equals(shiftStop)){
                adjustedPT = shiftStop;
                this.adjustmenttype = "None";
            }
            else if(zeroPT.equals(lunchStart)){
                adjustedPT = lunchStart;
                this.adjustmenttype = "None";
            }
            else{
                adjustedPT = lunchStop;
                this.adjustmenttype = "None";
            }
            
        }
        // punching in late start
        else if ((punchTime.isAfter(shiftStart)) && (punchTime.isBefore(shiftStart.plusMinutes(interval)) 
                || punchTime.equals(shiftStart.plusMinutes(interval))) && (punchType != PunchType.CLOCK_OUT) 
                && (!originalTimeStamp.getDayOfWeek().equals(DayOfWeek.SATURDAY)) 
                && (!originalTimeStamp.getDayOfWeek().equals(DayOfWeek.SUNDAY))){
            
            if(punchTime.isBefore(shiftStart.plusMinutes(gracePeriod))){
                adjustedPT = shiftStart;
                this.adjustmenttype = "Shift Start";
            }
            else{
                adjustedPT = shiftStart.plusMinutes(dock);
                this.adjustmenttype = "Shift Dock";
            }

        }
        // punching in early start
        else if((punchTime.isBefore(shiftStart)) && (punchTime.isAfter(shiftStart.minusMinutes(interval)) 
                || punchTime.equals(shiftStart.minusMinutes(interval))) && (punchType != PunchType.CLOCK_OUT) 
                && (!originalTimeStamp.getDayOfWeek().equals(DayOfWeek.SATURDAY)) 
                && (!originalTimeStamp.getDayOfWeek().equals(DayOfWeek.SUNDAY)) ){
                adjustedPT = shiftStart;
                this.adjustmenttype = "Shift Start";
        }
       
        
        //punching out early stop
        else if((punchTime.isBefore(shiftStop)) && (punchTime.isAfter(shiftStop.minusMinutes(interval)) 
                || punchTime.equals(shiftStop.minusMinutes(interval))) && (punchType != PunchType.CLOCK_IN) 
                && (!originalTimeStamp.getDayOfWeek().equals(DayOfWeek.SATURDAY)) 
                && (!originalTimeStamp.getDayOfWeek().equals(DayOfWeek.SUNDAY))){
            if(punchTime.isAfter(shiftStop.minusMinutes(gracePeriod))){
                adjustedPT = shiftStop;
                this.adjustmenttype = "Shift Stop";
            }
            else{
                adjustedPT = shiftStop.minusMinutes(dock);
                this.adjustmenttype = "Shift Dock";
             }        
        }
        
        // punch out late stop
        else if((punchTime.isAfter(shiftStop)) && (punchTime.isBefore(shiftStop.plusMinutes(interval)) 
                || punchTime.equals(shiftStop.plusMinutes(interval))) && (punchType != PunchType.CLOCK_IN) 
                && (!originalTimeStamp.getDayOfWeek().equals(DayOfWeek.SATURDAY)) 
                && (!originalTimeStamp.getDayOfWeek().equals(DayOfWeek.SUNDAY))){
            adjustedPT = shiftStop;
            this.adjustmenttype = "Shift Stop";
        }
        
        // punch in late lunchstart
        else if((punchTime.isAfter(lunchStart)) && (punchTime.isBefore(lunchStop)) 
                && (!originalTimeStamp.getDayOfWeek().equals(DayOfWeek.SATURDAY)) 
                && (!originalTimeStamp.getDayOfWeek().equals(DayOfWeek.SUNDAY))){
            if(punchType.equals(PunchType.CLOCK_OUT)){
                adjustedPT = lunchStart;
                this.adjustmenttype = "Lunch Start";
            }
            else if(punchType.equals(PunchType.CLOCK_IN)){
                adjustedPT = lunchStop;
                this.adjustmenttype = "Lunch Stop";
            }
            
        }
        
        // interval round
        else if ((!(min == interval)) && (!(min == (interval * 2))) && (!(min == (interval * 3))) && (!(min == ZERO))){
            int mod = min % interval;
            if(mod < 8){
                if(sec < 30){
                    adjustedPT = punchTime.minusMinutes(mod).withSecond(0);
                    this.adjustmenttype = "Interval round";   
                }
                else{
                    adjustedPT = punchTime.plusMinutes(interval - mod).withSecond(0);
                    this.adjustmenttype = "Interval round";
                }
                
            }
            else if(mod > 8){
                adjustedPT = punchTime.plusMinutes(interval - mod).withSecond(0);
                this.adjustmenttype = "Interval Round";
            }
           
           
            
        }
        else{
            adjustedPT = punchTime.withSecond(ZERO).withNano(ZERO);
            this.adjustmenttype = "None";
        }

        this.adjustedtimestamp =adjustedPT;
    } 
}




