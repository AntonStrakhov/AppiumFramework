package org.example;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Properties;

public class Base {

    public static AppiumDriverLocalService service;
    public static AndroidDriver<MobileElement> driver;

    public AppiumDriverLocalService startServer() {
        boolean flag = checkIfServerIsRunning(4723);
        if (!flag) {
            //service = AppiumDriverLocalService.buildDefaultService();
            service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder().withArgument(() -> "--base-path", "/wd/hub"));
            service.start();
        }
        return service;
    }

    public static boolean checkIfServerIsRunning(int port) {
        boolean isServerRunning = false;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.close();
        } catch (IOException e) {
            isServerRunning = true;
        } finally {
            serverSocket = null;
        }
        return isServerRunning;
    }

    public static void startEmulator() throws IOException, InterruptedException {
        Runtime.getRuntime().exec(System.getProperty("user.dir") + "\\src\\main\\java\\resources\\startEmulator.bat");
        Thread.sleep(30000);
    }

    public static AndroidDriver<MobileElement> capabilities(String appName) throws IOException, InterruptedException {

        FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\org\\example\\global.properties");
        Properties prop = new Properties();
        prop.load(file);

        File appDir = new File("src");
        File app = new File(appDir, (String) prop.get(appName));

        DesiredCapabilities capabilities = new DesiredCapabilities();
        String device = (String) prop.get("device");
        //String device = System.getProperty("deviceName");

        if (device.contains("ANSTR50")) {
            startEmulator();
        }

        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");//new step
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        return driver;
    }

    public static void getScreenshot(String s) throws IOException {
        File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile, new File (System.getProperty("user.dir") + "\\" +s + ".png"));
    }
}