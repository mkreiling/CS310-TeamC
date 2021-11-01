//Team C Project Authors
//Matthew Kreiling
//Daniel Edberg
//Jacob Davis
//Tajuddin Idrisa Mwijage
//Stephen Littlefield






package edu.jsu.mcis.cs310.tas_fa21;

public class Badge {
    
    //Variables for id and name
    private String ID;
    private String Description;  

    //Constructor for badge
    public Badge(String badgeid, String badgeDescription) {
        this.ID = badgeid;
        this.Description = badgeDescription;
    }
    
    //Getters
    public String getId() {
        return ID;
    }
        public void setID(String newID){
            this.ID = newID;
        }
    public String getDescription() {
        return Description;
    }
 public void setDescription(String newDescription) {
        this.Description = newDescription;
    }
    
    //toString method will print the id number and name of the badge owner

    @Override
    public String toString() {
      
        StringBuilder s = new StringBuilder();
        
        s.append('#').append(ID).append(" (").append(Description).append(')');
        
        return s.toString(); 
    }
    
}



