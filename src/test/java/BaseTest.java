import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    protected WebDriver driver;

    // Cambiar entre "chrome" y "firefox"
    private final String browser = "chrome";
    private final String url = "https://opensource-demo.orangehrmlive.com/";

    @BeforeMethod
    public void setUp() throws Exception {

        switch (browser) {

            case "chrome":
                driver = new ChromeDriver(
                        chromeSinGestorDeContrasenas()
                );
                break;

            case "firefox":
                driver = new FirefoxDriver();
                break;

            default:
                throw new Exception(
                        browser + " not supported"
                );
        }

        driver.get(url);
        driver.manage().window().maximize();
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
/*
    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }*/
}