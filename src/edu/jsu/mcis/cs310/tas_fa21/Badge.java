//Team C Project Authors
//Matthew Kreiling
//Daniel Edberg
//Jacob Davis
//Tajuddin Idrisa Mwijage
//Stephen Littlefield






package edu.jsu.mcis.cs310.tas_fa21;

public class Badge {
    
    //Variables for id and name
    private String BadgeID, BadgeDescription;  

    //Constructor for badge
    public Badge(String id, String descript) {
        this.BadgeID = id;
        this.BadgeDescription = descript;
    }
    
    //Getters for id number and name
    public String getId() {
        return BadgeID;
    }

    public String getDescription() {
        return BadgeDescription;
    }

    
    //toString method will print the id number and name of the badge owner

    @Override
    public String toString() {
        return "Badge{" + "id=" + BadgeID + ", description=" + BadgeDescription + '}';
    }
    
}



