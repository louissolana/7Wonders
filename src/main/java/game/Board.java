package game;

import java.util.List;

public class Board {
    private String name;
    private Resources boardResource;

    public Board(String name, Resources ownResource) {
        this.name = name;
        this.boardResource = ownResource;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(); 
        sb.append("Wonder: " + this.name + "\n");
        sb.append("Own resource: " + this.boardResource + "\n");
        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public Resources getBoardResource() {
        return boardResource;
    }
}
