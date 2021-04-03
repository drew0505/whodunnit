// CLASS: Model
//
// Author: Andrew Jonathan, 7875981
//
// REMARKS: Manages the game
//
//-----------------------------------------
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
public class Model{
    //Fields
    private ArrayList<Card> deck;
    private ArrayList<IPlayer> playerList;
    private ArrayList<Card> answerCard;
    private int numPlayers;
    private Scanner in;
    /**
     * Constructor
     */
    public Model(Scanner input, int totalPlayer){
        deck = new ArrayList<Card>();
        playerList = new ArrayList<IPlayer>();
        answerCard = new ArrayList<Card>();
        numPlayers = totalPlayer;
        in = input;
    }
    /**
     * add all type of Card to the Card list
     * @param type
     * @param value
     */
    public void addCard(String type, String[] value){
        int length = value.length;

        for(int i = 0; i < length; i++){
            deck.add(new Card(type, value[i]));
        }
    }
    /**
     * Adding player to the player list
     */
    public void addPlayer(){
        System.out.println("Setting up players...");

        ArrayList<Integer> randomIndex = new ArrayList<Integer>();

        //Creating arbitrary number for index player
        for(int i = 0; i < numPlayers; i++){
            randomIndex.add(i);
        }
        Collections.shuffle(randomIndex); //Shuffle the index of the player

        playerList.add(new HumanPlayer(in)); //Adding human player
        //Setup the human player
        playerList.get(0).setUp(numPlayers, randomIndex.get(0), getCardList(Type.SUSPECT.name()), getCardList(Type.LOCATION.name()), getCardList(Type.WEAPON.name()));
        for(int i = 1; i < numPlayers; i++){
            playerList.add(new ComputerPlayer());  //Adding computer player
            //Setup the computer player
            playerList.get(i).setUp(numPlayers, randomIndex.get(i), getCardList(Type.SUSPECT.name()), getCardList(Type.LOCATION.name()), getCardList(Type.WEAPON.name()));
        }
    }

    public ArrayList<Card> getDeck(){
        return deck;
    }
    /**
     * Get the list of card
     * @param type
     * @return list of cards that are selected
     */
    private ArrayList<Card> getCardList(String type){
        ArrayList<Card> retArray = null;

        //Search in the card list and copy to the new array
        if(type.equals(Type.WEAPON.name()) || type.equals(Type.LOCATION.name()) || type.equals(Type.SUSPECT.name() )){
            retArray = new ArrayList<Card>();
            for(int i = 0; i < deck.size(); i++){
                if(deck.get(i).getType().equals(type)){
                    retArray.add(deck.get(i));
                }
            }   
            //Check if not found
            if(retArray.isEmpty())
                retArray = null;
        }
        return retArray;
    }

