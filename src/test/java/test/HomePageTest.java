package test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;


public class HomePageTest extends BaseTest{
    HomePage homePage;

    @BeforeMethod
    public void setUp(){
        homePage = new HomePage(driver, extentTest);
    }

    @Test(testName = "Basic test", description = "adding basic Test to reports")
    public void test1(){
        //extentTest.info(test.description());
    }

    @Test(testName = "Basic Test 2 - Categories", description = "Assigning a category for the test based on the group name", groups = {"smoke","regression"})
    public void test2(){
        //extentTest.log(Status.INFO, test.description());
        extentTest.assignCategory(test.groups());
    }

    @Test(testName = "Basic Test 3 - Authors", description = "Assigning an author for each test")
    public void test3(){
        extentTest.assignAuthor("Jessica");
    }

    @Test(testName = "Selenium Test - Screenshot 1", description = "Adding a screenshot anywhere needed")
    public void test4(){
        driver.get("https://google.com");
        screenshot.takeScreenshotAndLog();
        extentTest.info("Some custom details here...");
    }

    @Test(testName = "Selenium Test - Screenshot 2", description = "Adding a screenshot if test FAILS")
    public void test5(){
        //method logic added to @AfterMethod
        driver.get("https://amazon.com");
        Assert.fail();
    }

    @Test(testName = "TLA website Test")
    public void test6(){
        driver.get("http://automation.techleadacademy.io/");
        homePage.highlightElementAndTakeScreenshot(homePage.phpLink);
    }

}
