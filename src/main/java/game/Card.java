package game;

import java.util.Map;

public class Card {
    private String name;
    private Type type;
    private Map<Resources, Integer> cost; // can be null
    private int age; // 1, 2, 3

    public Card(String name, Type type, Map<Resources, Integer> resources, int age) {
        this.name = name;
        this.type = type;
        this.cost = resources;
        this.age = age;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Card name: " + name);
        sb.append("Card type: " + type);
        sb.append("Card age: " + age);
        if(!cost.isEmpty()) {
            for(Map.Entry<Resources, Integer> entry: cost.entrySet()) {
                sb.append(entry.getKey() + ": " + entry.getValue());
            }
        }
        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public Map<Resources, Integer> getCost() {
        return cost;
    }

    public int getAge() {
        return age;
    }
}
