package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AddEmployeePage extends BasePage{

    private final By pinMenu = By.cssSelector("a[href='/web/index.php/pim/viewPimModule']");
    private final By addButton = By.xpath("//button[normalize-space()='Add']");;
    private final By firstNameInput = By.name("firstName");
    private final By lastNameInput = By.name("lastName");
    private final By employeeIdInput = By.xpath("//label[normalize-space()='Employee Id']/following::input[1]");
    private final By loginDetailsButton = By.className("oxd-switch-input--active");
    private final By userNameInput = By.xpath("//label[normalize-space()='Username']/following::input[1]");
    private final By passwordInput = By.cssSelector("input[type='password']");
    private final By saveButton = By.cssSelector("button.orangehrm-left-space");


    String employeeIdValue;

    public AddEmployeePage(WebDriver driver) {
        super(driver);
    }

    public void addEmployee(String firstName, String lastName, String userName, String password) {
        waitForElement(pinMenu).click();
        waitForElement(addButton).click();
        waitForElement(firstNameInput).sendKeys(firstName);
        driver.findElement(lastNameInput).sendKeys(lastName);
        employeeIdValue = driver.findElement(employeeIdInput).getAttribute("value");
        System.out.println(employeeIdValue);
        driver.findElement(loginDetailsButton).click();
        driver.findElement(userNameInput).sendKeys(userName);
        List<WebElement> passwords = driver.findElements(passwordInput);
        passwords.get(0).sendKeys(password);
        passwords.get(1).sendKeys(password);
        driver.findElement(saveButton).click();
    }

}
