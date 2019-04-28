package game;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class CardTest {
	
	private List<Cost> map;
	private List<Card> res;
	private Card c1;
	private Card c2;
	
	@Before
    public void setUp() throws Exception {
		map = new ArrayList<Cost>();
		map.add(new Cost(Resources.CLAY, 2));
		
		res = new ArrayList<Card>();
		
		c1 = new Card("Carriere", Type.RESOURCE, null, 1);
		c2 = new Card("Caserne", Type.MILITARY, map, 1);
    }

	@Test
	public void testCard() {
        assertEquals(c1.getName(), "Carriere");
        assertEquals(c1.getType(), Type.RESOURCE);
        assertEquals(c1.getAge(), 1);
	}
	
	public void checkCard2() {
        assertEquals(c2.getName(), "Caserne");
        assertEquals(c2.getType(), Type.MILITARY);
        assertEquals(c2.getAge(), 1);
        
        assertEquals(c2.getCost().get(0).getRes(), Resources.CLAY);
        assertEquals(c2.getCost().get(0).getQuant(), 2);
    }

	/*
	@Test actuellement Impossible car JSONAssert non compatible avec library org.json.simple.JSONObject (necessite org.json.JSONObject)
	public void testCardToJson() {
		String expected = "{\"name\": \"Carriere\", \"age\": \"1\", \"type\": \"resource\", \"cost\": []}";
		JSONObject obj = c1.CardToJson();
		JSONAssert.assertEquals(expected, obj, false);
	}*/
	/*
	@Test
	public void testCardToJsonWithCost() {
		String expected = "{\"name\": \"Caserne\", \"age\": \"1\", \"type\": \"military\", \"cost\": [{\"res\": \"clay_2\"}]}";
		JSONObject obj = c2.CardToJson();
		JSONAssert.assertEquals(expected, obj, false);
	}*/

	@Test
	public void testJsonToCard() {
		String dummyJson = "[{\"name\": \"Carriere\", \"age\": \"1\", \"type\": \"resource\", \"cost\": []}]";
		String actual = Card.JsonToCard(dummyJson).toString();
		String expected = "["+c1.toString()+"]";
		assertEquals(expected, actual);
	}
	
	@Test
	public void testJsonToCardWithCost() {
		String dummyJson = "[{\"name\": \"Caserne\", \"age\": \"1\", \"type\": \"military\", \"cost\": [{\"res\": \"clay_2\"}]}]";
		String actual = Card.JsonToCard(dummyJson).toString();
		String expected = "["+c2.toString()+"]";
		assertEquals(expected, actual);
	}

	@Test
	public void testToString() {
		String expected = "--------------------------- \nCard name: " + c1.getName() + "\nCard type: " + c1.getType() + "\nCard age: " + c1.getAge() + "\n--------------------------- \n";
		String actual = c1.toString();
		assertEquals(expected, actual);	
	}
	
	@Test
	public void testToStringWithCost() {
		String expected = "--------------------------- \nCard name: " + c2.getName() + "\nCard type: " + c2.getType() + "\nCard age: " + c2.getAge() + "\nCLAY: 2\n--------------------------- \n";
		String actual = c2.toString();
		assertEquals(expected, actual);	
	}
}
