package helpers;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/** Guarda una evidencia PNG del navegador cuando falla un caso. */
public final class ScreenshotHelper {

    private static final Path SCREENSHOT_DIR = Path.of("src", "test", "resources", "logs", "screenshots");
    private static final DateTimeFormatter TIMESTAMP = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private ScreenshotHelper() {
    }

    public static Path capture(WebDriver driver, String testName) throws IOException {
        Files.createDirectories(SCREENSHOT_DIR);
        // La fecha y el sufijo aleatorio evitan sobrescribir capturas anteriores.
        String safeName = testName.replaceAll("[^a-zA-Z0-9_-]", "_");
        String fileName = safeName + "_" + LocalDateTime.now().format(TIMESTAMP)
                + "_" + UUID.randomUUID().toString().substring(0, 8) + ".png";
        Path screenshot = SCREENSHOT_DIR.resolve(fileName);
        Files.write(screenshot, ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
        return screenshot;
    }
}
