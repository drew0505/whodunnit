// CLASS: Player
//
// Author: Andrew Jonathan, 7875981
//
// REMARKS: Manage the behaviour of Player
//
//-----------------------------------------
import java.util.ArrayList;
abstract class Player{
    //Constant
    protected final String HAND_LIST = "inHand";
    protected final String S_LIST = "suspectCards";
    protected final String L_LIST = "locationCards";
    protected final String W_LIST = "weaponCards";
    protected final String A_LIST = "allCards";
    //Fields
    private ArrayList<Card> inHand;
    private ArrayList<Card> suspectCards;
    private ArrayList<Card> locationCards;
    private ArrayList<Card> weaponCards;
    private ArrayList<Card> cardList;
    private int numPlayers;
    private int index;
    private Guess theGuess;

    public Player(){
        inHand = new ArrayList<Card>();
        cardList = new ArrayList<Card>();
        theGuess = null;
    }

    /**
     * Print list of Card
     * @param type of the Card
     */
    protected void printCard(ArrayList<Card> type){
        System.out.print("[");
        for(int i = 0; i < type.size(); i++){
            System.out.print(type.get(i).getValue());
            if((i+1) != type.size()){
                System.out.print(", ");
            }
        }
        System.out.print("]");
        System.out.println("\n");
    }//printCard

    //Getters
    protected ArrayList<Card> getList(String listType){
        ArrayList<Card> toReturn = null;
        if(listType.equals(HAND_LIST)){
            toReturn = inHand;
        }
        else if(listType.equals(S_LIST)){
            toReturn = suspectCards;
        }
        else if(listType.equals(L_LIST)){
            toReturn = locationCards;
        }
        else if(listType.equals(W_LIST)){
            toReturn = weaponCards;
        }
        else if(listType.equals(A_LIST)){
            toReturn = cardList;
        }
        return toReturn;
    }

    protected int index(){
        return index;
    }

    protected Guess theGuess(){
        return theGuess;
    }

    //Setters
    protected void setUp(int numPlayers, int index, ArrayList<Card> ppl,
    ArrayList<Card> places, ArrayList<Card> weapons){
        this.numPlayers = numPlayers;
        this.index = index;
        suspectCards = ppl;
        locationCards = places;
        weaponCards = weapons;
        cardList.addAll(suspectCards);
        cardList.addAll(locationCards);
        cardList.addAll(weaponCards);
    }

    protected void setTheGuess(Guess newGuess){
        theGuess = newGuess;
    }
}