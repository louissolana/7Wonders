package game;

import java.util.List;

public class Board {
    private String name;
    private Resources boardResource;
    private List<Card> cardsPlayed;

    public Board(String name, Resources ownResource, List<Card> played) {
        this.name = name;
        this.boardResource = ownResource;
        this.cardsPlayed = played;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Wonder: " + this.name);
        sb.append("Own resource: " + this.boardResource);
        sb.append("Cards played: ");
        for (Card c: this.cardsPlayed
             ) {
            sb.append(c.toString());
        }
        return sb.toString();
    }
}
