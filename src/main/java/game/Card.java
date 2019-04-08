package game;

import java.util.List;
import java.util.Map;
import java.io.UnsupportedEncodingException;

import org.json.simple.JSONObject;
import sun.rmi.runtime.Log;

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
    	obj.put("cardName", name);
    	obj.put("cardType", type);
    	if(cost.size() >= 0)
    	{
    		obj.put("cardCost", cost.get(0));
    	}
    	obj.put("cardAge", age);
    	
    	return obj;
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
