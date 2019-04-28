package game;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class BoardTest {
	
	private Board b1;
	
	@Before
    public void setUp() throws Exception {
		b1 = new Board("Pharos", Resources.STONE);
    }
	
	@Test
	public void testBoard() {
	    assertEquals(b1.getName(), "Pharos");
	    assertEquals(b1.getBoardResource(), Resources.STONE);
	}

	@Test
	public void testToString() {
        assertEquals(b1.toString(),"Wonder: Pharos\nOwn resource: STONE\n");
}

}
