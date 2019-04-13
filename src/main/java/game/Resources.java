package game;

public enum Resources {
    WOOD, CLAY, ORE, STONE, CLOTH, PAPER, SCIENCE, COMPASS, GEAR, TABLET, MILITARY, GOLD;

    public static String parseResource(Resources r) {
        if(r.equals(Resources.WOOD)) return "wood";
        else if(r.equals(Resources.CLAY)) return "clay";
        else if(r.equals(Resources.ORE)) return "ore";
        else if(r.equals(Resources.STONE)) return "stone";
        else if(r.equals(Resources.CLOTH)) return "cloth";
        else if(r.equals(Resources.PAPER)) return "paper";
        else if(r.equals(Resources.SCIENCE)) return "science";
        else if(r.equals(Resources.COMPASS)) return "compass";
        else if(r.equals(Resources.GEAR)) return "gear";
        else if(r.equals(Resources.TABLET)) return "tablet";
        else if(r.equals(Resources.MILITARY)) return "military";
        else return "gold";
    }

    public static Resources parseStringResource(String s) {
        if(s.equals("wood")) return Resources.WOOD;
        else if(s.equals("clay")) return Resources.CLAY;
        else if(s.equals("ore")) return Resources.ORE;
        else if(s.equals("stone")) return Resources.STONE;
        else if(s.equals("cloth")) return Resources.CLOTH;
        else if(s.equals("paper")) return Resources.PAPER;
        else if(s.equals("science")) return Resources.SCIENCE;
        else if(s.equals("compass")) return Resources.COMPASS;
        else if(s.equals("gear")) return Resources.GEAR;
        else if(s.equals("tablet")) return Resources.TABLET;
        else if(s.equals("military")) return Resources.MILITARY;
        else return Resources.GOLD;
    }
}
