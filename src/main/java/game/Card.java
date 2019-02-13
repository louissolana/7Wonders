package game;

import java.util.ArrayList;
import java.util.List;

public class Card {
    private String name;
    private Type type;
    private List<Resources> cost; // can be null
    private int age; // 1, 2, 3

    public Card(String name, Type type, ArrayList<Resources> resources, int age) {
        this.name = name;
        this.type = type;
        this.cost = resources;
        this.age = age;
    }
}
