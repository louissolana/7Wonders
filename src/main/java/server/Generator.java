package server;

import game.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Generator {
    private int players;

    public Generator(int p){
        players = p;
    }

    public List<Card> generateCards() {
        List<Card> deck = new ArrayList<Card>();
        JSONParser parser = new JSONParser();

        try {
            JSONArray infos = (JSONArray)parser.parse(new FileReader("../../resources/cards.json"));

            for(Object o: infos) {
                JSONObject jo = (JSONObject) o;
                if((Integer)jo.get("player") <= players) {
                    String cardName = (String)jo.get("name");
                    String type = (String)jo.get("type");
                    Type cardType = null;
                    // MILITARY, SOCIAL, SCIENCE, RESOURCE, CRAFT, MARKET
                    if(type.equals("MILITARY")) cardType = Type.MILITARY;
                    if(type.equals("SOCIAL")) cardType = Type.SOCIAL;
                    if(type.equals("SCIENCE")) cardType = Type.SCIENCE;
                    if(type.equals("RESOURCE")) cardType = Type.RESOURCE;
                    if(type.equals("CRAFT")) cardType = Type.CRAFT;
                    if(type.equals("MARKET")) cardType = Type.MARKET;
                    Integer cardAge = (Integer)jo.get("age");

                    if((JSONArray)jo.get("cost") == null) {
                        deck.add(new Card(cardName, cardType, null, cardAge));
                    } else {
                        List<Cost> costs = new ArrayList<Cost>();
                        String resource = (String)jo.get("res");
                        String[] tab = resource.split("_");
                        Resources costType = Resources.GOLD;
                        if (tab[0].equals("clay")) costType = Resources.CLAY;
                        if (tab[0].equals("stone")) costType = Resources.STONE;
                        if (tab[0].equals("ore")) costType = Resources.ORE;
                        if (tab[0].equals("wood")) costType = Resources.WOOD;
                        if (tab[0].equals("cloth")) costType = Resources.CLOTH;
                        if (tab[0].equals("science")) costType = Resources.SCIENCE;
                        if (tab[0].equals("military")) costType = Resources.MILITARY;
                        if (tab[0].equals("compass")) costType = Resources.COMPASS;
                        if (tab[0].equals("gear")) costType = Resources.GEAR;
                        if (tab[0].equals("paper")) costType = Resources.PAPER;
                        if (tab[0].equals("tablet")) costType = Resources.TABLET;
                        Cost cost = new Cost(costType, Integer.parseInt(tab[1]));
                        costs.add(cost);
                        deck.add(new Card(cardName, cardType, costs, cardAge));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return deck;
    }

    public List<Board> generateBoards() {
        return null;
    }
}
