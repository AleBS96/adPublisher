package com.lacuevita305.adPublisher.utils;


import com.lacuevita305.adPublisher.enums.WebElementsEnum;
import com.lacuevita305.adPublisher.persistence.entities.Ad;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.Optional;

public class UtilsRevolico {

    public static boolean publishAd(WebDriver webDriver, Ad ad, String revolicoCategory) throws IOException {
        Optional<WebElement> priceInput;
        priceInput = UtilsHumanActions.waitForElementByIDAsHuman(webDriver,WebElementsEnum.PRICE_INPUT_ID.getValue(),10);
        Optional<WebElement> imageInput;
        imageInput = UtilsHumanActions.waitForElementByCssSelector(webDriver,WebElementsEnum.INPUT.getValue(),10);

        if(imageInput.isPresent()){
            String imagePath = Utils.getRandomImagePath(ad.getProductName());
            imageInput.get().sendKeys(imagePath);
        }
        else {
            return false;
        }

        if(priceInput.isPresent()){
            priceInput.get().sendKeys(ad.getPrice().toString());
        }
        else {
            return false;
        }
        Optional<WebElement> tittleInput;
        tittleInput = UtilsHumanActions.waitForElementByIDAsHuman(webDriver,WebElementsEnum.TITLE_INPUT_ID.getValue(),10);

        if(tittleInput.isPresent()){
            tittleInput.get().sendKeys(ad.getTitle());
        }
        else {
            return false;
        }

        Optional<WebElement> bodyInput;
        bodyInput = UtilsHumanActions.waitForElementByIDAsHuman(webDriver,WebElementsEnum.BODY_INPUT_ID.getValue(),10);

        if(bodyInput.isPresent()){
            String[] lineas = ad.getBody().split("\\\\n");

            for (String linea : lineas) {
                bodyInput.get().sendKeys(linea);
                bodyInput.get().sendKeys(Keys.ENTER);
            }
        }
        else {
            return false;
        }


        Optional<WebElement> categorySelector;
        categorySelector = UtilsHumanActions.waitForElementByIDAsHuman(webDriver,WebElementsEnum.CATEGORY_SELECTOR_ID.getValue(),10);
        if(categorySelector.isPresent()){
            UtilsHumanActions.humanScrollAndClick(webDriver,categorySelector.get());

            Optional<WebElement> categoryButton;
            categoryButton = UtilsHumanActions.waitForElementByTextAsHuman(webDriver,WebElementsEnum.BUTTON.getValue(),"Aceptar",10);
            if(categoryButton.isPresent()){
                UtilsHumanActions.humanScrollAndClick(webDriver,categoryButton.get());
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }

        Optional<WebElement> publishButton;
        publishButton = UtilsHumanActions.waitForElementByTextAsHuman(webDriver,WebElementsEnum.BUTTON.getValue(),WebElementsEnum.PUBLISH_BUTTON_TEXT.getValue(),10);
        if(publishButton.isPresent()){
            UtilsHumanActions.humanScrollAndClick(webDriver,publishButton.get());
        }
        else {
            return false;
        }
      return true;
    }


}
