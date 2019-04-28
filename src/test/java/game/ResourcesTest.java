package game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ResourcesTest {

	@Test
	public void testParseResource() {
		assertEquals("paper", Resources.parseResource(Resources.PAPER));
		assertEquals("gold", Resources.parseResource(Resources.GOLD));
		assertEquals("wood", Resources.parseResource(Resources.WOOD));
		assertEquals("clay", Resources.parseResource(Resources.CLAY));
		assertEquals("ore", Resources.parseResource(Resources.ORE));
		assertEquals("stone", Resources.parseResource(Resources.STONE));
		assertEquals("cloth", Resources.parseResource(Resources.CLOTH));
		assertEquals("science", Resources.parseResource(Resources.SCIENCE));
		assertEquals("compass", Resources.parseResource(Resources.COMPASS));
		assertEquals("gear", Resources.parseResource(Resources.GEAR));
		assertEquals("tablet", Resources.parseResource(Resources.TABLET));
		assertEquals("military", Resources.parseResource(Resources.MILITARY));
	}

	@Test
	public void testParseStringResource() {
		assertEquals(Resources.PAPER, Resources.parseStringResource("paper"));
		assertEquals(Resources.GOLD, Resources.parseStringResource("gold"));
		assertEquals(Resources.WOOD, Resources.parseStringResource("wood"));
		assertEquals(Resources.CLAY, Resources.parseStringResource("clay"));
		assertEquals(Resources.ORE, Resources.parseStringResource("ore"));
		assertEquals(Resources.STONE, Resources.parseStringResource("stone"));
		assertEquals(Resources.CLOTH, Resources.parseStringResource("cloth"));
		assertEquals(Resources.SCIENCE, Resources.parseStringResource("science"));
		assertEquals(Resources.COMPASS, Resources.parseStringResource("compass"));
		assertEquals(Resources.GEAR, Resources.parseStringResource("gear"));
		assertEquals(Resources.TABLET, Resources.parseStringResource("tablet"));
		assertEquals(Resources.MILITARY, Resources.parseStringResource("military"));
	}

}
