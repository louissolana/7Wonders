package game;

import java.util.List;
import java.util.Map;

public class Player {
    private Board playerBoard;
    private List<Card> hand;
    private List<Card> oldHand;
    private Map<Resources, Integer> resources;
    private int gold;
    private int victory;
    private int battle;
    private Player playerLeft;
    private Player playerRight;
    private int id;

    public int getId() {
        return id;
    }

    public Player(int id, Board playerBoard, List<Card> hand, List<Card> oldHand, Map<Resources, Integer> res, int gold, int victory, int battle, Player playerLeft, Player playerRight) {
        this.id = id;
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

    public List<Card> getHand() {
        return hand;
    }

    public List<Card> getOldHand() { return oldHand;}

    public Map<Resources, Integer> getResources() {
        return resources;
    }

    public void changeAmountGold(int val) {
        if(gold + val >= 0) {
            gold += val;
        } else {
            gold = 0;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PLAYER INFO :\n");
        sb.append(playerBoard.toString());
        sb.append("Gold: " + gold + "\n");
        sb.append("Victory points: " + victory + "\n");
        sb.append("Battle points: " + battle + "\n");
        if(resources != null && !resources.isEmpty()) {
            sb.append("\nRESOURCES: \n");
            for (Map.Entry<Resources, Integer> map: resources.entrySet()
            ) {
                sb.append(map.getKey() + ": " + map.getValue() + "\n");
            }
        }
        if(hand != null && !hand.isEmpty()) {
            sb.append("\nHAND: \n");
            for (Card c: hand
            ) {
                sb.append(c.toString());
            }
        }


        return sb.toString();
    }
}
