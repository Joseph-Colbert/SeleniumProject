package conf;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    protected WebDriver driver;
    private final String url = "https://opensource-demo.orangehrmlive.com/";

    @BeforeMethod
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser) throws Exception {
        System.out.println("NAVEGADOR RECIBIDO: " + browser);
        switch (browser.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver(chromeSinGestorDeContrasenas());
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            default: throw new Exception(browser + " no soportado");
        }
        driver.manage().window().maximize();
        driver.get(url);
    }

    /*
     * Permite que ReportListener obtenga el driver
     * para realizar capturas de pantalla cuando
     * una prueba falla.
     */
    public WebDriver getDriver() {
        return driver;
    }

    private ChromeOptions chromeSinGestorDeContrasenas() {
        Map<String, Object> preferencias = new HashMap<>();
        preferencias.put("credentials_enable_service", false);
        preferencias.put("profile.password_manager_enabled", false);
        preferencias.put("profile.password_manager_leak_detection", false);
        ChromeOptions opciones = new ChromeOptions();
        opciones.setExperimentalOption("prefs", preferencias);
        opciones.addArguments("--disable-features=PasswordLeakDetection,AutofillServerCommunication");
        return opciones;
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}