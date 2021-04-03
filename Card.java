// CLASS: Card
//
// Author: Andrew Jonathan, 7875981
//
// REMARKS: What is the purpose of this class? 
//
//-----------------------------------------
public class Card{
    //Fields
    private String type;
    private String value;

    //Constructor
    public Card(String newType, String newValue){
        type = newType;
        value = newValue;
    }

    public String getType(){
        return type;
    }

    public String getValue(){
        return value;
    }

    public String toString(){
        return type + ": " + value;
    }
}