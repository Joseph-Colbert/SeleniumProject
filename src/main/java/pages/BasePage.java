package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    protected WebElement waitForElement(By element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    protected WebElement waitForElementToBeClickable(By element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected boolean isElementDisplayed(By element) {
        try {
            return waitForElement(element).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Espera utilizada por Firefox
    protected void clickElement(By element) {
        WebElement webElement = waitForElementToBeClickable(element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", webElement);
        try {
            webElement.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();",
                    webElement
            );
        }
    }

    // Espera utilizada por Firefox
    protected void waitForElementToDisappear(By element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
    }
}