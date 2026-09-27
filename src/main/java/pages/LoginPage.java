package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By userNameInput = By.name("username");
    private final By passwordInput = By.name("password");
    private final By loginButton = By.className("orangehrm-login-button");

    // Elemento que aparece después de iniciar sesión correctamente.
    private final By dashboardTitle =
            By.xpath("//h6[normalize-space()='Dashboard']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String userName, String password) {
        waitForElement(userNameInput).sendKeys(userName);
        waitForElement(passwordInput).sendKeys(password);
        waitForElementToBeClickable(loginButton).click();
    }

    public boolean isLoginSuccessful() {
        return isElementDisplayed(dashboardTitle);
    }
}