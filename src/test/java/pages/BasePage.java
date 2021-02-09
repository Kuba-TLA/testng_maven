package pages;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.Screenshot;

public class BasePage {
    protected WebDriver driver;
    private ExtentTest extentTest;

    public BasePage(WebDriver driver, ExtentTest extentTest){
        this.driver = driver;
        this.extentTest = extentTest;
        PageFactory.initElements(driver, this);
    }

    public void click(WebElement element){
        highlightElement(element);
        element.click();
    }

    public void sleep(long milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void moveIntoView(WebElement element){
        try{
            ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", element);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void highlightElement(WebElement element){
        JavascriptExecutor js = (JavascriptExecutor)driver;
        moveIntoView(element);
        for (int i = 0; i < 2; i++){
            try{
                if (i == 0){
                    js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: black; border: 3px solid red; background: yellow");
                }else {
                    sleep(500);
                    js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void highlightElementAndTakeScreenshot(WebElement element){
        JavascriptExecutor js = (JavascriptExecutor)driver;
        moveIntoView(element);
        for (int i = 0; i < 2; i++){
            try{
                if (i == 0){
                    js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: black; border: 3px solid red; background: yellow");
                    new Screenshot(driver, extentTest).takeScreenshotAndLog();
                }else {
                    sleep(500);
                    js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
