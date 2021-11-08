//Team C Project Authors
//Matthew Kreiling
//Daniel Edberg
//Jacob Davis
//Tajuddin Idrisa Mwijage
//Stephen Littlefield





package edu.jsu.mcis.cs310.tas_fa21;

/*
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;
import java.time.*;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
*/

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 

/**
 *
 * @TeamC
 */
public class Punch {
    
    private int id;
    private Badge badge;
    private Badge badgeid;
    private int terminalid;
    private PunchType punchType;
    private PunchType punchTypeid;
    private LocalDateTime originalTimeStamp;
    private LocalDateTime adjustedTimeStamp;
    
    private String adjustMessage;
    //private Timestamp LocalDateTime;
    private LocalDateTime adjustedtimestamp;
    private String adjustmenttype;
    
   
   
    
     public Punch(int terminalid, Badge badgeid, int punchtypeid) {
          
        this.badgeid = badgeid;
        this.terminalid = terminalid;
        this.originalTimeStamp = LocalDateTime.now();
        this.punchTypeid = PunchType.values()[punchtypeid];
        this.adjustedtimestamp = LocalDateTime.now(); 
        this.adjustmenttype = null;
    }
      
    public Punch(int terminalid, Badge badgeid, int punchtypeid, LocalDateTime originalTimeStamp, int punchid) {
          
        this.badgeid = badgeid;
        this.terminalid = terminalid;
        this.originalTimeStamp = originalTimeStamp;
        this.punchTypeid = PunchType.values()[punchtypeid];
        this.id = punchid;

        this.adjustedtimestamp = originalTimeStamp; 
        this.adjustmenttype = null;
    }
    
     public int getId(){
        return id; 
    }
       
    public int getTerminalid(){
        return terminalid;
    }

    public void setId(int id){
        this.id = id;
    }
        
    public Badge getBadge(){
        return badgeid;
    }
        
    public LocalDateTime getOriginaltimestamp() {
        return originalTimeStamp;
    }
     
    public PunchType getPunchType() {
        return punchTypeid;
    }
        
    public String getAdjustmenttype() {
        return adjustmenttype;
    }
    
    public LocalDateTime getAdjustedtimestamp(){
        return adjustedtimestamp;
    }
    
    public void setOriginalTimeStamp(LocalDateTime originaltimestamp) {
        this.originalTimeStamp = originaltimestamp;
    }
        
    public void setAdjustmenttype(String adjustmenttype) {
        this.adjustmenttype = adjustmenttype;
    }
    
      
     
