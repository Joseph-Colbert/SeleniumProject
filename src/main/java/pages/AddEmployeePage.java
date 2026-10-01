package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
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
    private final By photoInput = By.cssSelector("input[type='file']");
    private final By employeePhoto = By.cssSelector("img.employee-image");
    private final By formLoader = By.cssSelector(".oxd-form-loader");
    private String employeeIdValue;

    public AddEmployeePage(WebDriver driver) {
        super(driver);
    }

    public void addEmployee(String firstName, String lastName, String userName, String password, Path photo) {
        if (!Files.isRegularFile(photo)) {
            throw new IllegalArgumentException("No existe la foto de prueba: " + photo);
        }
        waitForElementToBeClickable(pimMenu).click();
        waitForElementToBeClickable(addButton).click();
        waitForElement(firstNameInput).sendKeys(firstName);
        waitForElement(lastNameInput).sendKeys(lastName);
        WebElement employeeId = waitForElement(employeeIdInput);
        // Para recuperar la ID exacta
        employeeId.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        employeeIdValue = Long.toString(System.currentTimeMillis() % 100_000_000L);
        employeeId.sendKeys(employeeIdValue);
        if (!employeeIdValue.equals(employeeId.getAttribute("value"))) {
            throw new IllegalStateException("No se pudo asignar un ID único al empleado");
        }
        // Selenium puede enviar la ruta directamente al input de archivo, aunque esté oculto.
        driver.findElement(photoInput).sendKeys(photo.toAbsolutePath().toString());
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(webDriver ->
                webDriver.findElement(employeePhoto).getAttribute("src").startsWith("data:image/"));
        // Esta espera evita que Firefox intente activar el control antes de tiempo.
        clickElement(loginDetailsButton);
        waitForElement(userNameInput).sendKeys(userName);
        waitForElement(passwordInput);
        List<WebElement> passwords = driver.findElements(passwordInput);
        passwords.get(0).sendKeys(password);
        passwords.get(1).sendKeys(password);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .ignoring(ElementClickInterceptedException.class)
                .until(webDriver -> {
                    if (!webDriver.findElements(formLoader).isEmpty()) {
                        return false;
                    }
                    WebElement button = webDriver.findElement(saveButton);
                    if (!button.isEnabled()) {
                        return false;
                    }
                    button.click();
                    return true;
                });
    }

    public boolean employeeSavedSuccessfully() {
        return isElementDisplayed(successMessage);
    }

    public String getEmployeeIdValue() {
        return employeeIdValue;
    }

    public boolean photoSavedSuccessfully() {
        // Después del alta, la ficha debe mostrar una imagen distinta de la predeterminada.
        return new WebDriverWait(driver, Duration.ofSeconds(10)).until(webDriver -> {
            String source = webDriver.findElement(employeePhoto).getAttribute("src");
            return source != null && !source.isBlank() && !source.contains("default-photo.png");
        });
    }
}
