// CLASS: Guess
//
// Author: Andrew Jonathan, 7875981
//
// REMARKS: What is the purpose of this class? 
//
//-----------------------------------------
public class Guess{
    //Fields
    /**
     * TRUE -> Accusation
     * FALSE -> Suggestion
     */
    private boolean guessType;
    private Card suspect;
    private Card location;
    private Card weapon;
    //Constructor
    public Guess(boolean guessType, Card ppl, Card loc, Card weap){
        suspect = ppl;
        location = loc;
        weapon = weap;
        this.guessType = guessType;
    }

    public boolean getGuess(){
        return guessType;
    }

    public Card getSuspect(){
        return suspect;
    }
    public Card getLocation(){
        return location;
    }
    public Card getWeapon(){
        return weapon;
    }

    public String toString(){
        return getSuspect().getValue() + " in " + getLocation().getValue()
                + " with the " + getWeapon().getValue();
    }
}