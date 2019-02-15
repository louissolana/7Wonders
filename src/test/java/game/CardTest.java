package game;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    Card c1, c2;
    List<Cost> map;


    @Test
    public void checkCard1() {
        c1 = new Card("Carriere", Type.RESOURCE, null, 1);
        assertEquals(c1.getAge(), 1);
        assertEquals(c1.getName(), "Carriere");
        assertEquals(c1.getType(), Type.RESOURCE);
    }

    @Test
    public void checkCard2() {
        map = new ArrayList<Cost>();
        map.add(new Cost(Resources.CLAY, 2));
        c2 = new Card("Caserne", Type.MILITARY, map, 1);

        assertEquals(c2.getAge(), 1);
        assertEquals(c2.getName(), "Caserne");
        assertEquals(c2.getType(), Type.MILITARY);

        assertEquals(c2.getCost().get(0).getRes(), Resources.CLAY);
        assertEquals(c2.getCost().get(0).getQuant(), 2);
    }
}