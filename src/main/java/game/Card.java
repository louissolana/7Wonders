package game;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Card {
    private String name;
    private Type type;
    private List<Cost> cost; // can be null
    private int age; // 1, 2, 3

    public Card(String name, Type type, List<Cost> resources, int age) {
        this.name = name;
        this.type = type;
        this.cost = resources;
        this.age = age;
    }
    
    public JSONObject CardToJson()
    {
    	JSONObject obj = new JSONObject();
    	obj.put("name", name);
    	obj.put("type", Type.parseType(type));
    	obj.put("age", ""+age);
        JSONArray costs = new JSONArray();
    	if (cost != null) {
            for (Cost c: cost
                 ) {
                JSONObject o = new JSONObject();
                o.put("res", Resources.parseResource(c.getRes())+"_"+c.getQuant());
                costs.add(o);
            }
        }
    	obj.put("cost", costs);
    	return obj;
    }

    public static List<Card> JsonToCard(String json) {
        List<Card> res = new ArrayList<Card>();
        JSONParser parser = new JSONParser();
        try {
            JSONArray cards = (JSONArray)parser.parse(json);
            for (Object o: cards
                 ) {
                JSONObject ca = (JSONObject)o;
                String ca_name = (String)ca.get("name");
                String ca_type = (String)ca.get("type");
                String ca_age = (String)ca.get("age");
                JSONArray ca_costs = (JSONArray)ca.get("cost");
                List<Cost> costs = new ArrayList<Cost>();
                if(!ca_costs.isEmpty()) {
                    for (Object ob: ca_costs) {
                        JSONObject tmp = (JSONObject)ob;
                        String co = (String)tmp.get("res");
                        String[] split = co.split("_");
                        costs.add(new Cost(Resources.parseStringResource(split[0]), Integer.parseInt(split[1])));
                    }
                    res.add(new Card(ca_name, Type.parseStringType(ca_type), costs, Integer.parseInt(ca_age)));
                } else {
                    res.add(new Card(ca_name, Type.parseStringType(ca_type), null, Integer.parseInt(ca_age)));
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--------------------------- \n");
        sb.append("Card name: " + name + "\n");
        sb.append("Card type: " + type + "\n");
        sb.append("Card age: " + age + "\n");
        if(cost != null && !cost.isEmpty()) {
            for(Cost co: cost) {
                sb.append(co.getRes() + ": " + co.getQuant() + "\n");
            }
        }
        sb.append("--------------------------- \n");
        return sb.toString();
    }

    public String getName() {

        return name;
    }

    public Type getType() {
        return type;
    }

    public List<Cost> getCost() {
        return cost;
    }

    public int getAge() {
        return age;
    }
}
