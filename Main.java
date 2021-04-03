//-----------------------------------------
// NAME		: Andrew Jonathan
// STUDENT NUMBER	: 7875981
// COURSE		: COMP 2150
// INSTRUCTOR	: Ali Neshati
// ASSIGNMENT	: assignment 3  
// 
// REMARKS: "whodunit" game
//
//
//-----------------------------------------
import java.util.Scanner;
public class Main{
    //Fields
    private static Model game;
    private static Scanner in;

    public static void main(String[]args){
        in = new Scanner(System.in);
        int numPlayers = Integer.MIN_VALUE;

        //Welcome and ask how many BOT to play
        System.out.println("Welcome to \"WHODUNIT?\"");
        System.out.println("How many computer opponents would you like?");

        numPlayers = in.nextInt() + 1;
        game = new Model(in, numPlayers);

        String[] weapon = {"Knife", "Xbox", "Billiard's Stick", "Alcohol Bottle", "Pan", 
                        "Guitar", "Pencil", "Scissors", "Shovel", "Adit's Trashcan"};
        String[] location = {"Kitchen", "Bathroom", "Basement", "Game Room", "Living Room", 
                        "Adit's Room", "Renee's Room", "Andrew's Room", "Playground", "Garage"};
        String[] suspect = {"Andrew", "Renee", "Raymond", "Justin", "Adit", "JoJo", "Juan", "Jeff", "Richard", "Naufal"};

        game.addCard(Type.WEAPON.name(), weapon);
        game.addCard(Type.LOCATION.name() , location);
        game.addCard(Type.SUSPECT.name(), suspect);

        game.addPlayer();

        game.start();
        in.close();
    }
}