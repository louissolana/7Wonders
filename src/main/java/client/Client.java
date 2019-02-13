package client;

import game.Player;

public class Client {
    private int id;
    private Player player;


    public Client(int id, Player player) {
        this.id = id;
        this.player = player;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Client id: " + id);
        sb.append(player.toString());

        return sb.toString();
    }
}
