package game;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    Board b1;
    List<Card> list;
    List<Cost> map;


    @Test
    public void testBoard() {
        list = new ArrayList<Card>();
        list.add(new Card("Carriere", Type.RESOURCE, null, 1));
        map = new ArrayList<Cost>();
        map.add(new Cost(Resources.CLAY, 2));
        list.add(new Card("Caserne", Type.MILITARY, map, 1));

        b1 = new Board("Pharos", Resources.STONE, list);

        assertEquals(b1.getName(), "Pharos");
        assertEquals(b1.getBoardResource(), Resources.STONE);
        assertEquals(b1.getCardsPlayed().size(), 2);
    }
}