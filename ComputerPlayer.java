// CLASS: ComputerPlayer
//
// Author: Andrew Jonathan, 7875981
//
// REMARKS: What is the purpose of this class? 
//
//-----------------------------------------
import java.util.ArrayList;
import java.util.Collections;
public class ComputerPlayer extends Player implements IPlayer{
    ArrayList<Card> infoCards; //List of info that other player's inHand Cards
    /**
     * Constructor
     */
    public ComputerPlayer(){
        infoCards = new ArrayList<Card>();
    }
    /**
     * Set up the player
     */
    public void setUp( int numPlayers, int index, ArrayList<Card> ppl,
    ArrayList<Card> places, ArrayList<Card> weapons){
        super.setUp(numPlayers, index, ppl, places, weapons);
    }

    public void setCard (Card c){
        super.getList(HAND_LIST).add(c);
    }

    public int getIndex(){
        return super.index();
    }

    public Card canAnswer(Guess g, IPlayer ip){
        Card toShow = null;
        ArrayList<Card> inHand = super.getList(HAND_LIST);
        ArrayList<Card> found = new ArrayList<Card>();
        ArrayList<Integer> randomInt = new ArrayList<Integer>();

        if(!inHand.isEmpty()){
            //Searching the guess in the hand card
            if( inHand.contains(g.getSuspect()) )
                found.add(g.getSuspect());
            if( inHand.contains(g.getLocation()) )
                found.add(g.getLocation());
            if( inHand.contains(g.getWeapon()) )
                found.add(g.getWeapon());
    
            if(found.size() == 1){
                toShow = found.get(0);
            }
            else if(found.size() > 1){
                for(int i = 0; i < found.size(); i++){
                    randomInt.add(i);
                }
                Collections.shuffle(randomInt);
                toShow = found.get(randomInt.get(0));
            }
        }
        return toShow;
    }

    /**
     * Get the list of card that are not in player's hand
     */
    private ArrayList<Card> notInHand(){
        ArrayList<Card> list = super.getList(A_LIST); //cardList
        ArrayList<Card> outHand = new ArrayList<Card>();

        for(int i = 0; i < list.size(); i++){
            if(!super.getList(HAND_LIST).contains(list.get(i))){
                outHand.add(list.get(i));
            }
        }
        if(outHand.isEmpty())
            outHand = null;
        return outHand;
    }

    /**
     * Player guessing the answer
     * @return Guess
     */
    public Guess getGuess(){
        Card susTemp = null;
        Card locTemp = null;
        Card weapTemp = null;
        ArrayList<Card> outHand = notInHand();

        if(outHand == null)
            return null;
        //With infoCards, AI will not guess the card that other player showed
        for(int i = 0; i < infoCards.size(); i++){
            for(int j = 0; j < outHand.size(); j++){
                if(infoCards.get(i).getValue().equals(outHand.get(j).getValue())){
                    outHand.remove(outHand.get(j));
                }
            }
        }
        int n = outHand.size();

        if(n > 2){
            Collections.shuffle(outHand); //Shuffle the outHand card to get random guess
            for(int i = 0; i < outHand.size(); i++){
                if(outHand.get(i).getType().equals(Type.SUSPECT.name())){
                    susTemp = outHand.get(i);
                }
                else if(outHand.get(i).getType().equals(Type.LOCATION.name())){
                    locTemp = outHand.get(i);
                }
                else if(outHand.get(i).getType().equals(Type.WEAPON.name())){
                    weapTemp = outHand.get(i);
                }
            }
            if(n == 3){
                super.setTheGuess(new Guess(true, susTemp, locTemp, weapTemp));
            }
            else if(n == 4){
                for(int i = 0; i < infoCards.size(); i++){
                    if(outHand.contains(infoCards.get(i))){
                        if(infoCards.get(i).getType().equals(Type.SUSPECT.name())){
                            susTemp = outHand.get(i);
                        }
                        else if(infoCards.get(i).getType().equals(Type.LOCATION.name())){
                            locTemp = outHand.get(i);
                        }
                        else if(infoCards.get(i).getType().equals(Type.WEAPON.name())){
                            weapTemp = outHand.get(i);
                        }
                        super.setTheGuess(new Guess(true, susTemp, locTemp, weapTemp));
                    }
                    else{
                        super.setTheGuess(new Guess(false, susTemp, locTemp, weapTemp));
                    }
                }
            }
            else{
                super.setTheGuess(new Guess(false, susTemp, locTemp, weapTemp));
            }
        }
        System.out.println("Player " + super.index() + ": Suggestion: " + super.theGuess());
        return super.theGuess();
    }//getGuess
    
    /**
     * Receiving info from other Player
     * @param ip -> the player
     * @param c -> the card
     */
    public void receiveInfo(IPlayer ip, Card c){
        if(ip != null && c != null){
            if(!infoCards.contains(c)){
                infoCards.add(c);
            }
        }
        else{
            System.out.println("No one could answer.");
        }
    }//receiveInfo
}