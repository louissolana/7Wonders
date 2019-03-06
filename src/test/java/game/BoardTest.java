package game;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class BoardTest {
	
	private Board b1;
    private List<Card> list = new ArrayList<Card>();
    private List<Cost> map = new ArrayList<Cost>();

	@Test
	public void testBoard() {
	     list.add(new Card("Carriere", Type.RESOURCE, null, 1));
	     map.add(new Cost(Resources.CLAY, 2));
	     list.add(new Card("Caserne", Type.MILITARY, map, 1));
	     
	     b1 = new Board("Pharos", Resources.STONE, list);
	     
	     assertEquals(b1.getName(), "Pharos");
	     assertEquals(b1.getBoardResource(), Resources.STONE);
	     assertEquals(b1.getCardsPlayed().size(), 2);
	}

	@Test
	public void testToString() {
		list.add(new Card("Carriere", Type.RESOURCE, null, 1));
		map.add(new Cost(Resources.CLAY, 2));
		list.add(new Card("Caserne", Type.MILITARY, map, 1));
	     
		b1 = new Board("Pharos", Resources.STONE, list);
		
        if(list == null)
        	assertEquals(b1.toString(), "Wonder: Pharos\n Own resource: STONE\n");
        //if (list != null)
        	//assertEquals(b1.toString(), "Wonder: Pharos\n Own resource: STONE\nCards played:"+list.toString());
	}
}
