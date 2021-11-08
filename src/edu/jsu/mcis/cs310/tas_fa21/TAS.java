//Team C Project Authors
//Matthew Kreiling
//Daniel Edberg
//Jacob Davis
//Tajuddin Idrisa Mwijage
//Stephen Littlefield

package edu.jsu.mcis.cs310.tas_fa21;


import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.*;

public class TAS {
     
    public static void main(String[] args) {
        TASDatabase database = new TASDatabase();
        
        Punch p = database.getPunch(69);
        Badge b = p.getBadge();
        Shift s = database.getShift(b);
        
        ArrayList<Punch> dailypunchlist = database.getDailyPunchList(b, p.getOriginaltimestamp().toLocalDate());
        
        for (Punch punch : dailypunchlist){
            punch.adjust(s);
        }
        //calculateTotalMinutes(dailypunchlist, s);
        
    }
    /*
    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift){
        int time = 0; 
        
        
        try{

            for(int i = 0; i < dailypunchlist.size(); i+= 2){

                Duration dur = Duration.between(dailypunchlist.get(i).getAdjustedtimestamp(), dailypunchlist.get(i+1).getAdjustedtimestamp());

                int minutesTotal = (int)dur.toMinutes();
                time = time + minutesTotal;

        
            }
            
               boolean clockout = false;
               for(Punch p : dailypunchlist){ 
                     
                    if (p.getAdjustedtimestamp().toLocalTime().equals(shift.getLunchstart())){

                        clockout = true; 
                        break;
                     }
               }

               if(!clockout){
                   time = (int) (time - shift.getLunchduration());
               }
               
        }
        catch(IndexOutOfBoundsException e){System.out.println("calculateTotalMinutes Error!" + e);} 
        catch(Exception e){e.printStackTrace();}

        return time;
        
        
    }*/
    /*
    public static String getPunchListAsJSON(ArrayList<Punch> dailypunchlist){
     
   
        ArrayList<HashMap<String, String>> jsonData = new ArrayList<>();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("EEE" + " LL/dd/uuuu HH:mm:ss" );
         
        for(Punch punch : dailypunchlist){
            
            HashMap<String, String> punchData = new HashMap<>();

            punchData.put("originaltimestamp", String.valueOf(punch.getOriginaltimestamp().format(format).toUpperCase()));
            punchData.put("badgeid", String.valueOf(punch.getBadge().getId()));
            punchData.put("adjustedtimestamp", String.valueOf(punch.getAdjustedtimestamp().format(format).toUpperCase()));
            punchData.put("adjustmenttype", String.valueOf(punch.getAdjustmenttype()));
            punchData.put("terminalid", String.valueOf(punch.getTerminalid()));
            punchData.put("id",String.valueOf(punch.getId())); 
            punchData.put("punchtype", String.valueOf(punch.getPunchType()));
            
            jsonData.add(punchData);
        }
        
        String json = JSONValue.toJSONString(jsonData);
        return json;
    }
*/

     public static double calculateAbsenteeism(ArrayList<Punch> punchlist, Shift s){
        

        return 0; 
    }
     
}
