package entity.enums;

import lombok.Getter;

import java.util.Objects;

@Getter
public enum Rating {
    G("G"),
    PG("PG"),
    PG_13("PG-13"),
    R("R"),
    NC_17("NC-17"),
    NONE(null);

    private String value;

    Rating(String value) {
        this.value = value;
    }

    public static Rating fromString(String value) {

        if (Objects.isNull(value)) return NONE;

        return switch (value) {
            case "G" -> G;
            case "PG" -> PG;
            case "PG-13" -> PG_13;
            case "R" -> R;
            case "NC-17" -> NC_17;
            default -> NONE;
        };
    }
}

