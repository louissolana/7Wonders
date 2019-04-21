package game;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    Board b1;
    List<Cost> map;


    @Test
    public void testBoard() {
        map = new ArrayList<Cost>();
        map.add(new Cost(Resources.CLAY, 2));

        b1 = new Board("Pharos", Resources.STONE);

        assertEquals(b1.getName(), "Pharos");
        assertEquals(b1.getBoardResource(), Resources.STONE);
    }
}