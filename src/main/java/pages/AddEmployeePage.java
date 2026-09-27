package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AddEmployeePage extends BasePage {

    private final By pimMenu = By.cssSelector("a[href='/web/index.php/pim/viewPimModule']");
    private final By addButton = By.xpath("//button[normalize-space()='Add']");
    private final By firstNameInput = By.name("firstName");
    private final By lastNameInput = By.name("lastName");
    private final By employeeIdInput = By.xpath("//label[normalize-space()='Employee Id']/following::input[1]");
    private final By loginDetailsButton = By.className("oxd-switch-input");
    private final By userNameInput = By.xpath("//label[normalize-space()='Username']/following::input[1]");
    private final By passwordInput = By.cssSelector("input[type='password']");
    private final By saveButton = By.xpath("//button[@type='submit' and normalize-space()='Save']");
    private final By successMessage = By.xpath("//p[contains(@class,'oxd-text--toast-message')]");
    private String employeeIdValue;

    public AddEmployeePage(WebDriver driver) {
        super(driver);
    }

    public void addEmployee(String firstName, String lastName, String userName, String password) {
        waitForElementToBeClickable(pimMenu).click();
        waitForElementToBeClickable(addButton).click();
        waitForElement(firstNameInput).sendKeys(firstName);
        waitForElement(lastNameInput).sendKeys(lastName);
        WebElement employeeId = waitForElement(employeeIdInput);
        // El ID propuesto por la demo puede estar ocupado. Las teclas actualizan
        // también el estado del formulario, a diferencia de clear() en este campo.
        employeeId.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        // Un valor basado en el tiempo reduce las colisiones entre ejecuciones.
        employeeIdValue = Long.toString(System.currentTimeMillis() % 100_000_000L);
        employeeId.sendKeys(employeeIdValue);
        if (!employeeIdValue.equals(employeeId.getAttribute("value"))) {
            throw new IllegalStateException("No se pudo asignar un ID único al empleado");
        }
        waitForElementToBeClickable(loginDetailsButton).click();
        waitForElement(userNameInput).sendKeys(userName);
        waitForElement(passwordInput);
        List<WebElement> passwords = driver.findElements(passwordInput);
        passwords.get(0).sendKeys(password);
        passwords.get(1).sendKeys(password);
        waitForElementToBeClickable(saveButton).click();
    }

    public boolean employeeSavedSuccessfully() {
        return isElementDisplayed(successMessage);
    }

    public String getEmployeeIdValue() {
        return employeeIdValue;
    }
}
