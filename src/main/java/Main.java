import game.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Resources, Integer> map1 = new HashMap<Resources, Integer>();
        map1.put(Resources.GOLD, 1);

        Map<Resources, Integer> map2 = new HashMap<Resources, Integer>();
        map2.put(Resources.WOOD, 2);
        Card c1 = new Card("Mine", Type.RESOURCE, map1, 1);
        Card c2 = new Card("Marché", Type.MARKET, null, 1);
        Card c3 = new Card("Tisserie", Type.CRAFT, map2, 1);
        System.out.println(c1.toString());

        Board b1 = new Board("Pharos", Resources.STONE, null);

        System.out.println(b1.toString());

        List<Card> list = new ArrayList<Card>();
        list.add(c1);
        list.add(c2);
        list.add(c3);

        Map<Resources, Integer> resourcesContainer = new HashMap<Resources, Integer>();
        resourcesContainer.put(Resources.STONE, 1);
        resourcesContainer.put(Resources.SCIENCE, 1);
        Player p1 = new Player(b1, list, null, resourcesContainer, 3, 0 , 0, null, null);

        System.out.println(p1.toString());
    }
}
