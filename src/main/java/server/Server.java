package server;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import game.Board;
import game.Card;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    private SocketIOServer server;
    final Object waitObject = new Object();
    public Server(Configuration config){
        server = new SocketIOServer(config);

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

    private void treatment(SocketIOClient soc) {

       /*
        HashMap<String,String> FirstOne = new HashMap<String, String>();
        HashMap<String,String> SecondOne = new HashMap<String, String>();

        answer.add(FirstOne);
        answer.add(SecondOne);

        FirstOne.put("result", "DISCARD");
        SecondOne.put("gold", "3");
        */

        Generator cards = new Generator(4);

        List<Card> listeCards = new ArrayList<Card>();
        listeCards = cards.generateCards();

        List<Board> listeBoards = new ArrayList<Board>();
        listeBoards = cards.generateBoards();



        soc.sendEvent("answer", cards);
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
