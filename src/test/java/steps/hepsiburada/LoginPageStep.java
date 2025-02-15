package steps.hepsiburada;

import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import pages.hepsiburada.LoginPage;

public class LoginPageStep {

    public LoginPage loginPage = new LoginPage();

    @And("Click {string} elements")
    public void click(String key) {
        loginPage.click(key);
    }

    @And("Fill {string} fields with {string}")
    public void clearAndFill(String key, String text) {
        loginPage.clearAndFill(key, text);
    }



}