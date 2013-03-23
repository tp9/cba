/** *  Author:  WOKUMURA *  Version: 1.0 *  Date:    August 2009 *  *  To play this game, create an instance of this class and call the "play" *  routine. *  *  This game was created from a template created by Michael Kolling. */import java.io.*;import java.util.Random;import java.util.ArrayList;class Game {    private Parser parser;    private Room currentRoom;    private Player currentPlayer;    private Random generator = new Random();            /**     * Create the game and initialise its internal map.     */    public Game()     {        createRooms();        parser = new Parser();    }        /**     * Create all the rooms and link their exits together.     */    private void createRooms()    {        Room outside, buildingA, buildingB, courtyard, office, netlab, clubroom;        // create the rooms        outside = new Room("outside the Shidler College of Business");        buildingA = new Room("Building A");        buildingB = new Room("Building B");        courtyard = new Room("the courtyard of the CBA");        office = new Room("the main admin office");        netlab = new Room("the CBA computer room");        clubroom = new Room("the clubroom");                // initialise room exits(n,e,s,w)        outside.setExits(courtyard, null, null, null);        buildingA.setExits(null, office, buildingB, clubroom);        buildingB.setExits(buildingA, null, netlab, null);        courtyard.setExits(office, null, outside, buildingB);        office.setExits(null, null, courtyard, buildingA);        netlab.setExits(buildingB, null, null, null);        clubroom.setExits(null, buildingA, null, null);        // spawn items        Item key = new Item("key", "A dull copper key", 30);        Item potion = new Item("potion", "A small potion of healing", 1);                outside.addItem(key);        courtyard.addItem(potion);        netlab.addItem(potion);        buildingB.addItem(potion);                // spawn monsters        Monster drChun = new Monster("Dr. Chun",100, 1, 3);        Monster noob = new Monster("Clubroom noob",50, 5, 7);                office.addMonster(drChun);        clubroom.addMonster(noob);                currentRoom = outside;  // start game outside    }        /**     *  Main play routine.  Loops until end of play.     */    public void play() throws Exception    {                    printWelcome();        // Enter the main command loop.  Here we repeatedly read commands and        // execute them until the game is over.                        boolean finished = false;        while (! finished)        {            Command command = parser.getCommand();            finished = processCommand(command);        }                    //System.out.println("Thank you for playing.  Good bye.");    }        /**     * Print out the opening message for the player.     */    private void printWelcome() throws Exception    {        System.out.print("Who are you: ");        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));        String playerName = reader.readLine();        //Create new player with playerName        currentPlayer = new Player(playerName);                System.out.println();        System.out.println("Welcome to the College of Business " + currentPlayer.getName() + "!");        System.out.println("Please feel free to look around and talk to the professors.");        System.out.println("Type 'help' if you need help.");        System.out.println();        System.out.println(currentRoom.longDescription());        if(currentRoom.getItem() != null)            System.out.println(currentRoom.getItem().getDescription());        if(currentRoom.getMonster() != null)            System.out.println(currentRoom.getMonster().getName());    }        /**     * Given a command, process (that is: execute) the command.     * If this command ends the game, true is returned, otherwise false is     * returned.     */    private boolean processCommand(Command command)     {        System.out.print("\n");                if(command.isUnknown())        {            if(command.getCommandWord() == null)                System.out.println("Please enter a command.");            else                System.out.println("I don't know what " + command.getCommandWord() + " means...");            return false;        }        String commandWord = command.getCommandWord();        if (commandWord.equals("help"))            printHelp(command);        else if (commandWord.equals("go"))            goRoom(command);        else if (commandWord.equals("get"))            getItem(command);        else if (commandWord.equals("drop"))            dropItem(command);        else if (commandWord.equals("look"))            lookRoom();        else if (commandWord.equals("inv"))            showInventory();        else if (commandWord.equals("examine"))            examineItem(command);        else if (commandWord.equals("stat"))            showStats();        else if (commandWord.equals("fight")){            boolean dead = fightMonster();            if(dead) return true;        }        else if (commandWord.equals("drink"))            drinkPotion(command);        else if (commandWord.equals("quit") || commandWord.equals("q"))        {            if(command.hasSecondWord())                System.out.println("Quit what?");            else                return true;  // signal that we want to quit        }        return false;    }    //implementations of user commands:-----------------------------------------    /**     * Print out some help information.     * Here we print some stupid, cryptic message and a list of the      * command words.     */    private void printHelp(Command command)     {        if(!command.hasSecondWord()){            System.out.println("You are lost. You are alone. You wander.");            System.out.println("Type help [command word] for an explanation of each command.");            System.out.println();            System.out.println("Your command words are:");            parser.showCommands();        }        else {            if (command.getSecondWord().equals("help"))                System.out.println("Displays list of command words");            else if (command.getSecondWord().equals("go"))                System.out.println("Moves in the direction specified. Ex: go north");            else if (command.getSecondWord().equals("get"))                System.out.println("Picks up items. Ex: get key");            else if (command.getSecondWord().equals("drop"))                System.out.println("Drops an item from your inventory. Room must be " +                    "empty where you \nare attempting to drop something. Ex: drop key");            else if (command.getSecondWord().equals("look"))                System.out.println("Looks at the current room.");            else if (command.getSecondWord().equals("inv"))                System.out.println("Displays your inventory. Your inventory is " +                    "limited to 15 items.");            else if (command.getSecondWord().equals("examine"))                System.out.println("Examines an item in your inventory. Ex: examine key");            else if (command.getSecondWord().equals("stat"))                System.out.println("Displays your current status.");            else if (command.getSecondWord().equals("fight"))                System.out.println("Fights any monster in the current room.");            else if (command.getSecondWord().equals("drink"))                System.out.println("Drinks a potion from your inventory. Fully restores "+                    "HP. Ex: drink potion");            else if (command.getSecondWord().equals("quit"))                System.out.println("Quits the current game. You will be asked if " +                    "you want to start a new game.");            else                System.out.println("There is no help for that command");        }    }    /**      * Try to go to one direction. If there is an exit, enter the new     * room, otherwise print an error message.     */    private void goRoom(Command command)     {        if(!command.hasSecondWord())        {            // if there is no second word, we don't know where to go...            System.out.println("Go where?");            return;        }        String direction = command.getSecondWord();        // Try to leave current room.        Room nextRoom = currentRoom.nextRoom(direction);        if (nextRoom == null)            System.out.println("There is no door!");        else         {            currentRoom = nextRoom;            System.out.println(currentRoom.longDescription());            if(currentRoom.getItem() != null)                System.out.println(currentRoom.getItem().getDescription());            if(currentRoom.getMonster() != null)                System.out.println(currentRoom.getMonster().getName());        }    }            private void getItem(Command command) {        if(!command.hasSecondWord())        {            // if there is no second word, we don't know what to get...            System.out.println("Get what?");            return;        }                if(currentRoom.getItem() == null || !command.getSecondWord().equalsIgnoreCase(currentRoom.getItem().getName()))            System.out.println("No " + command.getSecondWord() + " here");        else {            if(currentPlayer.itemIn(currentRoom.getItem())) {                System.out.println("You picked up a " + currentRoom.getItem().getName());                currentRoom.deleteItem();            }            else                System.out.println("Your inventory is full");        }            }//END getItem            private void dropItem(Command command) {        ArrayList inventory = currentPlayer.getInventory();                if(!command.hasSecondWord())        {            // if there is no second word, we don't know where to go...            System.out.println("Drop what?");            return;        }        int itemIndex = currentPlayer.searchInventory(command.getSecondWord());                if(itemIndex == -1)            System.out.println("You don't have a " + command.getSecondWord() );                else if(itemIndex == -2)            System.out.println("Your inventory is empty");                else {            if(currentRoom.getItem() == null) {                        currentRoom.addItem((Item)inventory.get(itemIndex));                        System.out.println("You dropped a " + ((Item)inventory.get(itemIndex)).getName());                        inventory.remove(itemIndex);             }             else                System.out.println("This room is full");        }               //         droppedItem = currentPlayer.itemOut(command.getSecondWord());        //         if(droppedItem == null)//             System.out.println("You don't have a " + command.getSecondWord() );//         else//             if(currentRoom.getItem() != null){//                 System.out.println("This room is full");//                 currentPlayer.itemIn(droppedItem);//             }//             else{//                 System.out.println("You dropped a " + droppedItem.getName());//                 currentRoom.addItem(droppedItem);//             }                }//END dropItem            private void lookRoom() {        System.out.println(currentRoom.longDescription());                if(currentRoom.getItem() != null)            System.out.println(currentRoom.getItem().getDescription());                    if(currentRoom.getMonster() != null)            System.out.println(currentRoom.getMonster().getName());    }//END lookRoom            private void showInventory() {        ArrayList inventory = currentPlayer.getInventory();        if (inventory.isEmpty())            System.out.println("Your inventory is empty");        else {            for(int i=0; i < inventory.size(); i++){                System.out.print((i+1) + ". ");                 System.out.println(((Item)inventory.get(i)).getName());            }        }                System.out.println("Total weight: " + currentPlayer.getTotWeight());    }//END showInventory            private void examineItem(Command command){        boolean success = false;        ArrayList inventory;                if(!command.hasSecondWord())        {            // if there is no second word, we don't know what to examine...            System.out.println("Examine what?");            return;        }        inventory = currentPlayer.getInventory();                int indexOfWord = inventory.indexOf(command.getSecondWord());                    if(indexOfWord == -1)            System.out.println("You don't have a " + command.getSecondWord() );        else {            System.out.println(((Item)inventory.get(indexOfWord)).getDescription());        }            }//END examineItem        private void showStats(){        System.out.println("Stats for " + currentPlayer.getName());        System.out.println("HP: " + currentPlayer.getHitpoints());        System.out.println("Level: " + currentPlayer.getLevel());        System.out.println("Max weight: " + currentPlayer.getMaxWeight());    }//END showStats            private boolean fightMonster(){        boolean dead = false;                if(currentRoom.getMonster() == null)            System.out.println("No monsters here");        else{            int monsterAttack = currentRoom.getMonster().getMinDmg() +                 generator.nextInt(currentRoom.getMonster().getMaxDmg() + 1 -                 currentRoom.getMonster().getMinDmg());            int playerAttack = currentPlayer.getMinDmg() +                 generator.nextInt(currentPlayer.getMaxDmg() + 1 - currentPlayer.getMinDmg());                        currentRoom.getMonster().addHitpoints(-playerAttack);            System.out.println("You hit " + currentRoom.getMonster().getName() +                                 " causing " + playerAttack + " damage!");            //IF MONSTER HP DROPS BELOW 0 THEN IT DOES            if(currentRoom.getMonster().getHP() <= 0){                System.out.println("You killed " + currentRoom.getMonster().getName() + "!!");                currentRoom.deleteMonster();            }            else{                currentPlayer.addHitpoints(-monsterAttack);                System.out.println(currentRoom.getMonster().getName() + " hit you " +                                    " causing " + monsterAttack + " damage!");                //IF PLAYER HP DROPS BELOW 0 THEN GAME ENDS                if(currentPlayer.getHitpoints() <= 0){                    System.out.println("You died!!");                    dead = true;                }            }        }                return dead;    }//END fightMonster            private void drinkPotion(Command command){        if(!command.hasSecondWord())        {            // if there is no second word, we don't know what to drink...            System.out.println("Drink what?");            return;        }        ArrayList inventory = currentPlayer.getInventory();        int itemIndex = currentPlayer.searchInventory(command.getSecondWord());                if(itemIndex > -1){            currentPlayer.addHitpoints(currentPlayer.getMaxHitpoints() - currentPlayer.getHitpoints());            inventory.remove(itemIndex);            System.out.println("You drank a " + command.getSecondWord());        }        else            System.out.println("You don't have a " + command.getSecondWord() + " to drink");                //         Item drunkPotion = currentPlayer.itemOut(command.getSecondWord());        //         if(drunkPotion == null)//             System.out.println("You don't have a " + command.getSecondWord() + " to drink");//         else{//             System.out.println("You drank a " + drunkPotion.getName());//             currentPlayer.addHitpoints(currentPlayer.getMaxHitpoints() - currentPlayer.getHitpoints());//         }    }//END drinkPotion}