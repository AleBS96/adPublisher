package com.lacuevita305.adPublisher.utils;

import com.lacuevita305.adPublisher.enums.GeneralUseEnum;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.*;


public class Utils {
    public static WebDriver initWebDriver(Integer port, String name) throws Exception {

        // Si no hay Chrome corriendo en ese puerto, ejecuto el BAT
        if (!Utils.isPortInUse(port)) {
            UtilsBash.ejecutarBat("Chrome.bat", name, port.toString());
            // Espera breve para que Chrome se inicie correctamente
            Thread.sleep(5000);
        }

        // En ambos casos (nuevo o existente), conecto el WebDriver
        return Utils.createWebDriver(port);
    }

    public static WebDriver createWebDriver(Integer port){
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:"+port.toString());
        return new ChromeDriver(options);
    }

    public static boolean isPortInUse(Integer port) {
        try (Socket socket = new Socket("127.0.0.1", port)) {
            return true; // se pudo conectar => puerto ocupado (Chrome ya abierto)
        } catch (IOException e) {
            return false; // no se pudo conectar => Chrome no está corriendo
        }
    }

    public static boolean openLinkInNewWindow(WebDriver driver, String url) {
        try {
            driver.get(url);

            // Espera hasta que la página termine de cargarse
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(webDriver -> ((JavascriptExecutor) webDriver)
                            .executeScript("return document.readyState").equals("complete"));
            UtilsHumanActions.closeGoogleVignetteIfPresent(driver);

            return true;

        } catch (Exception e) {
            throw e;
        }
    }

    /// FUNCION PARA ESPERAR A QUE APAREZCA EN EL DOM EL ELEMENTO
    /// QUE SE BUSCA POR SU TEXTO VISIBLE
    /// IGNORA ELEMENTOS OCULTOS O CLONADOS
    public static Optional<WebElement> waitForElementByText(
            WebDriver driver,
            String elementType,
            String text,
            int timeoutSegundos
    ) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSegundos));

            WebElement elemento = wait.until(d -> {
                String xpath = "//" + elementType + "[contains(., '" + text + "')]";

                List<WebElement> elementos = d.findElements(By.xpath(xpath));

                return elementos.stream()
                        .filter(WebElement::isDisplayed)
                        .filter(e -> !e.getText().trim().isEmpty())
                        .findFirst()
                        .orElse(null);
            });

            Optional<WebElement> resultado = Optional.ofNullable(elemento);
            debugElemento(resultado);
            System.out.println("Encontrado el botón: " + text);
            return resultado;

        } catch (TimeoutException e) {
            System.err.println("No se encontró el botón visible: " + text);
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /// FUNCION PARA ESPERAR A QUE APAREZCA EN EL DOM EL ELEMENTO
    /// QUE SE BUSCA POR SU TEXTO VISIBLE
    /// IGNORA ELEMENTOS OCULTOS O CLONADOS
    public static Optional<WebElement> waitForElementByCssSelector(
            WebDriver driver,
            String cssSelector,
            int timeoutSegundos
    ) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSegundos));

            WebElement fileInput = wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector + "[type='file']"))
            );

            Optional<WebElement> resultado = Optional.ofNullable(fileInput);
            debugElemento(resultado);
            System.out.println("Encontrado el input");
            return resultado;

        } catch (TimeoutException e) {
            System.err.println("No se encontró el input visible" + cssSelector);
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /// FUNCION PARA ESPERAR A QUE APAREZCA EN EL DOM EL ELEMENTO
    /// QUE SE BUSCA POR SU ID
    /// IGNORA ELEMENTOS OCULTOS O CLONADOS
    public static Optional<WebElement> waitForElementByID(
            WebDriver driver,
            String id,
            int timeoutSegundos
    ) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSegundos));

            WebElement elemento = wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));

            Optional<WebElement> resultado = Optional.ofNullable(elemento);
            debugElemento(resultado);
            System.out.println("Encontrado el elemento: " + id);
            return resultado;

        } catch (TimeoutException e) {
            System.err.println("No se encontró el botón visible: " + id);
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static void debugElemento(Optional<WebElement> optEl) {
        if (optEl.isPresent()) {
            WebElement el = optEl.get();
            System.out.println("=== DEBUG ELEMENTO ===");
            System.out.println("OuterHTML: " + el.getAttribute("outerHTML"));
            System.out.println("Texto: " + el.getText());
            System.out.println("Clase: " + el.getAttribute("class"));
            System.out.println("======================");
        } else {
            System.out.println("Elemento no presente");
        }
    }

    public static String getRandomImagePath(String product) throws IOException {

        Path path = Paths.get(GeneralUseEnum.RESOURCES_FOLDER_PATH.getValue());
        // Ruta relativa desde donde ejecutas el proyecto
        File folder = new File(path.toRealPath().toString() + "/" + product);

        if (!folder.exists() || !folder.isDirectory()) {
            throw new RuntimeException("La carpeta no existe: " + folder.getAbsolutePath());
        }

        File[] files = folder.listFiles((dir, name) ->
                name.toLowerCase().endsWith(".jpg") ||
                        name.toLowerCase().endsWith(".png") ||
                        name.toLowerCase().endsWith(".jpeg")
        );

        if (files == null || files.length == 0) {
            throw new RuntimeException("No hay imágenes en la carpeta");
        }

        Random random = new Random();
        File selected = files[random.nextInt(files.length)];

        return selected.getAbsolutePath();
    }
}
