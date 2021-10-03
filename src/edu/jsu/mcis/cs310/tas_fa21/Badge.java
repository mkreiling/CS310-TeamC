package edu.jsu.mcis.cs310.tas_fa21;

public class Badge {
    
    //Variables for id and name
    private String id, description;  

    //Constructor for badge
    public Badge(String id, String description) {
        this.id = id;
        this.description = description;
    }

    
    //Setters for id number and name
    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    //Getters for id number and name
    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    
    //toString method will print the id number and name of the badge owner

    @Override
    public String toString() {
        return "Badge{" + "id=" + id + ", description=" + description + '}';
    }
    
}
