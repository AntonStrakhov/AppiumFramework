package pageObject;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class FormPage {

    public FormPage(AppiumDriver<MobileElement> driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy(id="com.androidsample.generalstore:id/nameField")
    private MobileElement nameField;

    @AndroidFindBy(xpath="//*[@text='Female']")
    public MobileElement femaleOption;

    @AndroidFindBy(id="android:id/text1")
    private MobileElement countrySelection;

    public MobileElement getNameField() {
        System.out.println("Trying to find the Name field");
        return nameField;
    }

    public MobileElement getCountrySelection() {
        System.out.println("Selecting the option from dropdown");
        return countrySelection;
    }
}
