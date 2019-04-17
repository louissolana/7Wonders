package strategy;

import game.Card;
import org.json.simple.JSONObject;

import java.util.List;

public abstract class Stragegy {
    public abstract JSONObject action(List<Card> hand);
}
