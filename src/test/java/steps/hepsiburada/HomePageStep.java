package steps.hepsiburada;

import io.cucumber.java.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.hepsiburada.HomePage;

public class HomePageStep {

    public HomePage homePage = new HomePage();

    @And("Click {string} elementss")
    public void click(String key) {
        homePage.click(key);
    }

    @And("Fill {string} field with {string}")
    public void clearAndFill(String key, String text) {
        homePage.clearAndFill(key, text);
    }

    @And("Wait for given seconds {int}")
    public void waitForGivenSeconds(int seconds) {
        homePage.waitForTime(seconds);
    }

    @When("Check page url contains {string}")
    public void checkPageUrlContains(String url) {
        homePage.verifyUrl(url);
    }


    @Then("Check equality of element text {string} and with text {string}")
    public void checkTextEquals(String key,String text) {
        homePage.assertTextEquals(key,text);
    }

    @And("Click {string} random product")
    public void randomProduct(String key) {
        homePage.clickRandomProducts(key);
    }

    @And("Keep running codes in new tab")
    public void keepRunningCodesNewTabs() {
        homePage.keepRunningCodesNewTab();
    }

    @And("Click {string} drop down and select {string} with text")
    public void clickStringDropDownAndSelectText(String key, String value) {
        homePage.dropDown(key, value);
    }

    @And("Click {string} center element")
    public void clickCenterElementButton(String key) {
        homePage.clickCenterElementButton(key);
    }

    @Then("Check with element if there is no reviews")
    public void checkNoReview() {
        homePage.checkNoReviews();
    }

    @Then("Check elements for Pages")
    public void checkElementsForPages() {
        homePage.checkElementsForPage();
    }

    @Then("Check {string} like button for product")
    public void checkInProductDetailLike(String text) {
        homePage.checkInProductDetailLike(text);
    }

    @And("Get {string} text of field and assign it to variable {string}")
    public void elementGetText(String key, String text) {
        homePage.elementGetText(key, text);
    }

    @Then("Check get first variable {string} and get second variable {string}")
    public void checkAndCompare2Variables(String textFirst, String textSecond) {
        homePage.checkTwoStoredValueEquality(textFirst, textSecond);
    }
}