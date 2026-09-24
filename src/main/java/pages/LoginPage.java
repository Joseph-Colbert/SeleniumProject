package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By userNameInput = By.name("username");
    private final By passwordInput = By.name("password");
    private final By loginButton = By.className("orangehrm-login-button");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String userName, String password) {
        waitForElement(userNameInput).sendKeys(userName);
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(loginButton).click();
    }
}