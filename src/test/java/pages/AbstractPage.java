package pages;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import interfaces.AbstractPageImp;
import org.apache.commons.io.FileUtils;
import org.junit.Assume;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.driver.WebDriverManager;
import utils.helpers.dataStoreHelper.DataStoreMap;
import utils.helpers.elementHelper.ElementMap;
import utils.helpers.elementHelper.ElementResponse;
import utils.helpers.elementHelper.Elements;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public abstract class AbstractPage implements AbstractPageImp {

    public WebDriverWait wait;
    private WebDriver driver;
    private static final String SCREENSHOT_DIR = "screenshot/";
    private static int screenshotCounter = 1;

    public AbstractPage() {
        this.driver = WebDriverManager.getInstance().getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    DataStoreMap dataStoreMap = new DataStoreMap();

    public void clickElement(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (TimeoutException e) {
            Assert.fail(e.getMessage());
        }
    }

    public WebElement findElement(String key) {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < 7000) {
            try {
                return wait.until(ExpectedConditions.presenceOfElementLocated(getBy(key)));
            } catch (StaleElementReferenceException e) {
                System.out.println(e);
            }
        }
        return null;
    }

    public List<WebElement> findElements(String key) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getBy(key)));
    }

    public void clearAndFillInput(String key, String text) {
        try {
            centerElement(waitUntilElementIsVisible(findElement(key), System.currentTimeMillis())).clear();
            centerElement(waitUntilElementIsVisible(findElement(key), System.currentTimeMillis())).sendKeys(text);
        } catch (NoSuchElementException  e) {
            Assert.fail(e.getMessage());
        }
    }

    public void waitForTime(int seconds) {
        waitForGivenTime(seconds);
    }

    public void verifyUrl(String url) {
        Assert.assertTrue(driver.getCurrentUrl().contains(url));
    }

    public void assertTextEqual(WebElement key, String text) {
        String textProduct = wait.until(ExpectedConditions.visibilityOf(key)).getText();
        Assert.assertEquals(textProduct, text);
    }

    public void clickRandomProduct(String key) {
        List<WebElement> elementsList = findElements(key);
        if (!elementsList.isEmpty()) {
            WebElement[] elementsArray = elementsList.toArray(new WebElement[elementsList.size()]);
            Random rand = new Random();
            WebElement randomElement = elementsArray[rand.nextInt(elementsArray.length)];
            clickElement(randomElement);
        }
    }

    public void keepRunningCodesNewTab() {
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
    }

    public void clickElementWithText(String value) {
        try {
            centerElement(waitUntilElementIsClickable(getElementWithText(value), System.currentTimeMillis())).click();
        }  catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    public void dropDownSelect(WebElement key, String value) {
        centerElement(waitUntilElementIsVisible(key, System.currentTimeMillis())).click();
        clickElementWithText(value);
    }

    public void clickCenterElement(WebElement key) {
        centerElement(waitUntilElementIsClickable(key, System.currentTimeMillis())).click();
    }

    public void checkNoReviews() {
        List<WebElement> element = driver.findElements(By.className("hermes-ProductRate-module-iigXxhEaE3_4WHSctvzs"));
        if(element.size()==1)
        {Assert.assertTrue(true,"There is no reviews");Assume.assumeTrue(false);}
    }

    public void checkInProductDetailLikeButton(WebElement key) {
        String text = centerElement(waitUntilElementIsVisible(key, System.currentTimeMillis())).getText();
        if(text.equals("Beğendin"))
        {Assert.assertTrue(true,"He/She already has liked this product");}
        else
        {clickCenterElement(key);}
    }

    public void findElementAndSetTextToDataStore(WebElement key, String text) {
        String productPrice = centerElement(waitUntilElementIsVisible(key, System.currentTimeMillis())).getText();
        dataStoreMap.setContext(text, productPrice);
    }

    public void checkTwoStoredValueEquality(String textFirst, String textSecond) {
        Assert.assertEquals(dataStoreMap.getContext(textFirst), dataStoreMap.getContext(textSecond));
    }


    public WebElement waitUntilElementIsVisible(WebElement element, long startTime) {
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        WebDriverWait subWait = new WebDriverWait(driver, Duration.ofMillis(1));
        if ((System.currentTimeMillis() - startTime) > 5000) {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            return null;
        }
        try {
            subWait.until(ExpectedConditions.visibilityOf(element));
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            return element;
        } catch (StaleElementReferenceException | TimeoutException e) {
            return waitUntilElementIsVisible(element, startTime);
        }
    }

    public By getBy(String key) {
        ElementResponse elementInfo = ElementMap.INSTANCE.findElementInfoByKey(key);
        return Elements.getElementInfoToBy(elementInfo);
    }

    public WebElement getElementWithText(String text) {
        try {
            return driver.findElement(By.xpath("//*[text()='" + text + "']"));
        } catch (ElementNotFoundException e) {
            Assert.fail(e.getMessage());
            return null;
        }
    }

    public void checkElementsForPage() {
        String state = (String) ((JavascriptExecutor) driver).executeScript("return document.readyState");
        long startTime = System.currentTimeMillis();
        while (!state.equals("complete") && (System.currentTimeMillis() - startTime) < 7000) {
            waitForGivenTime(1);
            state = (String) ((JavascriptExecutor) driver).executeScript("return document.readyState");
        }}

    public WebElement centerElement(WebElement element) {

        String scrollScript = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                + "var elementTop = arguments[0].getBoundingClientRect().top;"
                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";

        ((JavascriptExecutor) driver).executeScript(scrollScript, element);
        waitForGivenTime(1);
        return element;
    }

    public void waitForGivenTime(int seconds)  {
        long milliseconds = (seconds * 1000L);
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
    }

    public WebElement waitUntilElementIsClickable(WebElement element, long startTime) {
        WebDriver subDriver = driver;
        subDriver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        WebDriverWait subWait = new WebDriverWait(subDriver, Duration.ofMillis(1));
        if ((System.currentTimeMillis() - startTime) > 5000) {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            return null;
        }
        try {
            subWait.until(ExpectedConditions.elementToBeClickable(element));
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            return element;
        } catch (StaleElementReferenceException | ElementClickInterceptedException | TimeoutException e) {
            System.out.println(e.getMessage());
            return waitUntilElementIsClickable(element, startTime);
        }
    }

    @Override
    public void takeScreenshot() {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotPath = SCREENSHOT_DIR + "screenshot_" + screenshotCounter++ + "_" + timestamp + ".png";

        try {
            FileUtils.copyFile(screenshot, new File(screenshotPath));
            System.out.println("Screenshot saved: " + screenshotPath);
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
        }
    }

}