package com.lacuevita305.adPublisher.utils;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.Optional;
import java.util.Random;


public class UtilsHumanActions {

    private static final Random random = new Random();

    //  Pausa aleatoria (simula tiempo humano)
    public static void humanPause(int minMs, int maxMs) {
        try {
            int waitTime = minMs + random.nextInt(maxMs - minMs);
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // 🔹 Scroll natural hacia el elemento
    public static void humanScroll(WebDriver driver, WebElement element) {
        humanPause(1200, 3000);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
                element
        );
        humanPause(1200, 3000);// simula "leer"
    }

    public static void scrollToBottomHumanLike(WebDriver driver){
         JavascriptExecutor js = (JavascriptExecutor) driver;

         long lastHeight = (long) js.executeScript("return document.body.scrollHeight");
         long currentPosition = 0;

         while (true) {
             // Avance entre 400 y 800 píxeles
             int scrollStep = 400 + random.nextInt(400);

             currentPosition += scrollStep;
             js.executeScript("window.scrollTo(0, arguments[0]);", currentPosition);

             // Pausa aleatoria (0.3 a 1.2 segundos)
             humanPause(700, 1200);

             long newHeight = (long) js.executeScript("return document.body.scrollHeight");

             // Si la página creció (cargó nuevo contenido), sigue
             if (newHeight > lastHeight) {
                 lastHeight = newHeight;
             }

             // Si ya llegamos al final (no cambia la altura)
             else if (currentPosition + 800 >= lastHeight) {
                 break;
             }
         }

         // Pequeño movimiento final
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        // Pausa aleatoria (0.3 a 1.2 segundos)
        humanPause(1000, 1500);
    }

    // 🔹 Click con movimiento del mouse + pausa
    public static void humanClickElement(WebDriver driver, WebElement element) {
        humanPause(1500, 2500);
        Actions actions = new Actions(driver);

        // Mueve el mouse al botón con una pausa antes de clicar
        actions.moveToElement(element)
                .pause(Duration.ofMillis(500 + random.nextInt(1000)))
                .click()
                .perform();

        // Pausa después del clic (como si esperaras ver resultado)
        humanPause(2000, 3000);
    }

    public static void closeGoogleVignetteIfPresent(WebDriver driver) {

        // Revisa todas las pestañas abiertas
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
            String url = driver.getCurrentUrl();
            System.out.println("Identificador de ventana: " + handle + " URL: " + url);

            if (url != null && url.contains("google_vignette")) {
                driver.navigate().back();
                // Espera humana opcional
                humanPause(1000, 2000);
            }
        }
    }

    // 🔹 Scroll + Click de forma natural
    public static void humanScrollAndClick(WebDriver driver, WebElement element) {
        humanScroll(driver, element);
        humanPause(1500, 2500);
        humanClickElement(driver, element);
        humanPause(1500, 2500);
    }


    public static boolean openLinkInNewWindowAsHuman(WebDriver driver, String url) {
        UtilsHumanActions.humanPause(3000, 5000);
        boolean wasOpened = Utils.openLinkInNewWindow(driver,url);
        UtilsHumanActions.humanPause(3000, 5000);
        return wasOpened;
    }

    public static Optional<WebElement> waitForElementByTextAsHuman (WebDriver driver, String elementType, String clases, int timeoutSegundos) {
        UtilsHumanActions.humanPause(2000, 3000);
        Optional<WebElement> modalCloseButton = Utils.waitForElementByText(driver, elementType, clases, timeoutSegundos);
        UtilsHumanActions.humanPause(2000, 3000);
        return modalCloseButton;

    }

    public static Optional<WebElement> waitForElementByIDAsHuman (WebDriver driver, String id, int timeoutSegundos) {
        UtilsHumanActions.humanPause(2000, 3000);
        Optional<WebElement> element = Utils.waitForElementByID(driver, id, timeoutSegundos);
        UtilsHumanActions.humanPause(2000, 3000);
        return element;
    }

    public static Optional<WebElement> waitForElementByCssSelector (WebDriver driver, String elementType, int timeoutSegundos) {
        UtilsHumanActions.humanPause(2000, 3000);
        Optional<WebElement> modalCloseButton = Utils.waitForElementByCssSelector(driver, elementType, timeoutSegundos);
        UtilsHumanActions.humanPause(2000, 3000);
        return modalCloseButton;
    }

}
