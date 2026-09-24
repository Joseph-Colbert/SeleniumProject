package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AditionalInformationPage extends BasePage {

    private final By driverLicenceInput =
            By.xpath("//label[contains(normalize-space(),\"Driver's License Number\")]/following::input[1]");
    private final By expirationDateInput = By.xpath("//label[normalize-space()='License Expiry Date']/following::input[1]");;
    private final By nationalitySelect =
            By.xpath("//label[normalize-space()='Nationality']/following::div[contains(@class,'oxd-select-text')][1]");
    private final By maritalStatusSelect =
            By.xpath("//label[normalize-space()='Marital Status']/following::div[contains(@class,'oxd-select-text')][1]");
    private final By birthInput =
            By.xpath("//label[normalize-space()='Date of Birth']/following::input[1]");
    private final By genderMaleRadio = By.xpath("//label[contains(normalize-space(.),'Male')]");
    private final By saveButton = By.className("oxd-button--secondary");

    public AditionalInformationPage(WebDriver driver) {
        super(driver);
    }

    public void aditionalInformation(String driverLicenceNumber, String expirationDate, String nationality, String maritalStatus, String birth) throws InterruptedException {
        waitForElement(driverLicenceInput).sendKeys(driverLicenceNumber);
        driver.findElement(expirationDateInput).sendKeys(expirationDate);
        driver.findElement(nationalitySelect).click();
        driver.findElement(By.xpath("//div[@role='option']//span[normalize-space()='" + nationality + "']")).click();
        driver.findElement(maritalStatusSelect).click();
        driver.findElement(By.xpath("//div[@role='option']//span[normalize-space()='" + maritalStatus + "']")).click();
        driver.findElement(birthInput).sendKeys(birth);
        driver.findElement(genderMaleRadio).click();
        driver.findElement(saveButton).click();
    }
}