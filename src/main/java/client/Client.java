package client;

import game.Card;
import game.Cost;
import game.Player;
import game.Resources;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import strategy.Stragegy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.Random;

public class Client {
    private int id;
    private Player player;
    private Socket socket;
    private String urlServ;
    private int portServ;
    private Resources recupRes;
    private Stragegy stragegy;

    final Object stopObject = new Object();

    public Client(final int id, final Player player, Stragegy str, String url, int port) {
        this.id = id;
        this.player = player;
        this.stragegy = str;
        this.urlServ = url;
        this.portServ = port;
        this.recupRes = null;

        try {
            socket = IO.socket(urlServ + ":" + port);

            // action pour se connecter
            /**
             * Le client se connecte, il envoie son indentifiant au serveur
             * Le serveur possède un Map(id, socketclient) et crée ou se met à jour quand des clients communiquent
             * Tous les échanges clients -> serveurs doivent commencer par l'id du client
             *
             * action: "connectAndWait", message: {"id":"x"}
             */
            socket.on("connect", new Emitter.Listener() {
                public void call(Object... objects) {
                    JSONObject toSend = new JSONObject();
                    toSend.put("id", getId());
                    socket.emit("connectAndWait", toSend.toString());
                }
            });

            // on reçoit la main de depart + le plateau de jeu, a traiter donc
            // on recupère un JSONObject de la forme:
            /**
                {"boardName":"XXX", "own":"stone", "hand":[...]}
                focus sur "hand":[...] :
                    {"cardName":"YYY", "age":"1", "type":"science", "cost": []} <-- cas cout nul
                    "cost": [{"res":"gold_1"},{"res":"wood_2"}] par exemple
             **/
            socket.on("initial", new Emitter.Listener() {
                public void call(Object... objects) {
                    //si on est le client concerne on traite l'evenement
                    if((Integer) objects[0] == id) {
                        System.out.println("[CLIENT"+id+"]received on initial: " + objects[1]);
                        getPlayer().setHand(Card.JsonToCard((String)objects[1]));
                        //displayHand(); check que la main est bien traitee

                        //on joue la 1re carte
                        JSONObject toSend = stragegy.action(player.getHand(), player.getCardsPlayed());
                        toSend.put("id", getId());
                        System.out.println("[CLIENT"+id+"] message envoye: " + toSend.toString());
                        socket.emit("card", toSend.toString());
                    }
                }
            });

            // action du tour de jeu, deja fait
            /**
             * Action à effectuer pendant un tour: défausser(DISCARD), jouer(PLAY), construire(BUILD)
             * action: "card", message: {"id":"x", "command":"DISCARD", "card":"nom_de_la_carte"}
             */
            socket.on("play", new Emitter.Listener() {
                public void call(Object... objects) {
                    JSONObject toSend = stragegy.action(player.getHand(), player.getCardsPlayed());
                    toSend.put("id", getId());
                    System.out.println("[CLIENT"+id+"] message envoye: " + toSend.toString());
                    socket.emit("card", toSend.toString());
                }
            });

            // resultat de l'action effectuee, a traiter
            /**
             * Le serveur répond à l'action, il faut la traiter en parsant le json obtenu
             * {"action":"DISCARDED", "resource":"x"}
             * On envoie ensuite la main au serveur
             * action: "hand", message: {"id":"x", "hand":[{}{}...{}]}  <-- liste des cartes en main
             */
            socket.on("answer", new Emitter.Listener() {
                public void call(Object... objects) {
                    String val = (String)objects[0];
                    if(Integer.parseInt(val) == id) {

                        System.out.println("objects[1]: " + objects[1]);
                        JSONParser parser = new JSONParser();
                        try {
                            JSONObject infos = (JSONObject) parser.parse((String) objects[1]);

                            String res = (String) infos.get("effect");
                            String[] matiere = res.split("_");
                            getPlayer().addResources(Resources.parseStringResource(matiere[0]), Integer.parseInt(matiere[1]));
                            //on jsonifie la main actuelle pour ensuite l'envoyer au serveur
                            JSONArray array = new JSONArray();
                            for(Card c : getPlayer().getHand()) {array.add(c.CardToJson());}
                            JSONObject toSend = new JSONObject();
                            toSend.put("id", getId());
                            toSend.put("hand", array);
                            socket.emit("give_hand", toSend.toString());
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            });

            /**
             * On reçoit la nouvelle main
             * {"action":"NEW_HAND", "hand":[{}{}...{}]} <--- JSONArray contenant x JSONObject
             */
            socket.on("next_turn", new Emitter.Listener() {
                public void call(Object... objects) {
                    if((Integer)objects[0] == id) {

                        if(player.getHand().size() > 1)
                        {
                            System.out.println("[CLIENT" + id + "] New hand received: " + objects[1]);
                            JSONObject toSend = stragegy.action(player.getHand(), player.getCardsPlayed());
                            toSend.put("id", getId());
                            System.out.println("[CLIENT"+id+"] message envoye: " + toSend.toString());
                            socket.emit("card", toSend.toString());
                        }
                        else
                        {
                            // Discard la dernière carte non utilisée puis remplir les listes avec les cartes de l'âge 2.
                            System.out.println("[CLIENT" + id + "] "+ player.getHand().get(0).getName() + " a ete defausse");
                            player.getHand().remove(0);
                            System.out.println("Fin de l'âge !");
                            // Modifier en conséquence :
                            /*
                            JSONObject toSend = stragegy.action(player.getHand(), player.getCardsPlayed());
                            socket.emit("new_age", toSend.toString());
                            */

                        }
                    }

                    //TODO comme pour initial, on parse la main pour instancier la nouvelle main puis l'affecter au Player comme étant la nouvelle main
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
        Random rand = new Random();
        int nbAlea = rand.nextInt(player.getHand().size());
        Card sacrificed = player.getHand().get(nbAlea);
        JSONObject res = new JSONObject();
        res.put("command", "DISCARD");
        res.put("card", sacrificed.getName());
        System.out.println(sacrificed.getName() + " a ete defausse");
        player.getHand().remove(sacrificed);

        for(Card c: player.getHand())
        {
            player.getOldHand().add(c);
        }

        //System.out.println(player.getOldHand().size());
        //System.out.println(player.getHand().size());

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

        for(Card c: player.getHand())
        {
            player.getOldHand().add(c);
        }

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

        for(Card c: player.getHand())
        {
            player.getOldHand().add(c);
        }

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
        System.out.println("Numero du Joueur : " + player.getId());
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

    public int getId() {
        return id;
    }

    public Player getPlayer() {
        return this.player;
    }
}
