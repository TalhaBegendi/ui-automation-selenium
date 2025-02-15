package utils.helpers.elementHelper;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ElementType {
    CLASS_NAME("className"),
    ID("id"),
    XPATH("xpath"),
    NAME("name"),
    CSS("css");

    private final String type;

    public static ElementType fromString(String type) {
        for (ElementType elementType : ElementType.values()) {
            if (elementType.getType().equalsIgnoreCase(type)) {
                return elementType;
            }
        }
        throw new IllegalArgumentException("Unknown element type: " + type);
    }
}
