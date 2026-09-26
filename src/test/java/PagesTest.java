import enums.Gender;
import enums.MaritalStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AddEmployeePage;
import pages.AditionalInformationPage;
import pages.LoginPage;

public class PagesTest extends BaseTest {

    @Test
    public void testSuccess() {

        LoginPage loginPage = new LoginPage(driver);
        AddEmployeePage addEmployeePage = new AddEmployeePage(driver);
        AditionalInformationPage aditionalInformationPage = new AditionalInformationPage(driver);

        loginPage.login("Admin", "admin123");
        addEmployeePage.addEmployee("Juan", "Perez", "juanperez123", "JuanPerez123!");
        Assert.assertTrue(addEmployeePage.employeeSavedSuccessfully(), "El empleado no fue guardado correctamente");

        aditionalInformationPage.aditionalInformation("12345678", "2028-23-09", "Japanese",
                                                        MaritalStatus.SINGLE, "1995-15-06", Gender.MALE);
        Assert.assertTrue(aditionalInformationPage.informationSavedSuccessfully(), "La información adicional no fue guardada correctamente");
    }
}