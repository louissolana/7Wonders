package game;

import static org.junit.Assert.*;

import org.junit.Test;

public class CostTest {
	
	 private Resources res;
	 private int quant;
	 private Cost c;

	@Test
	public void testCost() {
		res = Resources.ORE;
		quant = 4;
		c = new Cost(res, quant);
		assertEquals(c.getQuant(), 4);
		assertEquals(c.getRes(), Resources.ORE);
	}
}
