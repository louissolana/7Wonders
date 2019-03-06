package game;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CardTest {
	
	Card c1, c2;
    List<Cost> map = new ArrayList<Cost>();

    @Test
    public void checkCard1() {
        c1 = new Card("Carriere", Type.RESOURCE, null, 1);
        assertEquals(c1.getAge(), 1);
        assertEquals(c1.getName(), "Carriere");
        assertEquals(c1.getType(), Type.RESOURCE);
    }

    @Test
    public void checkCard2() {
        map.add(new Cost(Resources.CLAY, 2));
        c2 = new Card("Caserne", Type.MILITARY, map, 1);

        assertEquals(c2.getAge(), 1);
        assertEquals(c2.getName(), "Caserne");
        assertEquals(c2.getType(), Type.MILITARY);

        assertEquals(c2.getCost().get(0).getRes(), Resources.CLAY);
        assertEquals(c2.getCost().get(0).getQuant(), 2);
    }

	@Test
	public void testToString() {
		map.add(new Cost(Resources.GOLD, 5));
		c1 = new Card("Mine", Type.RESOURCE, map, 1);
        c2 = new Card("March�", Type.MARKET, null, 1);
		 String c1_test = "--------------------------- \nCard name: " + c1.getName() + "\nCard type: " + c1.getType() + "\nCard age: " + c1.getAge() + "\nGOLD: 5\n--------------------------- \n";
		 String c2_test = "--------------------------- \nCard name: " + c2.getName() + "\nCard type: " + c2.getType() + "\nCard age: " + c2.getAge() + "\n--------------------------- \n";
	        if(map == null || map.isEmpty()) {
	        	assertEquals(c2.toString(), c2_test);
	        }
	        if(map != null && !map.isEmpty()) {
	        	assertEquals(c1.toString(), c1_test);
	        }

	}
}
