package strategy;

import game.Card;
import game.Type;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ScientistMilitaryStrategy extends Stragegy
{
    public ScientistMilitaryStrategy() {}

    public JSONObject action(List<Card> hand, List<Card> cardPlayed){

        JSONObject res = new JSONObject();
        List<Card> scientistCards = new ArrayList<Card>();
        List<Card> militaryCards = new ArrayList<Card>();
        Boolean scientist = false;
        Boolean military = false;

        for(Card oneCard : hand)
        {
            String type = oneCard.getType().toString();
            String name = oneCard.getName();
            if(oneCard.getType().equals(Type.SCIENCE))
            {
                scientist = true;
                scientistCards.add(oneCard);

                for(Card cardPlay : cardPlayed)
                {
                    if(name == cardPlay.getName())
                    {
                        scientistCards.remove(oneCard);
                    }
                }
            }
            else if(type == "MILITARY")
            {
                military = true;
                militaryCards.add(oneCard);

                for(Card cardPlay : cardPlayed)
                {
                    if(name == cardPlay.getName())
                    {
                        militaryCards.remove(oneCard);
                    }
                }
            }
        }

        if(scientist)
        {
            Random rand = new Random();
            int nbAlea = rand.nextInt(scientistCards.size());
            Card chosenCard = scientistCards.get(nbAlea);
            res.put("command", "PLAY");
            res.put("card", chosenCard.getName());
            System.out.println(chosenCard.getName() + " a ete joue");
            hand.remove(cardPlayed);
            cardPlayed.add((chosenCard));

        }
        else if(!scientist && military)
        {
            Random rand = new Random();
            int nbAlea = rand.nextInt(militaryCards.size());
            Card chosenCard = militaryCards.get(nbAlea);
            res.put("command", "PLAY");
            res.put("card", chosenCard.getName());
            System.out.println(chosenCard.getName() + " a ete joue");
            hand.remove(cardPlayed);
            cardPlayed.add((chosenCard));
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