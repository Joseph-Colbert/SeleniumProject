import data.EmployeeData;
import data.EmployeeDataProvider;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.AddEmployeePage;
import pages.AdditionalInformationPage;
import pages.EmployeeListPage;
import pages.LoginPage;

import java.util.UUID;

// El listener envía a ExtentReports el resultado de cada ejecución del test.
@Listeners(ReportListener.class)
public class PagesTest extends BaseTest {

    /*
     * TEST 1 - LOGIN
     * Verifica que el usuario pueda iniciar sesión.
     */
    @Test(description = "Verifica que el administrador pueda iniciar sesión correctamente")
    public void loginSuccessful() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");
        Assert.assertTrue(
                loginPage.isLoginSuccessful(),
                "El inicio de sesión no fue exitoso"
        );
    }

    /*
     * TEST 2 - FLUJO COMPLETO E2E
     *
     * Conservamos el test original para comprobar
     * el flujo de negocio de principio a fin.
     *
     * Login
     * -> Crear empleado
     * -> Completar información adicional
     * -> Buscar empleado
     * -> Validar empleado
     */
    @Test(dataProvider = "empleados", dataProviderClass = EmployeeDataProvider.class, description = "Crea un empleado, completa sus datos y verifica que aparece en la lista")
    public void testSuccess(EmployeeData employee) {
        LoginPage loginPage = new LoginPage(driver);
        AddEmployeePage addEmployeePage = new AddEmployeePage(driver);
        AdditionalInformationPage additionalInformationPage = new AdditionalInformationPage(driver);
        EmployeeListPage employeeListPage = new EmployeeListPage(driver);

        loginPage.login("Admin", "admin123");
         // Evita reutilizar un nombre de usuario
        String uniqueUsername = employee.usernamePrefix()
                + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 8);

        addEmployeePage.addEmployee(
                employee.firstName(),
                employee.lastName(),
                uniqueUsername,
                employee.password()
        );
        Assert.assertTrue(addEmployeePage.employeeSavedSuccessfully(), "El empleado no fue guardado correctamente");
        String employeeId = addEmployeePage.getEmployeeIdValue();
        Assert.assertNotNull(employeeId, "No se capturó el ID del empleado");
        Assert.assertFalse(employeeId.isBlank(), "El ID del empleado está vacío");

        additionalInformationPage.aditionalInformation(
                employee.licenseNumber(),
                employee.licenseExpiration(),
                employee.nationality(),
                employee.maritalStatus(),
                employee.birthDate(),
                employee.gender()
        );

        Assert.assertTrue(additionalInformationPage.informationSavedSuccessfully(), "La información adicional no fue guardada correctamente");
        Assert.assertTrue(employeeListPage.containsEmployee(employeeId, employee.firstName(), employee.lastName()), "No se encontró en PIM el empleado con ID " + employeeId);
    }
}