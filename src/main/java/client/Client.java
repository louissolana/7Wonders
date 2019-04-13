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

    final Object stopObject = new Object();

    public Client(final int id, Player player, String url, int port) {
        this.id = id;
        this.player = player;
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
                    // utlisation de setters + mouliner les donnees recues -> s'inspirer de Generator
                    if((Integer) objects[0] == id) {
                        System.out.println("[CLIENT"+id+"]received on initial: " + objects[1]);
                        getPlayer().setHand(Card.JsonToCard((String)objects[1]));
                        displayHand();
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
                    JSONObject toSend = action();
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
            //TODO: traiter la réponse -> ajouter la ressource au set de ressources du joueur OU augmenter de 1 la ressource si elle est déjà présente
            socket.on("answer", new Emitter.Listener() {
                public void call(Object... objects) {
                    System.out.println("objects[0]: " + objects[0]);
                    JSONParser parser = new JSONParser();
                    recupRes = null;
                    try {
                        JSONObject infos = (JSONObject) parser.parse(new FileReader((String)objects[0]));

                        String res = (String)infos.get("effect");
                        String[] matiere = res.split("_");
                        Resources ownRes = Resources.GOLD;
                        if (matiere[0].equals("clay")) ownRes = Resources.CLAY;
                        if (matiere[0].equals("stone")) ownRes = Resources.STONE;
                        if (matiere[0].equals("ore")) ownRes = Resources.ORE;
                        if (matiere[0].equals("wood")) ownRes = Resources.WOOD;
                        if (matiere[0].equals("cloth")) ownRes = Resources.CLOTH;
                        if (matiere[0].equals("science")) ownRes = Resources.SCIENCE;
                        if (matiere[0].equals("military")) ownRes = Resources.MILITARY;
                        if (matiere[0].equals("compass")) ownRes = Resources.COMPASS;
                        if (matiere[0].equals("gear")) ownRes = Resources.GEAR;
                        if (matiere[0].equals("paper")) ownRes = Resources.PAPER;
                        if (matiere[0].equals("tablet")) ownRes = Resources.TABLET;
                        recupRes = ownRes; // Add aux ressources du player
                        getPlayer().addResources(ownRes, Integer.parseInt(matiere[1]));
                        } catch (ParseException e1) {
                        e1.printStackTrace();
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                });

                /**
                 * On reçoit la nouvelle main
                 * {"action":"NEW_HAND", "hand":[{}{}...{}]} <--- JSONArray contenant x JSONObject
                 */
                socket.on("next_turn", new Emitter.Listener() {
                    public void call(Object... objects) {

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
