package pages;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage{
    protected WebDriver driver;

    public HomePage(WebDriver driver, ExtentTest extentTest){
        super(driver, extentTest);
        this.driver = driver;
    }

    @FindBy(id = "php-travels")
    public WebElement phpLink;
}