    /**
     * Remove an element from cardList
     * @param card that is going to be removed
     */
    private void removeElement(Card card){
        for(int i = 0; i < deck.size(); i++){
            if(deck.get(i).equals(card)){
                deck.remove(i);
            }
        }
    }
    /**
     * Arrange the player's seat
     */
    private void setupPlayer(){
        IPlayer temp;
        for(int i = 0; i < numPlayers; i++){
            for(int j = 0; j < numPlayers; j++){
                if(playerList.get(i).getIndex() < playerList.get(j).getIndex()){
                    temp = playerList.get(i);
                    playerList.set(i, playerList.get(j));
                    playerList.set(j, temp);
                }
            }
        }
    }
    /**
     * Pick the answer and deal cards to the player
     */
    private void setupCard(){
        //Pick the answerCard
        Collections.shuffle(deck); //Shuffle the cardList
        //Pick the WEAPON answer
        answerCard.add(getCardList(Type.WEAPON.name()).get(0)); //Add to the answerCard
        removeElement(getCardList(Type.WEAPON.name()).get(0)); //Remove from cardList
        //Pick the LOCATION answer
        answerCard.add(getCardList(Type.LOCATION.name()).get(0)); //Add to the answerCard
        removeElement(getCardList(Type.LOCATION.name()).get(0)); //Remove from cardList
        //Pick the PERPETRATOR answer
        answerCard.add(getCardList(Type.SUSPECT.name()).get(0)); //Add to the answerCard
        removeElement(getCardList(Type.SUSPECT.name()).get(0)); //Remove from cardList

        //Distribute the remaining cards to the players
        int countPlayer = 0;
        int countCard = 0;
        while(!deck.isEmpty() && countCard < deck.size()){
            if(countPlayer == numPlayers){
                countPlayer = 0;
            }
            playerList.get(countPlayer).setCard(deck.get(countCard));
            countPlayer++;
            countCard++;
        }
        deck.clear();
    }
    /**
     * Start the game process
     */
    public void start(){
        setupPlayer();
        setupCard();
        System.out.println("Playing...");

        boolean over = false;
        int playerSeat = 0;
        IPlayer curr = playerList.get(playerSeat);
        IPlayer winner = null;
        Guess playerGuess = null;
        ArrayList<IPlayer> losers = new ArrayList<IPlayer>();
        
        while(!over){
            if(playerSeat == numPlayers){
                playerSeat = 0;
            }
            playerGuess = curr.getGuess();
            if(playerGuess.getGuess() == true){
                ArrayList<Card> guess = new ArrayList<Card>();
                guess.add(playerGuess.getSuspect());
                guess.add(playerGuess.getLocation());
                guess.add(playerGuess.getWeapon());
                if(answerCard.containsAll(guess)){
                    over = true;
                    System.out.println("Player " + curr.getIndex() + " won the game.");
                    winner = curr;
                }
                else{
                    System.out.println("Player " + curr.getIndex() + " made a bad accusation and was removed from the game.");
                    losers.add(curr);
                    playerList.remove(curr);
                    numPlayers--;
                    if(playerList.size() == 1){
                        System.out.println("Player " + playerList.get(0).getIndex() + " won the game.");
                        over = true;
                    }
                }
            }
            else{
                int next = (playerSeat+1)%numPlayers;
                boolean answered = false;
                Card otherAnswer;
                while(next != playerSeat && !answered){
                    if(next == numPlayers)
                        next = 0;
                    System.out.println("Asking player " + playerList.get(next).getIndex() +".");
                    otherAnswer = playerList.get(next).canAnswer(playerGuess, curr);
                    if(otherAnswer != null){
                        curr.receiveInfo(playerList.get(next), otherAnswer);
                        answered = true;
                        System.out.println("Player " + playerList.get(next).getIndex() + " answered.");
                    }
                    else{
                        if(next == curr.getIndex()){
                            otherAnswer = losers.get(0).canAnswer(playerGuess, curr);
                            if(otherAnswer != null){
                                curr.receiveInfo(playerList.get(next), otherAnswer);
                                answered = true;
                                System.out.println("Player " + playerList.get(next).getIndex() + " answered.");
                            }
                            break;
                        }
                        next++;
                    }
                }
                if(answered == false){
                    curr.receiveInfo(null, null);
                }
            }
            if(!over){
                curr = playerList.get((playerSeat+1)%numPlayers);
                playerSeat++;
            }
        }//while
        Card ansSus = null;
        Card ansLoc = null;
        Card ansWeap = null;
        for(int i = 0; i < answerCard.size(); i++){
            if(answerCard.get(i).getType().equals(Type.SUSPECT.name())){
                ansSus = answerCard.get(i);
            }
            else if(answerCard.get(i).getType().equals(Type.SUSPECT.name())){
                ansLoc = answerCard.get(i);
            }
            else if(answerCard.get(i).getType().equals(Type.SUSPECT.name())){
                ansWeap = answerCard.get(i);
            }
        }
        for(int i = 0; i < answerCard.size(); i++){
            if(answerCard.get(i).getType().equals(Type.SUSPECT.name())){
                ansSus = answerCard.get(i);
            }
            else if(answerCard.get(i).getType().equals(Type.LOCATION.name())){
                ansLoc = answerCard.get(i);
            }
            else if(answerCard.get(i).getType().equals(Type.WEAPON.name())){
                ansWeap = answerCard.get(i);
            }
        }
        if(winner != null){
            System.out.println("Player " + winner.getIndex() + " guessed correctly " + ansSus.getValue() + " in " + ansLoc.getValue() + " with " + ansWeap.getValue());
        }
        else{
            System.out.println("The answer is " + ansSus.getValue() + " in " + ansLoc.getValue() + " with " + ansWeap.getValue());
        }
    }
}