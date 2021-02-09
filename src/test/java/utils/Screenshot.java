package utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;


public class Screenshot{
    private WebDriver driver;
    private ExtentTest extentTest;

    public Screenshot(WebDriver driver, ExtentTest extentTest){
        this.driver = driver;
        this.extentTest = extentTest;
    }

    /**
     * Method will capture screenshot of whole window and add to the report
     */
    public void takeScreenshotAndLog(){
        //taking a screenshot
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

        //creating a file name including the folder name, this is because extentReport wont take full file path
        String fileName = "screenshots/Screenshot_" + new DateTimeUtils().currentDateTime + ".png";

        //this is needed to specify where to save the screenshot
        String filePath = System.getProperty("user.dir") + "/target/" + fileName;

        try{
            //saving the screenshot in "target" folder
            FileUtils.copyFile(screenshot, new File(filePath));

            //logging the screenshot to extentReport
           extentTest.info("Screenshot", MediaEntityBuilder.createScreenCaptureFromPath(fileName).build());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Method will take a screenshot and save it under target/screenshots folder
     * @return MediaEntityModelProvider which can be used as a parameter in extentTest log and will be added to
     * the Test report
     */
    public MediaEntityModelProvider takeScreenshot(){
        //taking a screenshot
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

        //creating a file name including the folder name, this is because extentReport wont take full file path
        String fileName = "screenshots/Screenshot_" + new DateTimeUtils().currentDateTime + ".png";

        //this is needed to specify where to save the screenshot
        String filePath = System.getProperty("user.dir") + "/target/" + fileName;

        try{
            //saving the screenshot in "target" folder
            FileUtils.copyFile(screenshot, new File(filePath));
            return MediaEntityBuilder.createScreenCaptureFromPath(fileName).build();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
