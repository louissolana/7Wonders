package game;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    Card c1, c2;
    Map<Resources, Integer> map;


    @Test
    public void checkCard1() {
        c1 = new Card("Carriere", Type.RESOURCE, null, 1);
        assertEquals(c1.getAge(), 1);
        assertEquals(c1.getName(), "Carriere");
        assertEquals(c1.getType(), Type.RESOURCE);
    }

    @Test
    public void checkCard2() {
        map = new HashMap<Resources, Integer>();
        map.put(Resources.CLAY, 2);
        c2 = new Card("Caserne", Type.MILITARY, map, 1);

        assertEquals(c2.getAge(), 1);
        assertEquals(c2.getName(), "Caserne");
        assertEquals(c2.getType(), Type.MILITARY);

        assertTrue(c2.getCost().keySet().contains(Resources.CLAY));
        assertTrue(c2.getCost().values().contains(2));
    }
}