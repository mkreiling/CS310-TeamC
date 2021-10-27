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
import java.time.LocalTime;

public class Shift {
    
    //variables
    
    private int id, gracePeriod, dock, interval, lunchDeduct;
    private LocalTime shiftBegin, lunchBegin, lunchEnd, shiftEnd;
    private final String description;
    
    public Shift(int id, int interval, LocalTime begin, int gracePeriod, int dock, LocalTime lunchBegin, int lunchDeduct, LocalTime lunchEnd, LocalTime end, String description)
    {
        this.id = id;
        this.interval = interval;
        this.shiftBegin = begin;
        this.gracePeriod = gracePeriod;
        this.dock = dock;
        this.lunchBegin = lunchBegin;
        this.lunchDeduct = lunchDeduct;
        this.lunchEnd = lunchEnd;
        this.shiftEnd = end;
        this.description = description;
    }
    
    //gets
    public int getId()
    {
        return id;
    }
    public int getInterval()
    {
        return interval;
    }
        public LocalTime getShiftBegin()
    {
        return shiftBegin;
    }
    public int getGracePeriod()
    {
        return gracePeriod;
    }
    public int getDock()
    {
        return dock;
    }
    public LocalTime getLunchBegin()
    {
        return lunchBegin;
    }
    public int getLunchDeduct()
    {
        return lunchDeduct;
    }
    public LocalTime getLunchEnd()
    {
        return lunchEnd;
    }
    public LocalTime getShiftEnd()
    {
        return shiftEnd;
    }
    public String getDescription()
    {
        return description;
    }
    /*private long getElapsedTime(LocalTime s, LocalTime e)
    {
        Calendar BeginCal = GregorianCalendar.getInstance();
        Calendar endCal = GregorianCalendar.getInstance();
        BeginCal.setTimeInMillis(s.getTime());
        endCal.setTimeInMillis(e.getTime());

        long begin, end;
        begin = BeginCal.getTimeInMillis();
        end = endCal.getTimeInMillis();
        return (end - begin) / (60 * 1000);
    }*/
    // formats and Overrides toString
    /*@Override
    public String toString()
    {
        String beginTime = (new SimpleDateFormat("HH:mm")).format(shiftBegin.getTime());
        String lunchBeginTime = (new SimpleDateFormat("HH:mm")).format(lunchBegin.getTime());
        String lunchEndTime = (new SimpleDateFormat("HH:mm")).format(lunchEnd.getTime());
        String endTime = (new SimpleDateFormat("HH:mm")).format(shiftEnd.getTime());
        String data = "";
        
        data += description + ": ";
        data += beginTime + " - ";
        data += getElapsedTime(shiftBegin, shiftEnd) + " minutes);";
        data += " Lunch: " + lunchBeginTime + " - ";
        data += lunchEndTime + " (";
        data += getElapsedTime(lunchBegin, lunchEnd) + " minutes)";
        data += endTime + " (";
        return data;
    }*/
}