       public void adjust(Shift s){

        TemporalField usweekday = WeekFields.of(Locale.US).dayOfWeek();

        LocalDateTime shiftstart = s.getStart().atDate(originalTimeStamp.toLocalDate());
        shiftstart = shiftstart.withSecond(0).withNano(0);
       
        LocalDateTime shiftstop = s.getStop().atDate(originalTimeStamp.toLocalDate()); 
        shiftstop = shiftstop.withSecond(0).withNano(0);
            
        LocalDateTime lunchstart = s.getLunchstart().atDate(originalTimeStamp.toLocalDate()); 
        LocalDateTime lunchstop = s.getLunchstop().atDate(originalTimeStamp.toLocalDate()); 
           
        LocalDateTime shiftstartgrace = shiftstart.plusMinutes(s.getGraceperiod()); 
        
        LocalDateTime shiftstopgrace = shiftstop.minusMinutes(s.getGraceperiod()); 
           
        LocalDateTime shiftstartplus = shiftstart.plusMinutes(s.getInterval()); 
        LocalDateTime shiftstartminus = shiftstart.minusMinutes(s.getInterval()); 
           
        LocalDateTime shiftstopplus = shiftstop.plusMinutes(s.getInterval()); 
        LocalDateTime shiftstopminus = shiftstop.minusMinutes(s.getInterval()); 
           
        LocalDateTime shiftstartdockplus = shiftstart.plusMinutes(s.getDock());
        LocalDateTime shiftstopdockminus = shiftstop.minusMinutes(s.getDock()); 
            
        int dayofweek = originalTimeStamp.get(usweekday);
        int roundint = originalTimeStamp.toLocalTime().getMinute() % s.getInterval(); 
        int h = s.getInterval()/2; 
        long roundlong;
            
        //Punchtypes
        if(punchTypeid == PunchType.CLOCK_IN){
               
            //weekdays(Mon-Fri) 
            if(dayofweek != Calendar.SATURDAY && dayofweek != Calendar.SUNDAY){
               
                if (originalTimeStamp.withSecond(0).withNano(0).isEqual(shiftstart)) {
                    adjustmenttype = "None";
                    adjustedtimestamp = shiftstart;
                }  
                    
                else if (originalTimeStamp.withSecond(0).withNano(0).isEqual(lunchstop)) {
                    adjustmenttype = "None";
                    adjustedtimestamp = lunchstop;
                }
                   
                //Early Shift start 
                else if((originalTimeStamp.isAfter(shiftstartminus) && originalTimeStamp.isEqual(shiftstartminus)) || originalTimeStamp.isBefore(shiftstart)) { // equal????
                    adjustmenttype = "Shift Start";
                    adjustedtimestamp = shiftstart; 
                }
                   
                //Late Shift start within the Grace period
                else if ((originalTimeStamp.isAfter(shiftstart) && originalTimeStamp.isEqual(shiftstart)) || originalTimeStamp.isBefore(shiftstartgrace)) { 
                    adjustmenttype = "Shift Start";
                    adjustedtimestamp = shiftstart; 
                }
                    
                //Late shift start outside grace period
                else if ((originalTimeStamp.isAfter(shiftstartgrace) && originalTimeStamp.isEqual(shiftstartgrace)) || originalTimeStamp.isBefore(shiftstartplus)) {
                    adjustmenttype = "Shift Dock";
                    adjustedtimestamp = shiftstartdockplus;
                }
                    
                //Early return from lunch.
                else if ((originalTimeStamp.isAfter(lunchstart) && originalTimeStamp.isEqual(lunchstart)) || originalTimeStamp.isBefore(lunchstop)) {
                    adjustmenttype = "Lunch Stop"; 
                    adjustedtimestamp = lunchstop; 
                }
                    
                else {
                    
                    if (roundint != 0) {
                        //round down.
                        if(roundint < h) { 
                            roundlong = new Long(roundint);
                            adjustmenttype = "Interval Round";
                            adjustedtimestamp = originalTimeStamp.minusMinutes(roundlong).withSecond(0);
                        } 
                    
                        //round up.
                        else if(roundint >= h){ 
                            roundlong = new Long(s.getInterval() - roundint);
                            adjustmenttype = "Interval Round";
                            adjustedtimestamp = originalTimeStamp.plusMinutes(roundlong).withSecond(0);
                        }
                    }
                    else {
                        adjustmenttype = "None";
                        adjustedtimestamp = originalTimeStamp.withSecond(0).withNano(0);
                    }
                }
            }
                    
            else{
                // Saturday CLOCKIN
                
                if (roundint != 0) {
                    
                    //round down.
                    if(roundint < h) { 
                        roundlong = new Long(roundint);
                        adjustmenttype = "Interval Round";
                        adjustedtimestamp = originalTimeStamp.minusMinutes(roundlong).withSecond(0);
                    }    
                    
                    //round up.
                    else if(roundint >= h){ 
                        roundlong = new Long(s.getInterval() - roundint);
                        adjustmenttype = "Interval Round";
                        adjustedtimestamp = originalTimeStamp.plusMinutes(roundlong).withSecond(0);
                    }
                }
                
                else {
                    adjustmenttype = "None";
                    adjustedtimestamp = originalTimeStamp.withSecond(0).withNano(0);
                }
            }

        }
         
        else if (punchTypeid == PunchType.CLOCK_OUT){
               
            //weekdays
            if(dayofweek != Calendar.SATURDAY && dayofweek != Calendar.SUNDAY){
                 
                if (originalTimeStamp.withSecond(0).withNano(0).isEqual(shiftstop)) {
                    adjustmenttype = "None";
                    adjustedtimestamp = shiftstop;
                } 
                   
                else if (originalTimeStamp.withSecond(0).withNano(0).isEqual(lunchstart)) {
                    adjustmenttype = "None";
                    adjustedtimestamp = lunchstart;
                }
                    
                //Early departure outside the grace period.
                else if((originalTimeStamp.isAfter(shiftstopminus) || originalTimeStamp.isEqual(shiftstopminus))&& 
                        (originalTimeStamp.isBefore(shiftstopgrace) || originalTimeStamp.isEqual(shiftstopgrace))){
                    adjustmenttype = "Shift Dock";
                    adjustedtimestamp = shiftstopdockminus; 
                }
                    
                //early departure within the grace period. 
                else if(originalTimeStamp.isAfter(shiftstopgrace) && (originalTimeStamp.isBefore(shiftstop) || originalTimeStamp.isEqual(shiftstop))){
                    adjustmenttype = "Shift Stop"; 
                    adjustedtimestamp = shiftstop; 
                }
                    
                //Late departure. 
                else if ((originalTimeStamp.isAfter(shiftstop) && originalTimeStamp.isBefore(shiftstopplus)) || originalTimeStamp.isEqual(shiftstopplus)) {
                    adjustmenttype = "Shift Stop"; 
                    adjustedtimestamp = shiftstop;         
                }
                   
                //Late departure for lunch
                else if (originalTimeStamp.isAfter(lunchstart) && (originalTimeStamp.isBefore(lunchstop) || originalTimeStamp.isEqual(lunchstop))){
                    adjustmenttype = "Lunch Start";
                    adjustedtimestamp = lunchstart;    
                }
                else {
                    if (roundint != 0) {
                        //round down.
                        if(roundint < h) { 
                            roundlong = new Long(roundint);
                            adjustmenttype = "Interval Round";
                            adjustedtimestamp = originalTimeStamp.minusMinutes(roundlong).withSecond(0);
                        } 
                    
                        //round up.
                        else if(roundint >= h){ 
                            roundlong = new Long(s.getInterval() - roundint);
                            adjustmenttype = "Interval Round";
                            adjustedtimestamp = originalTimeStamp.plusMinutes(roundlong).withSecond(0);
                        }
                    }
                    
                    else {
                        adjustmenttype = "None";
                        adjustedtimestamp = originalTimeStamp.withSecond(0).withNano(0);
                    }
                }
            }
            else{
                // Saturday CLOCKOUT
                
                if (roundint != 0) {
                    //round down.
                    if(roundint < h) { 
                        roundlong = new Long(roundint);
                        adjustmenttype = "Interval Round";
                        adjustedtimestamp = originalTimeStamp.minusMinutes(roundlong).withSecond(0);
                    }    

                    //round up.
                    else if(roundint >= h){ 
                        roundlong = new Long(s.getInterval() - roundint);
                        adjustmenttype = "Interval Round";
                        adjustedtimestamp = originalTimeStamp.plusMinutes(roundlong).withSecond(0);
                    }
                }
                
                else {
                    adjustmenttype = "None";
                    adjustedtimestamp = originalTimeStamp.withSecond(0).withNano(0);
                }
            }
        }
 
    }
  
       public String printOriginal(){
            
        StringBuilder str = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
         


        str.append('#').append(badgeid.getId()).append(" ").append(punchTypeid);
        str.append(": ").append((formatter.format(originalTimeStamp)).toUpperCase());
        System.out.println(str.toString());

        return str.toString();
    }
        public String printAdjusted(){

        StringBuilder str = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");

        str.append('#').append(badgeid.getId()).append(" ").append(punchTypeid);
        str.append(": ").append(formatter.format(adjustedtimestamp).toUpperCase());
        str.append(" (").append(adjustmenttype).append(")");
        System.out.println(str);
            
        return str.toString();
        
    }

}



