package game;

import java.util.List;
import java.util.Map;

public class Player {
    private Board playerBoard;
    private List<Card> hand;
    private List<Card> oldHand;
    private Map<Resources, Integer> resources;
    private short gold;
    private short victory;
    private short battle;
    private Player playerLeft;
    private Player playerRight;

    public Player(Board playerBoard, List<Card> hand, List<Card> oldHand, Map<Resources, Integer> res, short gold, short victory, short battle, Player playerLeft, Player playerRight) {
        this.playerBoard = playerBoard;
        this.hand = hand;
        this.oldHand = oldHand;
        this.resources = res;
        this.gold = gold;
        this.victory = victory;
        this.battle = battle;
        this.playerLeft = playerLeft;
        this.playerRight = playerRight;
    }

    public Card playCard() {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Board: " + playerBoard.toString());
        sb.append("Gold: " + gold);
        sb.append("Victory points: " + victory);
        sb.append("Battle points: " + battle);
        for (Map.Entry<Resources, Integer> map: resources.entrySet()
             ) {
            sb.append(map.getKey() + ": " + map.getValue());
        }
        for (Card c: hand
             ) {
            sb.append(c.toString());
        }

        return sb.toString();
    }
}
