package game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BuildingCardTest {
	
	Card bc1;
	
	@Before
    public void setUp() throws Exception {
		bc1 = new BuildingCard("Forum", Type.SOCIAL, null, 1, new BuildingCard("Theatre", Type.SOCIAL, null, 2, null));
	}
	
	@Test
	public void testBuildingCard() {
        assertEquals(((BuildingCard) bc1).getPrebuild().getName(), "Theatre");
	}
}
