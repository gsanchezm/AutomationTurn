package com.turn.config;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.turn.pages.AddCandidatePage;
import com.turn.pages.DashboardPage;
import com.turn.pages.LoginPage;
import com.turn.utils.DriverFactory;
import com.turn.utils.ExtentProperties;
import com.turn.utils.SeleniumUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BaseClass{
    public WebDriver driver;
    private WebDriverWait wait;
    public ExtentProperties exProp = new ExtentProperties();
    String extentReportFile;
    private static Logger Log = Logger.getLogger(BaseClass.class);
    public LoginPage login;
    public AddCandidatePage addCandidate;
    public DashboardPage dashboard;

    @AfterMethod
    public void getResult(ITestResult result){
        switch (result.getStatus()){
            case ITestResult.FAILURE:
                logTestFail(result);
                break;
            case ITestResult.SUCCESS:
                logTestPass(result);
                break;
            case ITestResult.SKIP:
                logTestSkip(result);
                break;
        }
    }

    @BeforeTest
    public void setUpTest(){
        extentReportFile = Constants.HTML_REPORT;

        // Create object of extent report and specify the report file path.
        exProp.setExtent(new ExtentReports(extentReportFile, true));

        exProp.getExtent().loadConfig(new File(Constants.EXTENT_CONFIG));
        // Start the test using the ExtentTest class object.
        DriverFactory.getInstance().setDriver(BrowserType.CHROME);
        driver = DriverFactory.getInstance().getDriver();

        login = new LoginPage();
        addCandidate = new AddCandidatePage();
        dashboard = new DashboardPage();
    }

    @AfterTest
    public void tearDown(){
        DriverFactory.getInstance().removeDriver();
        exProp.getExtentTest().log(LogStatus.INFO, "Browser closed");

        // close report.
        exProp.getExtent().endTest(exProp.getExtentTest());

        // writing everything to document.
        exProp.getExtent().flush();
    }

    @BeforeClass
    public void settingLogs(){
        PropertyConfigurator.configure(Constants.LogPropDir);
    }

    @AfterSuite
    public void openReport() throws IOException {
        Desktop.getDesktop().open(new File(Constants.HTML_REPORT));
    }

    public void startTestCase(String testName, String desc){
        exProp.setExtentTest(exProp.getExtent().startTest(testName, desc));
    }

    private void logTestPass(ITestResult result){
        exProp.getExtentTest().log(LogStatus.PASS, result.getName() + " Passed");
    }

    private void logTestFail(ITestResult result){
        // In case you want to attach screenshot then use below method
        // We used a random image but you've to take screenshot at run-time
        // and specify the error image path.
        exProp.getExtentTest().log(LogStatus.FAIL, result.getName() + " Failed due to below issue : " +
                exProp.getExtentTest().addScreenCapture(SeleniumUtils.takeSnapShot(driver, "error_")));
    }

    private void logTestSkip(ITestResult result){
        exProp.getExtentTest().log(LogStatus.SKIP, result.getName() + " Skipped");
    }

    public void logInfo(String details){
        exProp.getExtentTest().log(LogStatus.INFO, details );
    }

    public void logPassStep(String details){
        exProp.getExtentTest().log(LogStatus.PASS, details);
    }

    public void waitForElement(WebElement element){
        wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), 15);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitImplicit(){
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }
}
