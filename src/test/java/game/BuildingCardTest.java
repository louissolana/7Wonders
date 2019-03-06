package game;

import static org.junit.Assert.*;

import org.junit.Test;

public class BuildingCardTest {

	Card bc1;

    @Test
    public void testBuildingCard1() {
        bc1 = new BuildingCard("Forum", Type.SOCIAL, null, 1, new BuildingCard("Theatre", Type.SOCIAL, null, 2, null));
        assertEquals(((BuildingCard) bc1).getPrebuild().getName(), "Theatre");
    }

}
