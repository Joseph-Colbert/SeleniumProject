package test;

import conf.BaseTest;
import data.EmployeeData;
import data.EmployeeDataProvider;
import helpers.ReportListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.AddEmployeePage;
import pages.AdditionalInformationPage;
import pages.EmployeeListPage;
import pages.LoginPage;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

// El listener envía a ExtentReports el resultado de cada ejecución del test.
@Listeners(ReportListener.class)
public class PagesTest extends BaseTest {

    /*
     * TEST - FLUJO COMPLETO E2E
     * Comprueba el flujo de negocio de principio a fin:
     *
     * Login
     * - Crear empleado
     * - Validar creación del empleado
     * - Obtener ID generado
     * - Completar información adicional
     * - Validar guardado de información
     * - Buscar empleado en PIM
     * - Validar empleado encontrado
     */
    @Test(dataProvider = "empleados", dataProviderClass = EmployeeDataProvider.class, description = "Crea un empleado, completa sus datos y verifica que aparece en la lista")
    public void testSuccess(EmployeeData employee) {
        LoginPage loginPage = new LoginPage(driver);
        AddEmployeePage addEmployeePage = new AddEmployeePage(driver);
        AdditionalInformationPage additionalInformationPage = new AdditionalInformationPage(driver);
        EmployeeListPage employeeListPage = new EmployeeListPage(driver);
        loginPage.login("Admin", "admin123");

        URL photoResource = PagesTest.class.getClassLoader().getResource("images/test-avatar.png");
        Assert.assertNotNull(photoResource, "No se encontró la foto genérica de prueba");
        Path photo;
        try {
            photo = Path.of(photoResource.toURI());
        } catch (URISyntaxException e) {
            throw new IllegalStateException("No se pudo cargar la foto de prueba", e);
        }

        // Crea un empleado utilizando los datos proporcionados por el DataProvider.
        addEmployeePage.addEmployee(
                employee.firstName(),
                employee.lastName(),
                employee.uniqueUsername(),
                employee.password(),
                photo
        );
        Assert.assertTrue(addEmployeePage.employeeSavedSuccessfully(), "El empleado no fue guardado correctamente");
        Assert.assertTrue(addEmployeePage.photoSavedSuccessfully(), "La foto del empleado no se guardó");
        String employeeId = addEmployeePage.getEmployeeIdValue();
        // Verifica que el ID haya sido capturado y que no esté vacío.
        Assert.assertNotNull(employeeId, "No se capturó el ID del empleado");
        Assert.assertFalse(employeeId.isBlank(), "El ID del empleado está vacío");

        // Completa la información personal adicional del empleado.
        additionalInformationPage.aditionalInformation(
                employeeId,
                employee.licenseNumber(),
                employee.licenseExpiration(),
                employee.nationality(),
                employee.maritalStatus(),
                employee.birthDate(),
                employee.gender()
        );
        Assert.assertTrue(additionalInformationPage.informationSavedSuccessfully(), "La información adicional no fue guardada correctamente");
        Assert.assertTrue(additionalInformationPage.licensePersisted(employeeId, employee.licenseNumber()),
                "La licencia de conducir no quedó guardada en la ficha del empleado");
        Assert.assertTrue(addEmployeePage.photoSavedSuccessfully(),
                "La foto del empleado no persistió después de recargar la ficha");
        Assert.assertTrue(employeeListPage.containsEmployee(employeeId,employee.firstName(), employee.lastName()), "No se encontró en PIM el empleado con ID " + employeeId);
    }
}
