// CLASS: HumanPlayer
//
// Author: Andrew Jonathan, 7875981
//
// REMARKS: Interactive Player
//
//-----------------------------------------
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
public class HumanPlayer extends Player implements IPlayer{
    //Fields
    private Scanner in;
    //Constructor
    public HumanPlayer(Scanner input){
        in = input;
    }
    /**
     * Set up the player
     */
    public void setUp( int numPlayers, int index, ArrayList<Card> ppl,
    ArrayList<Card> places, ArrayList<Card> weapons){
        super.setUp(numPlayers, index, ppl, places, weapons);

        System.out.println("Here are the names of all suspect:");
        printCard(super.getList(S_LIST));

        System.out.println("Here are all the locations:");
        printCard(super.getList(L_LIST));

        System.out.println("Here are all the weapons:");
        printCard(super.getList(W_LIST));
    }
    /**
     * Set Player's Card
     * @param c -> the card
     */
    public void setCard (Card c){
        System.out.println("You receive the card " + c.getValue());
        super.getList(HAND_LIST).add(c);
    }//setCard

    /**
     * Print ArrayList in list of options
     * @param type of the list that we want to print
     */
    private void printOptions(ArrayList<Card> type){
        for(int i = 0; i < type.size(); i++){
            System.out.println(i + ": " + type.get(i).getValue());
        }
    }//printOptions

    /**
     * Get the index of the player
     */
    public int getIndex(){
        return super.index();
    }

    /**
     * This Player can answer the guess from other player
     * @param g
     * @param ip
     * @return Card
     */
    public Card canAnswer(Guess g, IPlayer ip){
        Card toShow = null;
        ArrayList<Card> inHand = super.getList(HAND_LIST);
        ArrayList<Card> found = new ArrayList<Card>();
        //Searching the guess in the hand card
        if( inHand.contains(g.getSuspect()) )
            found.add(g.getSuspect());
        if( inHand.contains(g.getLocation()) )
            found.add(g.getLocation());
        if( inHand.contains(g.getWeapon()) )
            found.add(g.getWeapon());

        if(found.isEmpty()){
            System.out.println("Player " + ip.getIndex() + " asked you about " + g + ", but you couldn't answer.");
        }
        else if(found.size() == 1){
            toShow = found.get(0);
            System.out.println("Player " + ip.getIndex() + " asked you about " + g + ", you only have one card, " + 
                                toShow.getValue() + ", showed it to them.");
        }
        else if(found.size() > 1){
            boolean passed = false;
            int inputInt = Integer.MIN_VALUE;
            do{
                passed = false;
                try{
                    System.out.println("Player "+ ip.getIndex() + " asked you about "+ g + ". Which do you show?");
                    printOptions(found);
                    inputInt = in.nextInt();
                    if(inputInt < found.size()){
                        passed = true;
                    }
                    else{
                        passed = false;
                    }
                }
                catch(InputMismatchException e){
                    passed = false;
                    in.nextLine(); //Clear out the buffer
                }
            }while(!passed);
            toShow = found.get(inputInt);
        }
        return toShow;
    }//canAnswer

    /**
     * Player guessing the answer
     * @return Guess
     */
    public Guess getGuess(){
        boolean passed = false; //To check the input is correct
        boolean guessTemp = false; //Accusation or Suggestion
        Card susTemp = null;
        Card locTemp = null;
        Card weapTemp = null;
        int inputInt = Integer.MIN_VALUE;
        String inputStr = "";

        System.out.println("It is your turn");
        //Choosing the perpetrator
        do{
            passed = false; //Set back the checker
            try{
                System.out.println("Which person do you want to suggest?");
                printOptions(super.getList(S_LIST));
                System.out.println("Please enter a number 0 - " + (super.getList(S_LIST).size()-1) );
                inputInt = in.nextInt();
                if(inputInt < super.getList(S_LIST).size()){
                    passed = true;
                }
                else{
                    passed = false;
                }
            }
            catch(InputMismatchException e){
                passed = false;
                in.nextLine(); //Clear out the buffer
            }
        }while(!passed);
        susTemp = super.getList(S_LIST).get(inputInt); //Get the Suspect Card
        //Choosing the location
        do{
            passed = false; //Set back the checker
            try{
                System.out.println("Which location do you want to suggest?");
                printOptions(super.getList(L_LIST));
                System.out.println("Please enter a number 0 - " + (super.getList(L_LIST).size()-1) );
                inputInt = in.nextInt();
                if(inputInt < super.getList(L_LIST).size()){
                    passed = true;
                }
                else{
                    passed = false;
                }
            }
            catch(InputMismatchException e){
                passed = false;
                in.nextLine(); //Clear out the buffer
            }
        }while(!passed);
        locTemp = super.getList(L_LIST).get(inputInt); //Get the Location Card
        //Choosing the weapon
        do{
            passed = false; //Set back the checker
            try{
                System.out.println("Which weapon do you want to suggest?");
                printOptions(super.getList(W_LIST));
                System.out.println("Please enter a number 0 - " + (super.getList(W_LIST).size()-1) );
                inputInt = in.nextInt();
                if(inputInt < super.getList(W_LIST).size()){
                    passed = true;
                }
                else{
                    passed = false;
                }
            }
            catch(InputMismatchException e){
                passed = false;
                in.nextLine();
            }
        }while(!passed);
        weapTemp = super.getList(W_LIST).get(inputInt); //Get the Weapon Card
        in.nextLine(); //Clear out the buffer
        //Choosing either Accusation or Suggestion
        do{
            passed = false; //Set back the checker
            System.out.println("Is this an accusation (Y/[N])?");
            inputStr = in.nextLine();
            //Accusation Choice
            if(inputStr.equals("y") || inputStr.equals("Y") || inputStr.equals("yes")){
                System.out.println("YES");
                guessTemp = true;
                passed = true;
            }
            //Suggestion choice
            else if(inputStr.equals("n") || inputStr.equals("N") || inputStr.equals("no") || inputStr.equals("")){
                System.out.println("NO");
                guessTemp = false;
                passed = true;
            }
            else{
                passed = false;
            }
        }while(!passed);
        super.setTheGuess(new Guess(guessTemp, susTemp, locTemp, weapTemp)); //Fix the guess
        System.out.println("Player " + super.index() + ": Suggestion: " + super.theGuess());
        return super.theGuess();
    }//getGuess

    /**
     * Receiving info from other Player
     * @param ip -> the player
     * @param c -> the card
     */
    public void receiveInfo(IPlayer ip, Card c){
        if(c == null){
            System.out.println("No one could refute your suggestion.");
        }
        else{
            System.out.println("Player "+ ip.getIndex() + " refuted your suggestion by showing you "+ c.getValue() +".");
        }
    }//receiveInfo
}