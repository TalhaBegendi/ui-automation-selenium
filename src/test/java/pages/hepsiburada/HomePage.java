package pages.hepsiburada;

import pages.AbstractPage;

public class HomePage extends AbstractPage {

    public void click(String key) {
        clickElement(findElement(key));
    }

    public void clearAndFill(String key, String text) {
        clearAndFillInput(key, text);
    }

    public void waitForTime(int seconds) {
        super.waitForTime(seconds);
    }

    public void verifyUrl(String url) {
        super.verifyUrl(url);
    }

    public void assertTextEquals(String key, String text) {
        assertTextEqual(findElement(key), text);
    }

    public void clickRandomProducts(String key) {
        clickRandomProduct(key);
    }

    public void dropDown(String key, String value){
        dropDownSelect(findElement(key), value);
    }
    public void clickCenterElementButton(String key){
        clickCenterElement(findElement(key));
    }

    public void checkInProductDetailLike(String key){
        checkInProductDetailLikeButton(findElement(key));
    }

    public void elementGetText(String key, String text) {
        findElementAndSetTextToDataStore(findElement(key), text);
    }
}