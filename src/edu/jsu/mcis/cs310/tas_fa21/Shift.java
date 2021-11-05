//Team C Project Authors
//Matthew Kreiling
//Daniel Edberg
//Jacob Davis
//Tajuddin Idrisa Mwijage
//Stephen Littlefield



package edu.jsu.mcis.cs310.tas_fa21;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.time.*;
import java.time.temporal.ChronoUnit;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Shift {
    
    //variables
    private int id;
    private final int MINPERHOUR = 60;
    private String description;
    private int interval;
    private int graceperiod;
    private int dock;
    private int lunchdeduct;
    private int shiftid;
    private int lunchduration;
    private int shiftduration; 
    private LocalTime start;
    private LocalTime stop;
    private LocalTime lunchstart; 
    private LocalTime lunchstop;
    //private LocalTime lunchstart;//Detects when lunch will start (locally).
    //private LocalTime lunchstop; //Detects when lunch will stop (locally).
    private int lunchDeductTime; //Will deduct the time from employee.
   // private long lunchduration; //Length of lunch duration.
    //private long shiftduration; /
    
    
    public Shift(ShiftParameters params) {
        this.description = params.getDescription();
    
        this.interval = params.getInterval();
        this.graceperiod = params.getGraceperiod();
        this.dock = params.getDock();
        this.lunchdeduct = params.getLunchdeduct();
 
    
        this.start = params.getStart();
        this.stop = params.getStop();
        this.lunchstart = params.getLunchstart();
        this.lunchstop = params.getLunchstop();
        this.shiftid = params.getId();

        this.
        setShiftduration(params.getStart(), params.getStop());
        setLunchduration(params.getLunchstart(), params.getLunchstop());
    }
    
    
    //gets
        
        
        
       public int getId() {
        return id; 
    }
    
    public String getDescription()
    {
        return description;
    }
    public LocalTime getStart(){
        return start;
    }
    public LocalTime getStop(){
        return stop;
    }
    public int getInterval(){
        return interval;
    }
    public int getGraceperiod() {
     
        return graceperiod;
    }

    public int getDock() {
     
        return dock;
    }

    public LocalTime getLunchstart() {
      
        return lunchstart;
    }

    public LocalTime getLunchstop() {
        return lunchstop;
    }

    public int getLunchdeduct() {
        return lunchdeduct;
    }
     public int getLunchDeductTime() {
        return lunchDeductTime;
    }
    
    public int getShiftid() {
        return shiftid;
    }

    public int getLunchduration() {
        return lunchduration;
    }

    public int getShiftduration() {
        return shiftduration;
    }
   
    
    
    
    private void setShiftduration(LocalTime start, LocalTime stop){
        int startmin = start.getHour() * MINPERHOUR + start.getMinute();
        int stopmin = stop.getHour() * MINPERHOUR + stop.getMinute();
      
        this.shiftduration = stopmin - startmin; 
    }
    
    private void setLunchduration(LocalTime lunchstart, LocalTime lunchstop){
        int startmin = (lunchstart.getHour() * MINPERHOUR) + lunchstart.getMinute();
        int stopmin = (lunchstop.getHour() * MINPERHOUR) + lunchstop.getMinute();
       
        this.lunchduration = stopmin - startmin;      
    }
    
     @Override
    public String toString() {
    
        String beginTime = start.toString();
        String lunchBeginTime = lunchstart.toString();
        String lunchEndTime = lunchstop.toString();
        String endTime = stop.toString();

        StringBuilder d = new StringBuilder();
        d.append(description).append(": ").append(beginTime).append(" - ").append(endTime).append(" (");
        d.append(start.until(stop, ChronoUnit.MINUTES)).append(" minutes); ");
        d.append("Lunch: ").append(lunchBeginTime).append(" - ").append(lunchEndTime).append(" (").append(lunchstart.until(lunchstop, ChronoUnit.MINUTES));
        d.append(" minutes)");
        
        
        return d.toString();
    }
  
 
  
}




