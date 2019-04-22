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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.*;

public class Server {
    private SocketIOServer server;
    final Object waitObject = new Object();
    private Generator cards;
    final Map<Long, SocketIOClient> connections = new HashMap<Long, SocketIOClient>();
    private int counter;

    public Server(Configuration config){
        counter = 0;
        cards = new Generator(4);
        server = new SocketIOServer(config);

        server.addConnectListener(new ConnectListener() {
            public void onConnect(SocketIOClient socketIOClient) {
                System.out.println("[SERVER]Receive call from " + socketIOClient.getRemoteAddress());
            }
        });

        server.addEventListener("connectAndWait", String.class, new DataListener<String>() {
            public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {
                System.out.println("[SERVER]Client arrived: " + s);
                JSONParser parser = new JSONParser();
                try {
                    JSONObject object = (JSONObject)parser.parse(s);
                    Long id = (Long)object.get("id");
                    updateMap(id, socketIOClient);
                    List<JSONArray> hands = generateHands(4);
                    socketIOClient.sendEvent("initial", id, hands.get(counter).toString());
                    counter++;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        server.addEventListener("card", String.class, new DataListener<String>() {
            public void onData(SocketIOClient socketIOClient, String string, AckRequest ackRequest) throws Exception {
                Long retrievedId = extractId(string);
                updateMap(retrievedId, socketIOClient);
                JSONObject answer = new JSONObject();
                answer.put("result","DISCARD");
                answer.put("effect", "gold_3");
                socketIOClient.sendEvent("answer", ""+retrievedId, answer.toString());

                synchronized (waitObject) {
                    waitObject.notify();
                }
            }
        });

        // on reçoit la main de chaque joueur
        server.addEventListener("give_hand", String.class, new DataListener<String>() {
            public void onData(SocketIOClient socketIOClient, String string, AckRequest ackRequest) throws Exception {
                JSONParser parser = new JSONParser();
                JSONObject object = (JSONObject)parser.parse(string);
                Long id = (Long)object.get("id");
                updateMap(id, socketIOClient);
                System.out.println("[SERVER]Received hand by client " + id + ", hand: " + string);
                // on transfert au voisin: (id + 1)%4  <-- on distribue au voisin de gauche, (id - 1)%4 <-- on distribuera au voisin de droite
                // d'ou l'interet de garder a jour le map (id, socketclient)
                JSONArray handToSend = (JSONArray)object.get("hand");
                System.out.println("[SERVER]id, next id: " + id + ", " + (id+1)%4);
                connections.get((id+1)%4).sendEvent("next_turn", (id+1)%4, handToSend.toString());

            }
        });
        server.addEventListener("new_age", String.class, new DataListener<String>() {
            public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {

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
                tmp.add(cardList.get(j + i*7).CardToJson());
            }
            res.add(tmp);
        }
        return res;
    }

    private void treatment(SocketIOClient soc) {

        JSONObject answer = new JSONObject();
        answer.put("result","DISCARD");
        answer.put("effect", "gold_3");
        soc.sendEvent("answer", answer.toString());
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

    private void updateMap(Long id, SocketIOClient client) {
        connections.put(id, client);
    }

    private Long extractId(String json) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject obj = (JSONObject)parser.parse(json);
            return (Long) obj.get("id");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (long)0;
    }
}
