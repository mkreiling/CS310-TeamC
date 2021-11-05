//Team C Project Authors
//Matthew Kreiling
//Daniel Edberg
//Jacob Davis
//Tajuddin Idrisa Mwijage
//Stephen Littlefield






package edu.jsu.mcis.cs310.tas_fa21;

public class Badge {
    
    //Variables for id and name
    public final String id;
    public final String Description;  

    //Constructor for badge
    public Badge(String id, String badgeDescription) {
        this.id = id;
        this.Description = badgeDescription;
    }
    
    //Getters
    public String getId() {
        return id;
    }

    public String getDescription() {
        return Description;
    }

    
    //toString method will print the id number and name of the badge owner

    @Override
    public String toString() {
      
        StringBuilder s = new StringBuilder();
        
        s.append('#').append(id).append(" (").append(Description).append(')');
        
        return s.toString(); 
    }
    
}



