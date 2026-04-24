package core;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.TIMEOUT), Duration.ofSeconds(Constants.POLLING));
        PageFactory.initElements(driver, this);
    }

    public void navigateToUrl(String url){
        driver.navigate().to(url);
    }

    public void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    public void type(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }

    public String getText(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText();
    }

    public boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }
    public void clearInput(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.BACK_SPACE);
    }

    public void scrollToElement(WebElement element) {
        WebDriver originalDriver = driver;

        // ✅ Unwrap SelfHealingDriver
        if (driver instanceof com.epam.healenium.SelfHealingDriver) {
            originalDriver = ((com.epam.healenium.SelfHealingDriver) driver).getDelegate();
        }

        JavascriptExecutor js = (JavascriptExecutor) originalDriver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

}
