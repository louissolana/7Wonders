package game;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class UserTest {
	
	private Player p1;
	private Player p2;
    private Player playerLeft;
    private Player playerRight;
	private Board playerBoard;
    private List<Card> hand;
    private List<Card> oldHand;
    private List<Card> alreadyPlayed;
    private Map<Resources, Integer> resources;
    
    private int gold;
    private int victory;
    private int battle;
    private int id;

    Card c1;
    Card c2;
    Card c3;
    Card c4;
    Card c5;
    Card c6;
    Card c7;
    Card c8;
    Card c9;

	@Before
	public void setUp() throws Exception {
		id = 8;
		gold = 10;
		victory = 1;
		battle = 1;
		playerBoard = new Board("Pharos", Resources.STONE);
		hand = new ArrayList<Card>();
		oldHand = new ArrayList<Card>();
		alreadyPlayed = new ArrayList<Card>();
		
		playerLeft = null;
		playerRight = null;
		
		c1 = new Card("Mine", Type.RESOURCE, null, 1);
		c2 = new Card("Marche", Type.MARKET, null, 1);
		c3 = new Card("Tisserie", Type.CRAFT, null, 1);
		
	    hand.add(c1);
		hand.add(c2);
		hand.add(c3);
		
		c4 = new Card("GenericCardOne", Type.MILITARY, null, 1);
	    c5 = new Card("GenericCardTwo", Type.SCIENCE, null, 5);
	    c6 = new Card("GenericCardThree", Type.SOCIAL, null, 2);
	    
		oldHand.add(c4);
		oldHand.add(c5);
		oldHand.add(c6);
		
		c7 = new Card("PlayedCardOne", Type.CRAFT, null, 3);
	    c8 = new Card("PlayedCardTwo", Type.MARKET, null, 2);
	    c9 = new Card("PlayedCardThree", Type.SOCIAL, null, 1);
	    
		alreadyPlayed.add(c7);
		alreadyPlayed.add(c8);
		alreadyPlayed.add(c9);
		
		resources = new HashMap<Resources, Integer>();
		resources.put(Resources.STONE, 6);
        resources.put(Resources.SCIENCE, 3);
        resources.put(Resources.GEAR, 2);
        
        p1 = new Player(id, playerBoard, hand, oldHand, resources, alreadyPlayed, gold, victory, battle, playerLeft, playerRight);
	}

	@Test
	public void testPlayer() {
		assertEquals(p1.getId(), id);
        assertEquals(p1.getCardsPlayed(), alreadyPlayed);
        assertEquals(p1.getResources(), resources);
		assertEquals(p1.getHand(), hand);
		assertEquals(p1.getResources(), resources);
	}

	@Test
	public void testChangeAmountGold() {
		int newAmount = 6;
		p1.changeAmountGold(newAmount);
		assertEquals(p1.getGold(), 16);
	}

	@Test
	public void testAddResources() {
	    Map<Resources, Integer> dummyExpectedResources;
	    
	    dummyExpectedResources = new HashMap<Resources, Integer>();
	    dummyExpectedResources.put(Resources.STONE, 6);
	    dummyExpectedResources.put(Resources.SCIENCE, 3);
	    dummyExpectedResources.put(Resources.GEAR, 2);
	    dummyExpectedResources.put(Resources.GOLD, 2);
	    dummyExpectedResources.put(Resources.WOOD, 4);
	    dummyExpectedResources.put(Resources.PAPER, 6);
	    
	    p1.addResources(Resources.GOLD, 2);
	    p1.addResources(Resources.WOOD, 4);
	    p1.addResources(Resources.PAPER, 6);
	    
	    assertEquals(dummyExpectedResources, resources);
	}
}
