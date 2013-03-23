class Monster
{
    // instance variables - replace the example below with your own
    private String name;
    private int hp;
    private int minDmg, maxDmg;

    /**
     * Constructor for objects of class Monster
     */
    public Monster(String monsterName, int monsterHP, int minDamage, int maxDamage)
    {
        // initialise instance variables
        this.name = monsterName;
        this.hp = monsterHP;
        minDmg = minDamage;
        maxDmg = maxDamage;
    }

    
    public String getName(){
        return name;
    }
    
    
    public void addHitpoints(int addHP)
    {
        hp = hp + addHP;
    }

    
    public int getHP(){
        return hp;
    }//END getHP
    
    
    public int getMinDmg(){
        return minDmg;
    }//END getMinDmg
    
    
    public int getMaxDmg(){
        return maxDmg;
    }//END getMaxDmg
}
