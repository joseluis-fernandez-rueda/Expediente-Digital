package selenium;

/**
 * @author 50863079M
 * 
 * 
 * Implemetado como parte del Trabajo Fin de Estudios 
 * en el Grado de Ingeniería Informática de la UNIR/
 *
 * */

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import static org.junit.jupiter.api.Assertions.*;


class LoginTest {

	 private WebDriver driver;
	    private WebDriverWait wait;

	    @BeforeEach
	    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\selenium\\chromedriver.exe"); // AJUSTA ESTA RUTA
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 30);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testLoginExitoso() {
        driver.get("http://localhost:8080/expedientedigital/index.xhtml"); // AJUSTA ESTA URL

        // Esperar a que aparezca el campo de usuario
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("formLogin:username")));
        WebElement password = driver.findElement(By.id("formLogin:password"));
        WebElement loginButton = driver.findElement(By.id("formLogin:btnEntrar"));

        username.sendKeys("50863079M");
        password.sendKeys("claveCorrecta");
        loginButton.click();

        // Esperamos redirección o contenido visible tras login
        wait.until(ExpectedConditions.urlContains("/gestor/index.xhtml")); // ⚠️ Ajusta a tu ruta real

        // Validación opcional visual
        
        assertTrue(driver.getCurrentUrl().contains("/gestor/index.xhtml"));
    }

    @Test
    public void testLoginFallido() {
        driver.get("http://localhost:8080/expedientedigital/index.xhtml");

        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("formLogin:username")));
        WebElement password = driver.findElement(By.id("formLogin:password"));
        WebElement loginButton = driver.findElement(By.id("formLogin:btnEntrar"));

        username.sendKeys("usuarioIncorrecto");
        password.sendKeys("claveIncorrecta");
        loginButton.click();

     // Esperar que sigamos en la misma página (login fallido)
        wait.until(ExpectedConditions.urlContains("/index.xhtml"));
        assertTrue(driver.getCurrentUrl().contains("/index.xhtml"));

     // Esperar a que el mensaje esté visible en el ul con id formLogin:msj
        WebElement mensajeError = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("formLogin:msj")
        ));

        assertTrue(mensajeError.isDisplayed());
        assertTrue(mensajeError.getText().toLowerCase().contains("error")); // o "credenciales" según el mensaje
    
    }
}
