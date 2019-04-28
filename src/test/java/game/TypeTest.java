package game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TypeTest {
	
	@Test
	public void testParseType() {
		assertEquals("military", Type.parseType(Type.MILITARY));
		assertEquals("social", Type.parseType(Type.SOCIAL));
		assertEquals("science", Type.parseType(Type.SCIENCE));
		assertEquals("resource", Type.parseType(Type.RESOURCE));
		assertEquals("craft", Type.parseType(Type.CRAFT));
		assertEquals("market", Type.parseType(Type.MARKET));
	}

	@Test
	public void testParseStringType() {
		assertEquals(Type.MILITARY, Type.parseStringType("military"));
		assertEquals(Type.SOCIAL, Type.parseStringType("social"));
		assertEquals(Type.SCIENCE, Type.parseStringType("science"));
		assertEquals(Type.RESOURCE, Type.parseStringType("resource"));
		assertEquals(Type.CRAFT, Type.parseStringType("craft"));
		assertEquals(Type.MARKET, Type.parseStringType("market"));
	}

}
