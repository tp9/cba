import java.util.ArrayList;

class Player
{
    // instance variables - replace the example below with your own
    private String name;
    private ArrayList inventory;
    private int hp, maxHp, lvl, maxWeight;
    private int minDmg, maxDmg;

    
    /**
     * Constructor for objects of class Player
     */
    public Player(String name/*, int hp, int lvl*/)
    {
        // initialise instance variables
        this.name = name;
        this.hp = 10;
        this.maxWeight = 30;
        setMaxHitpoints(20);
        setLevel(1);
        setDamage(10, 20);
        
        // initialize inventory
//        inventory = new Item[16];
        inventory = new ArrayList(1);
        
    }


    public String getName()
    {
        // put your code here
        return this.name;
    }


    public void addHitpoints(int hp){
        this.hp = this.hp + hp;
    }//END setHitpoints
    
    
    public int getMaxHitpoints(){
        return maxHp;
    }//END getHitpoints
    
    
    public void setMaxHitpoints(int maxHitpoints){
        this.maxHp = maxHitpoints;
    }//END setHitpoints
    
    
    public int getHitpoints(){
        return hp;
    }//END getHitpoints

    
    public void setLevel(int lvl){
        this.lvl = this.lvl + lvl;
    }//END setLevel
    
    
    public int getLevel(){
        return lvl;
    }//END getLevel
    
    
    public void setDamage(int min, int max){
        minDmg = min;
        maxDmg = max;
    }//END setDamage
    
    
    public int getMinDmg(){
        return minDmg;    
    }//END getMinDmg
    
    
    public int getMaxDmg(){
        return maxDmg;
    }//END getMaxDmg

    public int getMaxWeight(){
        return maxWeight;
    }//END getMaxWeight
    
//INVENTORY--------------------------------------------------------------------    
//    public Item[] getInventory(){
    public ArrayList getInventory(){
        return this.inventory;    
    }

    
    public int getTotWeight(){
        int totWeight = 0;
        
        if (!this.inventory.isEmpty()){
            for(int i = 0; i < this.inventory.size(); i++)
                totWeight += ((Item)this.inventory.get(i)).getWeight();
        }
        
        return totWeight;
    }//END getTotWeight
    
        
    public boolean itemIn(Item item){
        boolean success = false; //CHANGE TO TRUE IF ITEM ADDED TO INV
        
        if ((item.getWeight() + getTotWeight()) <= maxWeight) {
            this.inventory.add(item);
//            System.out.println("Item added");
            success = true;
        }
        
//         else
//             System.out.println("Your inventory is full");
        
        return success;
    }
    

    public boolean itemOut(String itemName){
        boolean success = false; //CHANGE TO TRUE IF ITEM REMOVED
        
        if(this.inventory.isEmpty())
            System.out.println("Your inventory is empty");
        else {
            for(int i = 0; i < this.inventory.size(); i++) {
                if(((Item)inventory.get(i)).getName().equalsIgnoreCase(itemName)) {
                    inventory.remove(i);
                    success = true;
                    break;
                }
            }
        }
                
        return success;
    }
    
    
    public int searchInventory(String itemName){
        boolean found = false;
        int index = -1;
        
        if(this.inventory.isEmpty())
            index = -2;
        else {
            for(int i = 0; i < this.inventory.size(); i++) {
                if(((Item)inventory.get(i)).getName().equalsIgnoreCase(itemName)) {
                    index = i;
                    found = true;
                    break;
                }
            }
        }        
        
        return index;
    }
    
    
//     public Item itemOut(String itemName){
//         Item tempItem = null;
// 
//         
//         
//         int i=0;
//         do {
//             if(this.inventory[i] != null){
//                 if (this.inventory[i].getName().equalsIgnoreCase(itemName)){
//                     tempItem = this.inventory[i];
//                     this.inventory[i] = null;
//                 }
//             }
//             i++;
//         } while (i<16 && tempItem == null);
//         
//         return tempItem;
//     }

}
