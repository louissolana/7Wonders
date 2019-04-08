import client.Client;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
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
        int nbJoueur = 4;
        List<Cost> map2 = new ArrayList<Cost>();
        map2.add(new Cost(Resources.WOOD, 2));

        Card c1 = new Card("Mine", Type.RESOURCE, map1, 1);
        Card c2 = new Card("March�", Type.MARKET, null, 1);
        Card c3 = new Card("Tisserie", Type.CRAFT, map2, 1);

        Board b1 = new Board("Pharos", Resources.STONE, null);
        Board b2 = new Board("Atlantis", Resources.STONE, null);
        Board b3 = new Board("Uruk", Resources.STONE, null);
        Board b4 = new Board("Venezia", Resources.STONE, null);


        List<Card> list = new ArrayList<Card>();
        list.add(c1);
        list.add(c2);
        list.add(c3);

        List<Card> list2 = new ArrayList<Card>();
        list2.add(c1);
        list2.add(c2);
        list2.add(c3);

        List<Card> list3 = new ArrayList<Card>();
        list3.add(c1);
        list3.add(c2);
        list3.add(c3);

        List<Card> list4 = new ArrayList<Card>();
        list4.add(c1);
        list4.add(c2);
        list4.add(c3);
        
        final List<Player> listPlayer = new ArrayList<Player>();

        Map<Resources, Integer> resourcesContainer = new HashMap<Resources, Integer>();
        resourcesContainer.put(Resources.STONE, 1);
        resourcesContainer.put(Resources.SCIENCE, 1);
        
        //Check Nb joueur
        switch(nbJoueur) {
        case 1: try {
				Player p1 = new Player(1, b1, list, null, resourcesContainer, 3, 0 , 0, null, null);
				listPlayer.add(p1);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
        break;
        case 2: try {
				Player p1 = new Player(1, b1, list, null, resourcesContainer, 2, 0 , 1, null, null);
				Player p2 = new Player(2, b2, list2, null, resourcesContainer, 3, 0 , 0, null, null);
				listPlayer.add(p1);
				listPlayer.add(p2);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
        break;
        case 3: try {
				Player p1 = new Player(1,b1, list, null, resourcesContainer, 1, 2 , 1, null, null);
				Player p2 = new Player(2,b2, list2, null, resourcesContainer, 3, 0 , 0, null, null);
				Player p3 = new Player(3,b3, list3, null, resourcesContainer, 3, 2 , 1, null, null);
				listPlayer.add(p1);
				listPlayer.add(p2);
				listPlayer.add(p3);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
        break;
        case 4: 
			try {
				Player p1 = new Player(1,b1, list, null, resourcesContainer, 1, 2 , 1, null, null);
				Player p2 = new Player(2,b2, list2, null, resourcesContainer, 3, 0 , 0, null, null);
				Player p3 = new Player(3,b3, list3, null, resourcesContainer, 3, 2 , 1, null, null);
				Player p4 = new Player(4,b4, list4, null, resourcesContainer, 2, 1 , 3, null, null);
				listPlayer.add(p1);
				listPlayer.add(p2);
				listPlayer.add(p3);
				listPlayer.add(p4);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
        break;
        }
        
        for(int i=0;i<listPlayer.size();i++) {
        //System.out.println(listPlayer.get(i).toString());
        }

        //client serveur gogogo
        final Configuration conf = new Configuration();
        conf.setHostname("127.0.0.1");
        conf.setPort(8080);
        conf.setUpgradeTimeout(10000000);
        conf.setPingTimeout(10000000);
        conf.setPingInterval(10000000);
        SocketConfig s = conf.getSocketConfig();
        s.setReuseAddress(true);

        Runnable r = new Runnable() {
            public void run()
            {
                Server server = new Server(conf);
                server.startServer();
            }
        };

        new Thread(r).start();

        for(int i = 0;i<listPlayer.size();i++)
        {        	
        	final int ind = i;        
        	Runnable ru = new Runnable() {
        		public void run() {
        			Client client = new Client(ind+1, listPlayer.get(ind), "http://127.0.0.1", 8080);
        	        client.connect();
        		}
        	};
        	new Thread(ru).start();
        }
    }
}
