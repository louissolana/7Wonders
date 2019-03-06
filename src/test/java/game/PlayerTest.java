package game;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class PlayerTest {
	
	private Player p;
	private Board playerBoard;
    private List<Card> hand = new ArrayList<Card>();
    private List<Card> oldHand = new ArrayList<Card>();
    private Map<Resources, Integer> resources = new HashMap<Resources, Integer>();
    private int gold = 8;
    private int victory;
    private int battle;
    private Player playerLeft;
    private Player playerRight;
    
    Card c1 = new Card("Mine", Type.RESOURCE, null, 1);
    Card c2 = new Card("March�", Type.MARKET, null, 1);
    Card c3 = new Card("Tisserie", Type.CRAFT, null, 1);
    
    Card c4 = new Card("GenericCardOne", Type.MILITARY, null, 1);
    Card c5 = new Card("GenericCardTwo", Type.SCIENCE, null, 5);
    Card c6 = new Card("GenericCardThree", Type.SOCIAL, null, 2);
    
	@Test
	public void testPlayer() {
		hand.add(c1);
		hand.add(c2);
		hand.add(c3);
		
		oldHand.add(c4);
		oldHand.add(c5);
		oldHand.add(c6);
		
		resources.put(Resources.STONE, 6);
        resources.put(Resources.SCIENCE, 3);
        resources.put(Resources.GEAR, 2);
		
		p = new Player (playerBoard, hand, oldHand, resources, gold, victory, battle, playerLeft, playerRight);
		
		assertEquals(p.getHand(), hand);
		assertEquals(p.getResources(), resources);
	}

	@Test
	public void testChangeAmountGold() {
		int val = 2;
		p = new Player (playerBoard, hand, oldHand, resources, gold, victory, battle, playerLeft, playerRight);
		p.changeAmountGold(val);
		assertEquals(p.getGold(), 10);
	}
}
