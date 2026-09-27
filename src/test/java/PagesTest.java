import data.EmployeeData;
import data.EmployeeDataProvider;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.AddEmployeePage;
import pages.AditionalInformationPage;
import pages.EmployeeListPage;
import pages.LoginPage;

import java.util.UUID;

// El listener envía a ExtentReports el resultado de cada ejecución del test.
@Listeners(ReportListener.class)
public class PagesTest extends BaseTest {

    // TestNG ejecuta este método una vez por cada empleado del DataProvider.
    @Test(dataProvider = "empleados", dataProviderClass = EmployeeDataProvider.class,
            description = "Crea un empleado, completa sus datos y verifica que aparece en la lista")
    public void testSuccess(EmployeeData employee) {

        LoginPage loginPage = new LoginPage(driver);
        AddEmployeePage addEmployeePage = new AddEmployeePage(driver);
        AditionalInformationPage aditionalInformationPage = new AditionalInformationPage(driver);
        EmployeeListPage employeeListPage = new EmployeeListPage(driver);

        loginPage.login("Admin", "admin123");
        // El sufijo evita reutilizar un nombre de usuario existente en la demo.
        String uniqueUsername = employee.usernamePrefix()
                + UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        addEmployeePage.addEmployee(employee.firstName(), employee.lastName(),
                uniqueUsername, employee.password());
        Assert.assertTrue(addEmployeePage.employeeSavedSuccessfully(), "El empleado no fue guardado correctamente");
        String employeeId = addEmployeePage.getEmployeeIdValue();
        Assert.assertNotNull(employeeId, "No se capturó el ID del empleado");
        Assert.assertFalse(employeeId.isBlank(), "El ID del empleado está vacío");

        // Guardamos los datos personales y comprobamos la confirmación de la página.
        aditionalInformationPage.aditionalInformation(employee.licenseNumber(),
                employee.licenseExpiration(), employee.nationality(), employee.maritalStatus(),
                employee.birthDate(), employee.gender());
        Assert.assertTrue(aditionalInformationPage.informationSavedSuccessfully(), "La información adicional no fue guardada correctamente");
        // Buscamos el mismo ID y validamos que la fila corresponda a este empleado.
        Assert.assertTrue(employeeListPage.containsEmployee(employeeId, employee.firstName(), employee.lastName()),
                "No se encontró en PIM el empleado con ID " + employeeId);
    }
}
