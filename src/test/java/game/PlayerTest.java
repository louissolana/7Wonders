package game;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest
{
        Board b1;
        List<Card> list;
        List<Card> oldList;
        List<Card> played;
        List<Cost> map;
        Map<Resources, Integer> resourcesContainer = new HashMap<Resources, Integer>();
        Player p1;

        @Test
        public void testPlayer() {
            list = new ArrayList<Card>();
            list.add(new Card("Carriere", Type.RESOURCE, null, 1));
            map = new ArrayList<Cost>();
            map.add(new Cost(Resources.CLAY, 2));
            list.add(new Card("Caserne", Type.MILITARY, map, 1));

            b1 = new Board("Pharos", Resources.STONE);
            resourcesContainer.put(Resources.STONE, 1);
            resourcesContainer.put(Resources.SCIENCE, 1);

            Player p1 = new Player(0, b1, list, oldList, resourcesContainer, played, 3, 0 , 0, null, null);

            assertEquals(p1.getCardsPlayed().size(), 2);
        }
}
