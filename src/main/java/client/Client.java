package client;

import game.Card;
import game.Player;

import java.util.Scanner;

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

    /**
     * Action to discard a card and gain 3 gold
     * @return Json string containing the command DISCARD and the Card sacrificed
     * eg: send ["command":"DISCARD","card":"card_name"]
     *     receive ["command":"DISCARDED","gold":3]
     */
    public String discard() {
        return "";
    }

    /**
     * Action to build a stage of the wonder
     * @return Json string containing the command BUILD, the Card sacrificed and the number of the stage
     */
    public String build() {

        return "";
    }

    /**
     * Action to play a Card and trigger it's effect
     * @return Json String containing the command PLAY and the Card played
     */
    public String playCard() {

        return "";
    }

    /**
     * Allows to choose a card through user input, later decided by bot
     * @return Card
     */
    public Card chooseCard() {
        for (int i = 0; i < player.getHand().size(); ++i) {
            System.out.println(i + ": " + player.getHand().get(i).toString());
        }
        System.out.println("Enter number of picked card");
        Scanner entry = new Scanner(System.in);
        String line = entry.nextLine();
        return null;
    }

    public void printHand(){
        for(Card c: player.getHand()){
            System.out.println(c.toString());
        }
    }
}
