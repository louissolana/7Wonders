package strategy;

import game.Card;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ScientistMilitaryStrategy extends Stragegy
{
    public ScientistMilitaryStrategy() {}

    public JSONObject action(List<Card> hand) {

        JSONObject res = new JSONObject();
        List<Card> scientistCards = new ArrayList<Card>();
        List<Card> militaryCards = new ArrayList<Card>();
        Boolean scientist = false;
        Boolean military = false;

        for(Card oneCard : hand)
        {
            String type = oneCard.getType().toString();
            if(type == "SCIENCE")
            {
                scientist = true;
                scientistCards.add(oneCard);
            }
            else if(type == "MILITARY")
            {
                military = true;
                militaryCards.add(oneCard);
            }
        }

        if(scientist)
        {
            Random rand = new Random();
            int nbAlea = rand.nextInt(scientistCards.size());
            Card cardPlayed = scientistCards.get(nbAlea);
            res.put("command", "PLAY");
            res.put("card", cardPlayed.getName());
            System.out.println(cardPlayed.getName() + " a ete joue");
            hand.remove(cardPlayed);

        }
        else if(!scientist && military)
        {
            Random rand = new Random();
            int nbAlea = rand.nextInt(militaryCards.size());
            Card cardPlayed = militaryCards.get(nbAlea);
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