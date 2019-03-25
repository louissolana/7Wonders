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

    public Server(Configuration config){

        cards = new Generator(4);
        server = new SocketIOServer(config);
        List<JSONArray> hands = generateHands(4);

        server.addConnectListener(new ConnectListener() {
            public void onConnect(SocketIOClient socketIOClient) {
                System.out.println("[SERVER]Receive call from " + socketIOClient.getRemoteAddress());
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
