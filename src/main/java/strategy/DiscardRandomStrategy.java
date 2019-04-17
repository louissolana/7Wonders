package strategy;

import game.Card;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Random;

public class DiscardRandomStrategy extends Stragegy {

    public DiscardRandomStrategy() {}

    public JSONObject action(List<Card> hand) {
        Random rand = new Random();
        int nbAlea = rand.nextInt(hand.size());
        Card sacrificed = hand.get(nbAlea);
        JSONObject res = new JSONObject();
        res.put("command", "DISCARD");
        res.put("card", sacrificed.getName());
        System.out.println(sacrificed.getName() + " a ete defausse");
        hand.remove(sacrificed);

        return res;
    }
}
