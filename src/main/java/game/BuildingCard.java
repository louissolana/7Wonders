package game;

import java.util.List;

public class BuildingCard extends Card {

    private BuildingCard prebuild;

    public BuildingCard(String name, Type type, List<Cost> resources, int age, BuildingCard builds) {
        super(name, type, resources, age);
        this.prebuild = builds;
    }

    public BuildingCard getPrebuild() {
        return prebuild;
    }
}

