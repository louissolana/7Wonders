package game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CostTest {
	
	private Cost cost;

	@Before
	public void setUp() throws Exception {
		cost = new Cost(Resources.GOLD, 10);
	}

	@Test
	public void testCost() {
		assertEquals(cost.getRes(), Resources.GOLD);
        assertEquals(cost.getQuant(), 10);
	}

}
