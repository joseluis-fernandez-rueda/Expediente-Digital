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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SubidaDocumentosTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\selenium\\chromedriver.exe"); // ⚠️ AJUSTA la ruta
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 20);

        // Suponemos que ya estás logado y entras directamente en la página detalle.xhtml
        driver.get("http://localhost:8080/expedientedigital/expediente/detalle.xhtml");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    public void testSubidaExitosaDeDocumentoPDF() {
    	  // 1. Ir al login
        driver.get("http://localhost:8080/expedientedigital/index.xhtml");

        // 2. Hacer login
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("formLogin:username")));
        WebElement password = driver.findElement(By.id("formLogin:password"));
        WebElement loginButton = driver.findElement(By.id("formLogin:btnEntrar"));

        username.sendKeys("50863079M");
        password.sendKeys("claveSegura");  
        loginButton.click();

        // 3. Simular hover en menú y clic en "DNI / NIE / PASAPORTE"
        Actions actions = new Actions(driver);

        WebElement menuPrincipal = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//a[@aria-haspopup='true']") // menú padre
        ));
        actions.moveToElement(menuPrincipal).pause(java.time.Duration.ofMillis(500)).perform();

        // Esperar a que aparezca el submenú
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul.ui-menu-child")));

        WebElement subMenuItem = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//a[@role='menuitem']//span[normalize-space(text())='DNI / NIE / PASAPORTE']")
        ));
        actions.moveToElement(subMenuItem).pause(java.time.Duration.ofMillis(300)).click().perform();

        // 4. Esperar que se cargue detalle.xhtml
        wait.until(ExpectedConditions.urlContains("detalle.xhtml"));
        assertTrue(driver.getCurrentUrl().contains("detalle.xhtml"));

        // 5. Seleccionar archivo válido
        String rutaArchivo = new File("C:/Users/Joseluin/Documents/dni.pdf").getAbsolutePath();
        WebElement inputFile = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("input[type='file'][id$='fuDocumentacion_input']")
        ));
        inputFile.sendKeys(rutaArchivo);

        // 6. Comprobar que NO aparece "Invalid file type"
        List<WebElement> errores = driver.findElements(By.xpath("//*[contains(text(),'Invalid file type')]"));
        assertTrue(errores.isEmpty(), "Apareció mensaje de tipo de archivo inválido");

        // 7. Pulsar botón Upload
        WebElement uploadButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("button.ui-fileupload-upload")
        ));
        uploadButton.click();

     // 8.Esperamos hasta que la fila con el nombre del archivo aparezca
        WebDriverWait wait = new WebDriverWait(driver, 30);
        boolean archivoEncontrado = wait.until(driver -> {
            try {
                List<WebElement> filasActuales = driver.findElements(By.xpath("//tbody[@id='frmDocs:tbl_data']/tr"));
                for (WebElement fila : filasActuales) {
                    List<WebElement> celdas = fila.findElements(By.tagName("td"));
                    if (celdas.size() > 2 && celdas.get(2).getText().contains("DNINIEPASAPORTE_2025-07-06.pdf")) {
                        return true;
                    }
                }
            } catch (StaleElementReferenceException e) {
                // El DOM ha cambiado, volvemos a intentarlo
                return false;
            }
            return false;
        });
        assertTrue(archivoEncontrado, "El archivo no se encuentra en la tabla después de subirlo.");
    }
    

    @Test
    public void testSubidaArchivoFormatoIncorrecto() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        driver.get("http://localhost:8080/expedientedigital/index.xhtml"); // Ajusta la URL base

        // 1. Login
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("formLogin:username")));
        WebElement password = driver.findElement(By.id("formLogin:password"));
        WebElement loginButton = driver.findElement(By.id("formLogin:btnEntrar"));

        username.sendKeys("50863079M");
        password.sendKeys("claveSegura");  
        loginButton.click();

        // 2. Buscar empleado
        WebElement campoBusqueda = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("frmBuscar:empleado")));
        campoBusqueda.sendKeys("50863079M");
        driver.findElement(By.id("frmBuscar:btnBuscar")).click();

        // 3. Seleccionar empleado (clic en el icono de la tabla)
        // Esperar a que se cargue la fila de empleados
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tbody[@id='tbl_data']//tr")));
        WebElement enlaceDetalle = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, 'detalle.xhtml') and contains(text(),'50863079M')]")));
        enlaceDetalle.click();

        // 4. Seleccionar categoría en el menú
/*        WebElement menu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("formDocs:menu")));
        Actions actions = new Actions(driver);
        actions.moveToElement(menu).perform();

        WebElement opcionCategoria = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[@role='menuitem']//span[contains(text(), 'DNI / NIE / PASAPORTE')]")));
        opcionCategoria.click();
*/
        
        
      //  WebDriverWait wait = new WebDriverWait(driver, 30);
        Actions actions = new Actions(driver);

     // 1. Esperar a que el menú esté visible
        WebElement menuDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[id$='menu']") // busca cualquier id que termine en 'menu'
        ));

        // 2. Hover sobre el primer <a> para que se despliegue el submenú
        List<WebElement> items = menuDiv.findElements(By.cssSelector("ul[role='menubar'] > li.ui-menu-parent"));

        WebElement datosPersonalesItem = null;
        for (WebElement item : items) {
            if (item.getText().contains("DATOS PERSONALES")) {
                datosPersonalesItem = item;
                break;
            }
        }

        assertNotNull(datosPersonalesItem, "No se encontró el menú 'DATOS PERSONALES'");


        // 3. Hacer hover sobre el <a> dentro de ese <li>
        WebElement anchor = datosPersonalesItem.findElement(By.tagName("a"));
        actions.moveToElement(anchor).perform();

        // 4. Esperar a que aparezca y hacer clic en el submenú "DNI / NIE / PASAPORTE"
        WebElement opcionSubmenu = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a/span[text()='DNI / NIE / PASAPORTE']")
        ));
        opcionSubmenu.click();
        
        
        // 5. Seleccionar archivo con formato inválido
        WebElement inputArchivo = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("input[type='file'][id$='fuDocumentacion_input']")));
        inputArchivo.sendKeys("C:\\Users\\Joseluin\\Documents\\notas.txt");

        // 6. Verificar mensaje "Invalid file type"
        WebElement mensaje = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//*[contains(text(), 'Invalid file type')]")));
        assertTrue(mensaje.isDisplayed());

        // 7. Verificar que el archivo NO se muestra en la tabla
        List<WebElement> filas = driver.findElements(By.xpath("//tbody[@id='frmDocs:tbl_data']/tr"));
        boolean encontrado = false;
        for (WebElement fila : filas) {
            List<WebElement> celdas = fila.findElements(By.tagName("td"));
            if (celdas.size() > 2 && celdas.get(2).getText().contains("malicioso.exe")) {
                encontrado = true;
                break;
            }
        }
        assertFalse(encontrado, "El archivo con formato inválido fue subido, pero no debería.");
    }

    
}


