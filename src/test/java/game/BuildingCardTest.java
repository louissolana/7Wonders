package game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingCardTest {
    Card bc1;

    @Test
    public void testBuildingCard1() {
        bc1 = new BuildingCard("Forum", Type.SOCIAL, null, 1,
                new BuildingCard("Theatre", Type.SOCIAL, null, 2, null));

        assertEquals(((BuildingCard) bc1).getPrebuild().getName(), "Theatre");
    }
}