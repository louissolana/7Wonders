import client.Client;
import com.corundumstudio.socketio.Configuration;
import game.*;
import server.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Cost> map1 = new ArrayList<Cost>();
        map1.add(new Cost(Resources.GOLD, 1));

        List<Cost> map2 = new ArrayList<Cost>();
        map2.add(new Cost(Resources.WOOD, 2));
        Card c1 = new Card("Mine", Type.RESOURCE, map1, 1);
        Card c2 = new Card("March�", Type.MARKET, null, 1);
        Card c3 = new Card("Tisserie", Type.CRAFT, map2, 1);

        Board b1 = new Board("Pharos", Resources.STONE, null);

        List<Card> list = new ArrayList<Card>();
        list.add(c1);
        list.add(c2);
        list.add(c3);

        Map<Resources, Integer> resourcesContainer = new HashMap<Resources, Integer>();
        resourcesContainer.put(Resources.STONE, 1);
        resourcesContainer.put(Resources.SCIENCE, 1);
        Player p1 = new Player(b1, list, null, resourcesContainer, 3, 0 , 0, null, null);

        System.out.println(p1.toString());

        //client serveur gogogo
        final Configuration conf = new Configuration();
        conf.setHostname("127.0.0.1");
        conf.setPort(8080);

        Runnable r = new Runnable() {
            public void run()
            {
                Server server = new Server(conf);
                server.startServer();
            }
        };

        new Thread(r).start();

        Client client = new Client(1, p1, "http://127.0.0.1", 8080);
        client.connect();
    }
}
