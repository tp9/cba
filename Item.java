class Item
{
    //Item attributes
    private String description, name;
    private int weight;

    
    /**
     * Constructor for objects of class Item
     */
    public Item(String name, String description, int weight)
    {
        // initialise instance variables
        this.name = name;
        this.description = description;
        this.weight = weight;
    }


    public String getDescription() {
        return description;
    }//END getDescription    
    
    
    public String getName() {
        return name;
    }//END setLocation
   
    
    public int getWeight(){
        return weight;
    }//END getWeight
}
