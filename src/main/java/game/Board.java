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
        sb.append("Wonder: " + this.name + "\n");
        sb.append("Own resource: " + this.boardResource + "\n");
        if(cardsPlayed != null) {
            sb.append("Cards played: ");
            for (Card c: this.cardsPlayed
            ) {
                sb.append(c.toString() + "\n");
            }
        }
        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public Resources getBoardResource() {
        return boardResource;
    }

    public List<Card> getCardsPlayed() {
        return cardsPlayed;
    }
}
