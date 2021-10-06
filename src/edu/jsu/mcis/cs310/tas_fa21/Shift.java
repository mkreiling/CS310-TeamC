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
import java.sql.*;
public class Shift {
    //variables
    private int id, gracePeriod, dock, interval, lunchDeduct;
    private Time shiftBegin, lunchBegin, lunchEnd, shiftEnd;
    private final String description;
    
    public Shift(int id, int interval, Time begin, int gracePeriod, int dock, Time lunchBegin, int lunchDeduct, Time lunchEnd, Time end, String description)
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
        public Time getShiftBegin()
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
    public Time getLunchBegin()
    {
        return lunchBegin;
    }
    public int getLunchDeduct()
    {
        return lunchDeduct;
    }
    public Time getLunchEnd()
    {
        return lunchEnd;
    }
    public Time getShiftEnd()
    {
        return shiftEnd;
    }
    public String getDescription()
    {
        return description;
    }
    private long getElapsedTime(Time s, Time e)
    {
        Calendar BeginCal = GregorianCalendar.getInstance();
        Calendar endCal = GregorianCalendar.getInstance();
        BeginCal.setTimeInMillis(s.getTime());
        endCal.setTimeInMillis(e.getTime());

        long begin, end;
        begin = BeginCal.getTimeInMillis();
        end = endCal.getTimeInMillis();
        return (end - begin) / (60 * 1000);
    }
    // formats and Overrides toString
    @Override
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
    }
}
