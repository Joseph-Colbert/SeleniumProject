package helpers;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

/** Administra el reporte HTML y la entrada que corresponde al test actual. */
public final class ReportManager {

    private static ExtentReports report;
    // Cada hilo conserva su propia entrada para no mezclar resultados.
    private static final ThreadLocal<ExtentTest> currentTest = new ThreadLocal<>();

    private ReportManager() {
    }

    public static synchronized void startReport() {
        if (report == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("target/reports/ExtentReport.html");
            spark.config().setDocumentTitle("Pruebas de empleados - OrangeHRM");
            spark.config().setReportName("Automatización de empleados");
            report = new ExtentReports();
            report.attachReporter(spark);
        }
    }

    public static synchronized void startTest(String name, String description) {
        currentTest.set(report.createTest(name, description));
    }

    public static ExtentTest currentTest() {
        return currentTest.get();
    }

    public static void clearTest() {
        currentTest.remove();
    }

    public static synchronized void finishReport() {
        if (report != null) {
            // ExtentReports no escribe el HTML completo hasta llamar a flush.
            report.flush();
            report = null;
        }
    }
}
