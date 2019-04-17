package strategy;

import game.Card;
import org.json.simple.JSONObject;

import java.util.List;

public class DiscardStrategy extends Stragegy {

    public DiscardStrategy() {}

    public JSONObject action(List<Card> hand) {
        Card sacrificed = hand.get(0);
        JSONObject res = new JSONObject();
        res.put("command", "DISCARD");
        res.put("card", sacrificed.getName());
        System.out.println(sacrificed.getName() + " a ete defausse");
        hand.remove(0);


        return res;
    }
}
