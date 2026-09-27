package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/** Acciones de la lista PIM para localizar un empleado ya creado. */
public class EmployeeListPage extends BasePage {

    private final By pimMenu = By.cssSelector("a[href='/web/index.php/pim/viewPimModule']");
    private final By employeeIdInput = By.xpath("//label[normalize-space()='Employee Id']/following::input[1]");
    private final By searchButton = By.xpath("//button[@type='submit' and normalize-space()='Search']");
    private final By resultRows = By.cssSelector(".oxd-table-body .oxd-table-card");

    public EmployeeListPage(WebDriver driver) {
        super(driver);
    }

    public boolean containsEmployee(String employeeId, String firstName, String lastName) {
        waitForElementToBeClickable(pimMenu).click();
        WebElement idInput = waitForElement(employeeIdInput);
        idInput.clear();
        idInput.sendKeys(employeeId);
        waitForElementToBeClickable(searchButton).click();

        // La tabla carga después de la búsqueda; esperamos una fila con los tres datos.
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(webDriver -> {
            List<WebElement> rows = webDriver.findElements(resultRows);
            for (WebElement row : rows) {
                List<String> cells = row.findElements(By.cssSelector("[role='cell']"))
                        .stream().map(WebElement::getText).map(String::trim).toList();
                if (cells.contains(employeeId) && cells.contains(firstName) && cells.contains(lastName)) {
                    return true;
                }
            }
            return null;
        });
    }
}
