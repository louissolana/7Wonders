package game;

import java.util.List;

public class Player {
    private Board playerBoard;
    private List<Card> hand;
    private List<Card> oldHand;
    private short gold;
    private short victory;
    private short battle;
    private Player playerLeft;
    private Player playerRight;

    public Player(Board playerBoard, List<Card> hand, List<Card> oldHand, short gold, short victory, short battle, Player playerLeft, Player playerRight) {
        this.playerBoard = playerBoard;
        this.hand = hand;
        this.oldHand = oldHand;
        this.gold = gold;
        this.victory = victory;
        this.battle = battle;
        this.playerLeft = playerLeft;
        this.playerRight = playerRight;
    }
}
