package test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ConfigReader;
import utils.Screenshot;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    private final String propertyPath = "configuration.properties";
    protected WebDriver driver;

    //builds a new report using the html template
    private ExtentHtmlReporter htmlReporter;
    private ExtentReports extent;

    //helps to generate the logs in test report.
    protected ExtentTest extentTest;

    //gets details of each Test
    protected Test test;

    protected Screenshot screenshot;

    @BeforeTest
    public void startReport() {
        // initialize the HtmlReporter and give location where it will be created
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") +"/target/testReport.html");

        //initialize ExtentReports and attach the HtmlReporter
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        //To add system or environment info by using the setSystemInfo method. Also could use config.properties file
        //instead of @Parameters
        extent.setSystemInfo("OS", System.getProperty("os.name") + System.getProperty("os.version"));
        extent.setSystemInfo("Author", System.getProperty("user.name"));
        extent.setSystemInfo("Browser", ConfigReader.readProperty(propertyPath, "browser"));

        //configuration items to change the look and feel
        //add content, manage tests etc
        htmlReporter.config().setChartVisibilityOnOpen(false);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
        htmlReporter.config().setDocumentTitle("TLA Automation Reports");
        htmlReporter.config().setReportName(ConfigReader.readProperty(propertyPath, "reportType"));
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
    }

    @BeforeMethod()
    public void setUp(Method method){
        //Extracting custom test name from each Test
        test = method.getAnnotation(Test.class);

        extentTest = extent.createTest(test.testName());
        initializeDriver();
        screenshot = new Screenshot(driver, extentTest);

        extentTest.info(test.description());
    }

    @AfterMethod(alwaysRun = true)
    public void getResult(ITestResult result) throws IOException {
        if(result.getStatus() == ITestResult.FAILURE) {
            extentTest.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" FAILED ", ExtentColor.RED));
            extentTest.fail(result.getThrowable(), screenshot.takeScreenshot());
        }
        else if(result.getStatus() == ITestResult.SUCCESS) {
            extentTest.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" PASSED ", ExtentColor.GREEN));
        }
        else {
            extentTest.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" SKIPPED ", ExtentColor.ORANGE));
            extentTest.skip(result.getThrowable());
        }
        driver.quit();
    }

    @AfterTest
    public void tearDown() {
        //to write or update test information to reporter
        extent.flush();
    }

    private void initializeDriver(){
        String browser = ConfigReader.readProperty(propertyPath, "browser");

        switch (browser.toLowerCase()){
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            default:
                System.out.println("Invalid browser name");
                return;
        }
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
}
