package game;

import java.util.ArrayList;

public class BuildingCard extends Card {

    private BuildingCard prebuild;

    public BuildingCard(String name, Type type, ArrayList<Resources> resources, int age, BuildingCard builds) {
        super(name, type, resources, age);
        this.prebuild = builds;
    }
}
