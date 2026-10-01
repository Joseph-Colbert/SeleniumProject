package pages;

import enums.Gender;
import enums.MaritalStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdditionalInformationPage extends BasePage {

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
    //Por Firefox
    private final By formLoader =
            By.className("oxd-form-loader");

    public AdditionalInformationPage(WebDriver driver) {
        super(driver);
    }

    private By optionByText(String option) {
        return By.xpath("//div[@role='option']//span[normalize-space()='" + option + "']");
    }

    private void selectOption(By dropdown, String option) {
        waitForElementToDisappear(formLoader);
        waitForElementToBeClickable(dropdown).click();
        waitForElementToBeClickable(optionByText(option)).click();
    }

    private void selectGender(Gender gender) {
        if (gender == Gender.MALE) {
            waitForElementToBeClickable(genderMaleRadio).click();
        } else if (gender == Gender.FEMALE) {
            waitForElementToBeClickable(genderFemaleRadio).click();
        }
    }

    public void aditionalInformation(String driverLicenceNumber, String expirationDate, String nationality, MaritalStatus maritalStatus, String dateOfBirth, Gender gender) {
        waitForElement(driverLicenceInput).sendKeys(driverLicenceNumber);
        waitForElement(expirationDateInput).sendKeys(expirationDate);
        selectOption(nationalitySelect, nationality);
        selectOption(maritalStatusSelect, maritalStatus.toString());
        waitForElement(dateOfBirthInput).sendKeys(dateOfBirth);
        selectGender(gender);
        waitForElementToBeClickable(saveButton).click();
    }

    public boolean informationSavedSuccessfully() {
        return isElementDisplayed(successMessage);
    }
}