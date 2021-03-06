import client.Client;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import game.*;
import server.Server;
import strategy.*;

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
        Card c2 = new Card("Marche", Type.MARKET, null, 1);
        Card c3 = new Card("Tisserie", Type.CRAFT, map2, 1);

        Board b1 = new Board("Pharos", Resources.STONE);
        Board b2 = new Board("Atlantis", Resources.STONE);
        Board b3 = new Board("Uruk", Resources.STONE);
        Board b4 = new Board("Venezia", Resources.STONE);


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

        List<Card> oldList = new ArrayList<Card>();
        List<Card> oldList2 = new ArrayList<Card>();
        List<Card> oldList3 = new ArrayList<Card>();
        List<Card> oldList4 = new ArrayList<Card>();

        List<Card> played = new ArrayList<Card>();
        List<Card> played2 = new ArrayList<Card>();
        List<Card> played3 = new ArrayList<Card>();
        List<Card> played4 = new ArrayList<Card>();

        final List<Player> listPlayer = new ArrayList<Player>();

        Map<Resources, Integer> resourcesContainer = new HashMap<Resources, Integer>();
        resourcesContainer.put(Resources.STONE, 1);
        resourcesContainer.put(Resources.SCIENCE, 1);
        
        //Check Nb joueur
        switch(nbJoueur) {
        case 1: try {
				Player p1 = new Player(0, b1, list, oldList, resourcesContainer, played, 3, 0 , 0, null, null);
				listPlayer.add(p1);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
        break;
        case 2: try {
				Player p1 = new Player(0, b1, list, oldList, resourcesContainer, played, 2, 0 , 1, null, null);
				Player p2 = new Player(1, b2, list2, oldList2, resourcesContainer, played2, 3, 0 , 0, null, null);
				listPlayer.add(p1);
				listPlayer.add(p2);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
        break;
        case 3: try {
				Player p1 = new Player(0,b1, list, oldList, resourcesContainer, played, 1, 2 , 1, null, null);
				Player p2 = new Player(1,b2, list2, oldList2, resourcesContainer, played2, 3, 0 , 0, null, null);
				Player p3 = new Player(2,b3, list3, oldList3, resourcesContainer, played3, 3, 2 , 1, null, null);
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
				Player p1 = new Player(0,b1, list, oldList, resourcesContainer, played, 1, 2 , 1, null, null);
				Player p2 = new Player(1,b2, list2, oldList2, resourcesContainer, played2,  3, 0 , 0, null, null);
				Player p3 = new Player(2,b3, list3, oldList3, resourcesContainer, played3,3, 2 , 1, null, null);
				Player p4 = new Player(3,b4, list4, oldList4, resourcesContainer, played4,2, 1 , 3, null, null);
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
        
        /*for(int i=0;i<listPlayer.size();i++) {
        //System.out.println(listPlayer.get(i).toString());
        }*/

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

        Stragegy discard = new DiscardStrategy();
        final Stragegy rndDiscard = new DiscardRandomStrategy();
        final Stragegy scientistMilitary = new PlaySmartStrategy(Type.SCIENCE, Type.MILITARY);
        final Stragegy socialMilitary = new PlaySmartStrategy(Type.SOCIAL, Type.MILITARY);

        new Thread(r).start();

        for(int i = 0;i<listPlayer.size();i++)
        {        	
        	final int ind = i;        
        	Runnable ru = new Runnable() {
        		public void run() {
        			Client client = new Client(ind, listPlayer.get(ind), rndDiscard, "http://127.0.0.1", 8080);
        	        client.connect();
        		}
        	};
        	new Thread(ru).start();
        }
    }
}
