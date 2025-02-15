package utils.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import utils.helpers.PropertyManager;

public class DriverFactory {
    private WebDriver driver;
    String browserName;
    String domain;
    boolean runWithBrowser;

    public WebDriver initializeAndGetDrivers() {
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        PropertyManager propertyManager = PropertyManager.propertyManager;
        browserName = propertyManager.getProperty("env.properties", "browser");
        domain = propertyManager.getProperty("env.properties", "domain");
        runWithBrowser = Boolean.parseBoolean(
                propertyManager.getProperty("env.properties", "runWithBrowser"));

        switch (browserName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("disable-notifications");
                setHeadlessMode(chromeOptions, runWithBrowser);
//               System.setProperty("webdriver.chrome.driver",
//                        System.getProperty("user.dir") + "/drivers/chromedriver");
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                setHeadlessMode(firefoxOptions, runWithBrowser);
                driver = new FirefoxDriver(firefoxOptions);
                break;

            default:
                Assert.fail("Lütfen 'chrome', 'firefox' veya 'edge' şeklinde bir tarayıcı seçin.");
                return null;
        }
        driver.get(domain);
        return driver;
    }

    private void setHeadlessMode(Object options, boolean runWithBrowser) {
        if (!runWithBrowser) {
            if (options instanceof ChromeOptions) {
                ((ChromeOptions) options).addArguments("--headless");
            } else if (options instanceof FirefoxOptions) {
                ((FirefoxOptions) options).addArguments("-headless");
            }
        }
    }
}