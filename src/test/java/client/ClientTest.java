package client;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import game.Board;
import game.Card;
import game.Player;
import game.Resources;
import game.Type;
import strategy.PlaySmartStrategy;
import strategy.Stragegy;

public class ClientTest {
	
	private Client c;
	
	private Player p;
	
	Player playerLeft = null;
    Player playerRight = null;
	Board playerBoard = new Board("Pharos", Resources.STONE);
	
    List<Card> hand = new ArrayList<Card>();
    List<Card> oldHand = new ArrayList<Card>();
    List<Card> alreadyPlayed = new ArrayList<Card>();
    Map<Resources, Integer> resources = new HashMap<Resources, Integer>();
    
    int gold = 4;
    int victory = 0;
    int battle = 0;
    int id = 2;

    Card c1 = new Card("Mine", Type.RESOURCE, null, 1);
    Card c2 = new Card("Marche", Type.MARKET, null, 1);
    Card c3 = new Card("Tisserie", Type.CRAFT, null, 1);
    Card c4 = new Card("GenericCardOne", Type.MILITARY, null, 1);
    Card c5 = new Card("GenericCardTwo", Type.SCIENCE, null, 5);
    Card c6 = new Card("GenericCardThree", Type.SOCIAL, null, 2);
    Card c7 = new Card("PlayedCardOne", Type.CRAFT, null, 3);
    Card c8 = new Card("PlayedCardTwo", Type.MARKET, null, 2);
    Card c9 = new Card("PlayedCardThree", Type.SOCIAL, null, 1);
    
    Stragegy scientistMilitary = new PlaySmartStrategy(Type.SCIENCE, Type.MILITARY);
    
	@Before
	public void setUp() throws Exception {
	    hand.add(c1);
		hand.add(c2);
		hand.add(c3);
		
		oldHand.add(c4);
		oldHand.add(c5);
		oldHand.add(c6);
		
		alreadyPlayed.add(c7);
		alreadyPlayed.add(c8);
		alreadyPlayed.add(c9);
		
		resources.put(Resources.STONE, 6);
        resources.put(Resources.SCIENCE, 3);
        resources.put(Resources.GEAR, 2);
        
        p = new Player(id, playerBoard, hand, oldHand, resources, alreadyPlayed, gold, victory, battle, playerLeft, playerRight);
        c = new Client(0, p, scientistMilitary, "http://127.0.0.1", 8080);
	}

	@Test
	public void testClient() {
		assertEquals(c.getId(), 0);
		assertEquals(c.getPlayer(), p);
	}

	@Test
	public void testIsNullOrEmpty() {
		String content = "lorem ipsum and stuff";
		assertEquals(false, c.isNullOrEmpty(content));
		String empty = "";
		assertEquals(true, c.isNullOrEmpty(empty));
		String nothing = null;
		assertEquals(true, c.isNullOrEmpty(nothing));
	}
	
	@Test
	public void testToString() {
		String expected = "Client id: " + c.getId() + p.toString();
		String actual = c.toString();
		assertEquals(expected, actual);
	}

}
