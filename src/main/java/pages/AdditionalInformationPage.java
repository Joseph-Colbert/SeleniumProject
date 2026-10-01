package pages;

import enums.Gender;
import enums.MaritalStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AdditionalInformationPage extends BasePage {
    private final By employeeIdInput =
            By.xpath("//label[normalize-space()='Employee Id']/following::input[1]");

    private final By driverLicenceInput =
            By.xpath("//label[contains(normalize-space(),\"Driver's License Number\")]/following::input[1]");
    private final By expirationDateInput =
            By.xpath("//label[normalize-space()='License Expiry Date']/following::input[1]");
    private final By nationalitySelect =
            By.xpath("//label[normalize-space()='Nationality']/following::div[contains(@class,'oxd-select-text')][1]");
    private final By maritalStatusSelect =
            By.xpath("//label[normalize-space()='Marital Status']/following::div[contains(@class,'oxd-select-text')][1]");
    private final By dateOfBirthInput =
            By.xpath("//label[normalize-space()='Date of Birth']/following::input[1]");
    private final By genderMaleRadio =
            By.xpath("//label[contains(normalize-space(.),'Male')]");
    private final By genderFemaleRadio =
            By.xpath("//label[contains(normalize-space(.),'Female')]");
    private final By saveButton =
            By.xpath("//button[@type='submit' and normalize-space()='Save']");
    private final By successMessage =
            By.xpath("//p[contains(@class,'oxd-text--toast-message')]");
    private final By formLoader = By.cssSelector(".oxd-form-loader");

    public AdditionalInformationPage(WebDriver driver) {
        super(driver);
    }

    private By optionByText(String option) {
        return By.xpath("//div[@role='option']//span[normalize-space()='" + option + "']");
    }

    private void selectOption(By dropdown, String option) {
        clickWhenReady(dropdown);
        clickWhenReady(optionByText(option));
    }

    private void waitForFormReady() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.invisibilityOfElementLocated(formLoader));
    }

    private void clickWhenReady(By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .ignoring(ElementClickInterceptedException.class)
                .until(webDriver -> {
                    waitForFormReady();
                    WebElement element = webDriver.findElement(locator);
                    if (!element.isDisplayed() || !element.isEnabled()) {
                        return false;
                    }
                    element.click();
                    return true;
                });
    }

    private void selectGender(Gender gender) {
        if (gender == Gender.MALE) {
            clickWhenReady(genderMaleRadio);
        } else if (gender == Gender.FEMALE) {
            clickWhenReady(genderFemaleRadio);
        }
    }

    public void aditionalInformation(String employeeId, String driverLicenceNumber, String expirationDate, String nationality, MaritalStatus maritalStatus, String dateOfBirth, Gender gender) {
        // El formulario aparece antes de terminar de cargar el empleado; esperamos sus datos.
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(webDriver ->
                employeeId.equals(webDriver.findElement(employeeIdInput).getAttribute("value")));
        waitForFormReady();
        replaceValue(expirationDateInput, expirationDate);
        selectOption(nationalitySelect, nationality);
        selectOption(maritalStatusSelect, maritalStatus.toString());
        replaceValue(dateOfBirthInput, dateOfBirth);
        selectGender(gender);
        // En OrangeHRM, cambios posteriores en fechas y selectores pueden restaurar
        // el valor anterior de la licencia. Escribimos este campo al final.
        replaceValue(driverLicenceInput, driverLicenceNumber);
        waitForElement(driverLicenceInput).sendKeys(Keys.TAB);
        // Detecta si el control ha rechazado el valor antes de intentar guardar.
        if (!driverLicenceNumber.equals(waitForElement(driverLicenceInput).getAttribute("value"))) {
            throw new IllegalStateException("El formulario no conservó el número de licencia");
        }
        clickWhenReady(saveButton);
    }

    private void replaceValue(By locator, String value) {
        waitForFormReady();
        WebElement input = waitForElement(locator);
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        input.sendKeys(value);
    }

    public boolean informationSavedSuccessfully() {
        return new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.textToBePresentInElementLocated(successMessage, "Successfully Updated"));
    }

    public boolean licensePersisted(String employeeId, String licenseNumber) {
        // Leer de nuevo desde el servidor evita confundir texto escrito con datos guardados.
        driver.navigate().refresh();
        return new WebDriverWait(driver, Duration.ofSeconds(10)).until(webDriver ->
                employeeId.equals(webDriver.findElement(employeeIdInput).getAttribute("value"))
                        && licenseNumber.equals(webDriver.findElement(driverLicenceInput).getAttribute("value")));
    }
}
