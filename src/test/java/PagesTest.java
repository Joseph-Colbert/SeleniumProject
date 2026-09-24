import org.testng.annotations.Test;
import pages.AddEmployeePage;
import pages.AditionalInformationPage;
import pages.LoginPage;

public class PagesTest extends BaseTest {

    @Test
    public void testSuccess() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        AddEmployeePage addEmployeePage = new AddEmployeePage(driver);
        AditionalInformationPage aditionalInformationPage = new AditionalInformationPage(driver);
        loginPage.login("Admin", "admin123");
        addEmployeePage.addEmployee(
                "Juan",
                "Perez",
                "juanperez",
                "123juanperez"
        );
        aditionalInformationPage.aditionalInformation("30345875", "2028-04-05",
                                            "Japanese", "Single", "1995-03-08");

    }
}