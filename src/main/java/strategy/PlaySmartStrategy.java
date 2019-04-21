package strategy;

import game.Board;
import game.Card;
import game.Type;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class PlaySmartStrategy extends Stragegy {
    private Type color1;
    private Type color2;

    public PlaySmartStrategy(Type c1, Type c2) {
        color1 = c1;
        color2 = c2;
    }

    public JSONObject action(List<Card> hand)
    {
        JSONObject res = new JSONObject();
        List<Card> color1Cards = new ArrayList<Card>();
        List<Card> color2Cards = new ArrayList<Card>();
        Boolean color1Card = false;
        Boolean color2Card = false;

        for(Card oneCard : hand)
        {
            Type type = oneCard.getType();
            if(type == color1)
            {
                color1Card = true;
                color1Cards.add(oneCard);
            }
            else if(type == color2)
            {
                color2Card = true;
                color2Cards.add(oneCard);
            }
        }

        if(color1Card)
        {
            Random rand = new Random();
            int nbAlea = rand.nextInt(color1Cards.size());
            Card cardPlayed = color1Cards.get(nbAlea);
            res.put("command", "PLAY");
            res.put("card", cardPlayed.getName());
            System.out.println(cardPlayed.getName() + " a ete joue");
            hand.remove(cardPlayed);

        }
        else if(!color1Card && color2Card)
        {
            Random rand = new Random();
            int nbAlea = rand.nextInt(color2Cards.size());
            Card cardPlayed = color2Cards.get(nbAlea);
            res.put("command", "PLAY");
            res.put("card", cardPlayed.getName());
            System.out.println(cardPlayed.getName() + " a ete joue");
            hand.remove(cardPlayed);
        }
        else{
            Random rand = new Random();
            int nbAlea = rand.nextInt(hand.size());
            Card sacrificed = hand.get(nbAlea);
            res.put("command", "DISCARD");
            res.put("card", sacrificed.getName());
            System.out.println(sacrificed.getName() + " a ete defausse");
            hand.remove(sacrificed);
        }
        return res;

    }
}
