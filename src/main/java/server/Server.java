package server;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import game.Board;
import game.Card;
import org.json.simple.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class Server {
    private SocketIOServer server;
    final Object waitObject = new Object();
    private Generator cards;
    //TODO creer Map<id, socketclient> pour stocker et mettre a jour les infos clients

    public Server(Configuration config){

        cards = new Generator(4);
        server = new SocketIOServer(config);
        //List<JSONArray> hands = generateHands(4);

        //TODO: ajouter un compteur de clients pour savoir quand envoyer la sauce chef
        /**
         * Attendre les clients, puis distribuer a tous (via une boucle)
         * {"boardName":"XXX", "own":"stone", "hand":[...]}
         * focus sur "hand":[...] :
         *     {"cardName":"YYY", "age":"1", "type":"science", "cost": []} <-- cas cout nul
         *          cost": [{"res":"gold_1"},{"res":"wood_2"}] par exemple
         */
        server.addConnectListener(new ConnectListener() {
            public void onConnect(SocketIOClient socketIOClient) {
                System.out.println("[SERVER]Receive call from " + socketIOClient.getRemoteAddress());
                //TODO mettre a jour la Map + incrementer compteur, une fois le bon nombre de clients, envoyer
                // mains & plateaux a chacun des joueurs
                /*
                    soit foreach sur le map, soit for classique
                    sur chaque socclient: socClient.sendEvent("initial", board&Hand);
                 */
                //TODO on remet a 0 le compteur pour temporiser
            }
        });

        server.addEventListener("card", JSONObject.class, new DataListener<JSONObject>() {
            public void onData(SocketIOClient socketIOClient, JSONObject jsonObject, AckRequest ackRequest) throws Exception {
                treatment(socketIOClient);

                synchronized (waitObject) {
                    waitObject.notify();
                }
            }
        });

        // on reçoit la main de chaque joueur
        server.addEventListener("hand", org.json.simple.JSONObject.class, new DataListener<org.json.simple.JSONObject>() {
            public void onData(SocketIOClient socketIOClient, org.json.simple.JSONObject jsonObject, AckRequest ackRequest) throws Exception {

            }
        });
    }
    
    private List<JSONArray> generateHands(int players) {
        List<Card> cardList = cards.generateCards(); // generation des cartes
        Collections.shuffle(cardList); // on melange
        List<JSONArray> res = new ArrayList<JSONArray>();

        for(int i = 0; i < players; ++i) {
            JSONArray tmp = new JSONArray();
            for(int j = 0; j < 7; ++j) {
                tmp.add((org.json.simple.JSONObject)cardList.get(j + i*7).CardToJson());
            }
            res.add(tmp);
        }
        return res;
    }

    private void treatment(SocketIOClient soc) {
        List<Card> listeCards = new ArrayList<Card>();
        listeCards = cards.generateCards();


        List<Board> listeBoards = new ArrayList<Board>();
        listeBoards = cards.generateBoards();

        HashMap<String,String> FirstOne = new HashMap<String, String>();
        HashMap<String,String> SecondOne = new HashMap<String, String>();

        //answer.add(FirstOne);
        //answer.add(SecondOne);

        JSONObject answer = new JSONObject();
        answer.put("result","DISCARD");
        answer.put("effect", "gold_3");
        //FirstOne.put("result", "DISCARD");
        //SecondOne.put("gold", "3");
        soc.sendEvent("answer", answer);

        /*JSONArray cardJson = new JSONArray();

        for(Card card: listeCards)
        {
            cardJson.put(card.CardToJson());
        }


        soc.sendEvent("answer", cardJson);*/
    }

    public void startServer() {
        server.start();
        synchronized (waitObject) {
            try {
                waitObject.wait();
            } catch (InterruptedException ie) {
                System.out.println("[SERVER]Error while waiting");
            }
        }

        //server.stop();
    }
}
