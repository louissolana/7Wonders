package client;

import game.Card;
import game.Cost;
import game.Player;
import org.json.JSONObject;

import java.util.Scanner;

public class Client {
    private int id;
    private Player player;


    public Client(int id, Player player) {
        this.id = id;
        this.player = player;
    }

    public JSONObject action() {
        JSONObject command = new JSONObject();
        //display hand
        displayHand();
        //get input action
        System.out.println("Choose action: 1 Discard, 2 Build, 3 Play");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();

        switch (input.charAt(0)) {
            case 1:
                command = discard();
                break;
            case 2:
                command = build();
                break;
            case 3:
                command = playCard();
                break;
            default:
                break;
        }
        //
        return command;
    }

    /**
     * Action to discard a card and gain 3 gold
     * @return Json string containing the command DISCARD and the Card sacrificed
     * eg: send ["command":"DISCARD","card":"card_name"]
     *     receive ["command":"DISCARDED","gold":3]
     */
    public JSONObject discard() {
        Card sacrificed = chooseCard(1);
        JSONObject res = new JSONObject();
        res.put("command", "DISCARD");
        res.put("card", sacrificed.getName());

        return res;
    }

    /**
     * Action to build a stage of the wonder
     * @return Json string containing the command BUILD, the Card sacrificed and the number of the stage
     */
    public JSONObject build() {
        Card sacrToBuild = chooseCard(2);
        JSONObject res = new JSONObject();
        res.put("command", "BUILD");
        res.put("card", sacrToBuild.getName());

        return res;
    }

    /**
     * Action to play a Card and trigger it's effect
     * @return Json String containing the command PLAY and the Card played
     */
    public JSONObject playCard() {
        Card played = chooseCard(3);
        JSONObject res = new JSONObject();
        res.put("command", "PLAY");
        res.put("card", played.getName());

        return res;
    }

    /**
     * Allows to choose a card through user input, later decided by bot
     * @return Card
     */
    public Card chooseCard(int act) {
        for (int i = 0; i < player.getHand().size(); ++i) {
            System.out.println(i + ": " + player.getHand().get(i).toString());
        }
        System.out.println("Enter number of picked card");
        Scanner entry = new Scanner(System.in);
        String line = entry.nextLine();
        if (line.charAt(0) >= 1 && line.charAt(0) <= player.getHand().size()) {
            if (act == 1) {
                return player.getHand().get(line.charAt(0));
            } else if(act == 2) {
                //no check atm to build
                return player.getHand().get(line.charAt(0));
            } else {
                //check if possible
                if (player.getHand().get(line.charAt(0)).getCost() == null) {
                    return player.getHand().get(line.charAt(0));
                } else {
                    boolean valid = false;
                    Card tmp = player.getHand().get(line.charAt(0));
                    for (Cost co: tmp.getCost()
                         ) {
                        valid = player.getResources().containsKey(co.getRes()) && player.getResources().containsValue(co.getRes());
                    }
                    if (valid) return tmp;
                }
            }
        } else {
            return chooseCard(act);
        }
        return null;
    }

    public void displayHand() {
        for (Card c: player.getHand()
        ) {
            System.out.println(c.toString());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Client id: " + id);
        sb.append(player.toString());

        return sb.toString();
    }
}
