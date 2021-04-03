import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Scanner;
public class Testing{
    IPlayer bot1;
    IPlayer bot2;
    Model game;
    Scanner in;
    ArrayList<Card> answer;

    private ArrayList<Card> getCardList(String type){
        ArrayList<Card> retArray = null;

        //Search in the card list and copy to the new array
        if(type.equals(Type.WEAPON.name()) || type.equals(Type.LOCATION.name()) || type.equals(Type.SUSPECT.name() )){
            retArray = new ArrayList<Card>();
            for(int i = 0; i < game.getDeck().size(); i++){
                if(game.getDeck().get(i).getType().equals(type)){
                    retArray.add(game.getDeck().get(i));
                }
            }   
            //Check if not found
            if(retArray.isEmpty())
                retArray = null;
        }
        return retArray;
    }
    @BeforeEach
    public void setup(){
        game = new Model(in, 1);
        answer = new ArrayList<Card>();
        String[] weapon = {"Rusty Pan", "Knife", "Pencil", "PS5", "Lamp"};
        String[] suspect = {"Andrew", "Raymond", "Renee", "Adit", "Juan"};
        String[] location = {"Playground", "Basement", "Living Room", "Parking Lot", "Kitchen"};

        game.addCard(Type.WEAPON.name(), weapon);
        game.addCard(Type.LOCATION.name(), location);
        game.addCard(Type.SUSPECT.name(), suspect);
        game.addPlayer();
        bot1 = new ComputerPlayer();
        bot1.setUp(2, 0, getCardList(Type.SUSPECT.name()), getCardList(Type.LOCATION.name()), getCardList(Type.WEAPON.name()));
        bot2 = new ComputerPlayer();
        bot2.setUp(2, 1, getCardList(Type.SUSPECT.name()), getCardList(Type.LOCATION.name()), getCardList(Type.WEAPON.name()));
    }
    @Test
    public void test1(){
        Guess g = new Guess(false, game.getDeck().get(0), game.getDeck().get(4), game.getDeck().get(8));
        assertNull(bot1.canAnswer(g, bot2));
    }
    @Test
    public void test2(){
        Guess g = new Guess(false, game.getDeck().get(0), game.getDeck().get(4), game.getDeck().get(8));
        bot1.setCard(game.getDeck().get(4));
        assertEquals(game.getDeck().get(4), bot1.canAnswer(g, bot2));
    }
    @Test
    public void test3(){
        Guess g = new Guess(false, game.getDeck().get(0), game.getDeck().get(4), game.getDeck().get(8));
        ArrayList<Card> list = new ArrayList<Card>();
        list.add(game.getDeck().get(0));
        list.add(game.getDeck().get(4));
        list.add(game.getDeck().get(8));
        bot1.setCard(game.getDeck().get(0));
        bot1.setCard(game.getDeck().get(4));
        bot1.setCard(game.getDeck().get(8));

        assertTrue(list.contains(bot1.canAnswer(g, bot2)));
    }
}