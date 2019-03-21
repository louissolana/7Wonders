package client;

import com.fasterxml.jackson.annotation.JsonAlias;
import game.Card;
import game.Cost;
import game.Player;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Scanner;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Client {
    private int id;
    private Player player;
    private Socket socket;
    private String urlServ;
    private int portServ;

    final Object stopObject = new Object();




    public Client(int id, Player player, String url, int port) {
        this.id = id;
        this.player = player;
        this.urlServ = url;
        this.portServ = port;

        try {
            socket = IO.socket(urlServ + ":" + port);

            socket.on("connect", new Emitter.Listener() {
                public void call(Object... objects) {
                    JSONObject toSend = action();
                    socket.emit("card", toSend);
                }
            });

            socket.on("disconnect", new Emitter.Listener() {
                public void call(Object... objects) {
                    System.out.println("[CLIENT]Disconnect");
                    socket.disconnect();
                    synchronized (stopObject) {
                        stopObject.notify();
                    }
                }
            });

            socket.on("answer", new Emitter.Listener() {
                public void call(Object... objects) {
                    JSONArray test = (JSONArray) objects[0];
                    System.out.println("[CLIENT]Answer: " + test.toString());
                    setAmountGold(3);
                }
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        socket.connect();
        synchronized (stopObject) {
            try {
                stopObject.wait();
            } catch (InterruptedException ie) {
                System.out.println("[CLIENT]Problem here");
            }
        }
    }

    private void setAmountGold(int val) {
        player.changeAmountGold(val);
    }

    public static boolean isNullOrEmpty(String str) {
        if (str != null && !str.equals(""))
            return false;
        return true;
    }

    public JSONObject action() {
        //JSONObject command = new JSONObject();
        //display hand
        displayHand();
        //get input action
        System.out.println("Choose action: 1 Discard, 2 Build, 3 Play");
        /*Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();

        if (isNullOrEmpty(input)) {
            action();
        } else {
            switch (input.charAt(0)) {
                case '1':
                    command = discard();
                    socket.connect();

                    break;
                case '2':
                    command = build();
                    socket.connect();
                    break;

                case '3':
                    command = playCard();
                    socket.connect();
                    break;

                default:
                    break;
            }
            //
            return command;
        }*/
        return discard();
    }


    /**
     * Action to discard a card and gain 3 gold
     * @return Json string containing the command DISCARD and the Card sacrificed
     * eg: send ["command":"DISCARD","card":"card_name"]
     *     receive ["command":"DISCARDED","gold":3]
     */
    public JSONObject discard() {
        //Card sacrificed = chooseCard(1);
        Card sacrificed = player.getHand().get(0);
        JSONObject res = new JSONObject();
        res.put("command", "DISCARD");
        res.put("card", sacrificed.getName());
        System.out.println(sacrificed.getName() + " a été défaussée");
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
        System.out.println("[DEBUG] enter playcard");
        Card played = chooseCard(3);
        JSONObject res = new JSONObject();
        res.put("command", "PLAY");
        res.put("card", played.getName());
        System.out.println(res);
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
        System.out.println(line);
        if (Integer.parseInt(line) >= 0 && Integer.parseInt(line) <= player.getHand().size()) {

            int pos = Integer.parseInt(line);
            if (act == 1) {
                return player.getHand().get(pos);
            } else if(act == 2) {
                //no check atm to build
                return player.getHand().get(pos);
            } else if(act == 3){
                //check if possible
                if (player.getHand().get(pos).getCost() == null) {
                    System.out.println(player.getHand().get(pos).toString());
                    return player.getHand().get(pos);
                } else {
                    boolean valid = false;
                    Card tmp = player.getHand().get(pos);
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
        System.out.println("Numéro du Joueur : " + player.getId());
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
