package game;

// rouge, bleu, vert, marron, gris, jaune
public enum Type {
    MILITARY, SOCIAL, SCIENCE, RESOURCE, CRAFT, MARKET;

    public static String parseType(Type t) {
        if (t.equals(Type.MILITARY)) return "military";
        else if (t.equals(Type.SOCIAL)) return "social";
        else if (t.equals(Type.SCIENCE)) return "science";
        else if (t.equals(Type.RESOURCE)) return "resource";
        else if (t.equals(Type.CRAFT)) return "craft";
        else return "market";
    }

    public static Type parseStringType(String s) {
        if(s.equals("military")) return Type.MILITARY;
        else if(s.equals("social")) return Type.SOCIAL;
        else if(s.equals("science")) return Type.SCIENCE;
        else if(s.equals("resource")) return Type.RESOURCE;
        else if(s.equals("craft")) return Type.CRAFT;
        else return Type.MARKET;
    }
}

