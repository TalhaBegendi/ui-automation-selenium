package utils.driver;

import interfaces.AbstractPageImp;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import pages.AbstractPage;

public class WebDriverManager {

    private static WebDriver driver;
    @Getter
    private static final WebDriverManager instance = new WebDriverManager();
    private final DriverFactory driverFactory = new DriverFactory();
    private AbstractPageImp abstractPageImp ;

    public WebDriver getDriver() {
        if (driver == null) {
            System.out.println("Creating a new driver...");
            driver = driverFactory.initializeAndGetDrivers();
        }
        return driver;
    }

    public void clearDriver() {
        if (driver != null) {
            System.out.println("Closing driver...");
            driver.quit();
            driver = null;
        }
    }

    @Before
    public void setUp() {
        System.out.println("Starting scenario...");
        getDriver();
        abstractPageImp = new AbstractPage() {};
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            abstractPageImp.takeScreenshot();
            System.out.println("Scenario failed, closing driver...");
        }
        clearDriver();
    }
}