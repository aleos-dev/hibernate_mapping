package entity.enums;

public enum SpecialFeature {

    TRAILERS("Trailers"),
    COMMENTARIES("Commentaries"),
    DELETED_SCENES("Deleted Scenes"),
    BEHIND_THE_SCENES("Behind the Scenes"),
    NONE("");

    private final String value;

    SpecialFeature(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static SpecialFeature parseSpecialFeature(String s) {
        return switch (s) {
            case "Trailers" -> TRAILERS;
            case "Commentaries" -> COMMENTARIES;
            case "Deleted Scenes" -> DELETED_SCENES;
            case "Behind the Scenes" -> BEHIND_THE_SCENES;
            default -> NONE;
        };
    }
}
