package helpers;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import conf.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Recibe los eventos de TestNG y los convierte en entradas del reporte HTML. */
public class ReportListener implements ITestListener {

    // Se crea un reporte para toda la ejecución de la suite.
    @Override
    public void onStart(ITestContext context) {
        ReportManager.startReport();
    }

    @Override
    public void onTestStart(ITestResult result) {
        // El nombre incluye al empleado para distinguir las ejecuciones.
        String parameters = Stream.of(result.getParameters())
                .map(String::valueOf).collect(Collectors.joining(", "));
        String name = result.getMethod().getMethodName() + " (" + parameters + ")";
        ReportManager.startTest(name, result.getMethod().getDescription());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        currentOrCreate(result).pass("Empleado creado, datos adicionales guardados y registro encontrado por ID.");
        ReportManager.clearTest();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = currentOrCreate(result);
        test.fail(result.getThrowable());

        Object instance = result.getInstance();
        if (instance instanceof BaseTest baseTest) {
            WebDriver driver = baseTest.getDriver();
            if (driver != null) {
                try {
                    // Capturamos antes de que @AfterMethod cierre el navegador.
                    Path image = ScreenshotHelper.capture(driver, result.getMethod().getMethodName());
                    // La imagen se incrusta en el HTML para poder abrir el reporte por separado.
                    String base64 = Base64.getEncoder().encodeToString(Files.readAllBytes(image));
                    test.fail("Captura: " + image.toAbsolutePath(),
                            MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
                } catch (Exception screenshotError) {
                    test.warning("No se pudo capturar la pantalla: " + screenshotError.getMessage());
                }
            }
        }
        ReportManager.clearTest();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest test = currentOrCreate(result);
        test.skip(result.getThrowable());
        ReportManager.clearTest();
    }

    @Override
    public void onFinish(ITestContext context) {
        // flush escribe en disco los resultados acumulados.
        ReportManager.finishReport();
    }

    private ExtentTest currentOrCreate(ITestResult result) {
        if (ReportManager.currentTest() == null) {
            onTestStart(result);
        }
        return ReportManager.currentTest();
    }
}
