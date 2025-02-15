package utils.helpers.elementHelper;

import org.openqa.selenium.By;

public class Elements {

    public static By getElementInfoToBy(ElementResponse element) {
        ElementType elementType = ElementType.fromString(element.getType());
        switch (elementType) {
            case CLASS_NAME:
                return By.className(element.getValue());
            case ID:
                return By.id(element.getValue());
            case XPATH:
                return By.xpath(element.getValue());
            case NAME:
                return By.name(element.getValue());
            case CSS:
                return By.cssSelector(element.getValue());
            default:
                throw new IllegalArgumentException("Unknown element type: " + element.getType());
        }
    }
    }