import java.io.*;

public class RunGame
{
    public static void main(String[] args) throws Exception
    {
        String playAgain = "n";

        do{
    
            Game zork = new Game();
    
            zork.play();
            
            do{
                System.out.print("Would you like to play again (y or n): ");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                playAgain = reader.readLine();
                if(!(playAgain.equalsIgnoreCase("y") || playAgain.equalsIgnoreCase("n"))){
                    System.out.println("Please enter y or n");
                }
            }while(!(playAgain.equalsIgnoreCase("y") || playAgain.equalsIgnoreCase("n"))); 
        }while(playAgain.equalsIgnoreCase("y"));            

        System.out.println("Thank you for playing.  Good bye.");
    }
}
