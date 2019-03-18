package server;

import game.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
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
            String inputFile = new File("src/main/resources/cards.json").getAbsolutePath();
            JSONArray infos = (JSONArray)parser.parse(new FileReader(inputFile));

            for(Object o: infos) {
                JSONObject jo = (JSONObject) o;
                System.out.println(jo.get("player"));
                if(Integer.parseInt((String)jo.get("player")) <= players) {
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
                    Integer cardAge = Integer.parseInt((String)jo.get("age"));

                    if((JSONArray)jo.get("cost") == null || ((JSONArray) jo.get("cost")).isEmpty()) {
                        deck.add(new Card(cardName, cardType, null, cardAge));
                    } else {
                        List<Cost> costs = new ArrayList<Cost>();
                        JSONObject res = (JSONObject) ((JSONArray) jo.get("cost")).get(0);
                        String resource = (String)res.get("res");
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
        List<Board> boards = new ArrayList<Board>();
        JSONParser parser = new JSONParser();

        try {
            String inputFile = new File("src/main/resources/boards.json").getAbsolutePath();
            JSONArray infos = (JSONArray)parser.parse(new FileReader(inputFile));

            for(Object o: infos) {
                JSONObject jo = (JSONObject)o;
                String boardName = (String)jo.get("name");
                String res = (String)jo.get("own");
                Resources ownRes = Resources.GOLD;
                if (res.equals("clay")) ownRes = Resources.CLAY;
                if (res.equals("stone")) ownRes = Resources.STONE;
                if (res.equals("ore")) ownRes = Resources.ORE;
                if (res.equals("wood")) ownRes = Resources.WOOD;
                if (res.equals("cloth")) ownRes = Resources.CLOTH;
                if (res.equals("science")) ownRes = Resources.SCIENCE;
                if (res.equals("military")) ownRes = Resources.MILITARY;
                if (res.equals("compass")) ownRes = Resources.COMPASS;
                if (res.equals("gear")) ownRes = Resources.GEAR;
                if (res.equals("paper")) ownRes = Resources.PAPER;
                if (res.equals("tablet")) ownRes = Resources.TABLET;
                boards.add(new Board(boardName, ownRes, null));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return boards;
    }
}
