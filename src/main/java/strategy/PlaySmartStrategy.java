package strategy;

import game.Card;
import game.Type;
import org.json.simple.JSONObject;

import java.util.List;

public class PlaySmartStrategy extends Stragegy {
    private Type color1;
    private Type color2;

    public PlaySmartStrategy(Type c1, Type c2) {
        color1 = c1;
        color2 = c2;
    }

    public JSONObject action(List<Card> hand) {
        return null;
    }
}
