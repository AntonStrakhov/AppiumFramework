package org.example;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObject.CheckOutPage;
import pageObject.FormPage;


import java.io.IOException;

import java.util.concurrent.TimeUnit;

public class EcommerceTC4 extends Base {

    @Test
    public void totalValidation() throws IOException, InterruptedException {

        service = startServer();
        AndroidDriver<MobileElement> driver = capabilities("GeneralStoreApp");
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        FormPage formPage = new FormPage(driver);
        //driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("Hello");
        //formPage.nameField.sendKeys("Hello");
        formPage.getNameField().sendKeys("Hello");
        driver.hideKeyboard();
        formPage.femaleOption.click();
        formPage.getCountrySelection().click();
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Argentina\"));");
        // if the previous is not work use below
        // driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textMatches(\"" + containedText + "\").instance(0))"));
        Utilities u = new Utilities(driver);
        u.scrollToText("Argentina");
        //driver.findElement(By.xpath("//*[@text='Argentina']")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();

        driver.findElements(By.xpath("//*[@text='ADD TO CART']")).get(0).click();
        driver.findElements(By.xpath("//*[@text='ADD TO CART']")).get(0).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();

        int count = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice")).size();

        double sum = 0;

        CheckOutPage checkOutPage = new CheckOutPage(driver);
        for (int i = 0; i < count; i++) {
            String amount1 = checkOutPage.productList.get(i).getText();
            double amount = getAmount(amount1);
            sum = sum + amount;//280.97+116.97
        }
        System.out.println(sum + "sum of products");
        String total = checkOutPage.totalAmount.getText();
        total = total.substring(1);
        double totalValue = Double.parseDouble(total);
        System.out.println(totalValue + "Total value of products");
        Assert.assertEquals(sum, totalValue);
        service.stop();
    }
    @BeforeTest
    public  void killAllNodes() throws IOException, InterruptedException {
        Runtime.getRuntime().exec("taskkill /F /IM node.exe");
        Thread.sleep(3000);
    }

    public static double getAmount(String value) {
        value = value.substring(1);
        double amount2value = Double.parseDouble(value);
        return amount2value;
    }
}