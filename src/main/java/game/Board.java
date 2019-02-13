package game;

public class Board {
    private String name;
    private Resources boardResource;

    public Board(String name, Resources ownResource) {
        this.name = name;
        this.boardResource = ownResource;
    }
}
