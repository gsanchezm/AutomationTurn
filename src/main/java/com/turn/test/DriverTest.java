package com.turn.test;

import com.turn.config.BaseClass;
import com.turn.config.Constants;
import com.turn.config.TestData;
import com.turn.utils.SeleniumUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class DriverTest extends BaseClass{

    @Test(dataProvider = "TurnData", dataProviderClass = TestData.class, priority = 0)
    public void userCanLogin(String userEmail, String userPass){
        startTestCase("Login With Valid Credentials","Verify user is able to login with correct password.");
        login.navigateToTurnPage();
        logInfo("Navigating to: " + Constants.URL);
        login.LoginAs(userEmail).withPassword(userPass).login();
        Assert.assertEquals("Turn Partners Dashboard", dashboard.getPageTitle());
        logPassStep("Login successfully");
    }

    @Test(dependsOnMethods = "userCanLogin", dataProvider = "TurnData", dataProviderClass = TestData.class, enabled = true, priority = 1)
    public void addCandidateSuccessfully(String ... contacInfo){
        startTestCase("Add candidate correctly","Verify user is able to add a candidate");
        dashboard.clickAddCandidate();
        logInfo("Opening add candidate form");
        addCandidate.addCandidate(contacInfo[0]).withLastName(contacInfo[1]).withMobile(contacInfo[2]).withEmail(contacInfo[3]).add();
        for (String contact: addCandidate.getConfirmationDetails() ) {
            logInfo(contact);
        }
        Assert.assertTrue(addCandidate.submtExist());
        logPassStep("Almost done adding contacts");
        addCandidate.returnDashboard().backAddCandidate().cancel();
        SeleniumUtils.waitTime(1);
    }

    @Test(dependsOnMethods = "userCanLogin", dataProvider = "TurnData", dataProviderClass = TestData.class, enabled = true, priority = 2)
    public void introduceInvalidCredentials(String ... contacInfo){
        startTestCase("Add worng candidate infromation","Verify errors displayed when wrong candidate info is typed");
        dashboard.clickAddCandidate();
        logInfo("Opening add candidate form");
        addCandidate.addCandidate(contacInfo[0]).withLastName(contacInfo[2]).withMobile(contacInfo[4]).withEmail(contacInfo[6]).add();

        if (addCandidate.fisrtNameErrorExist(driver)){
            Assert.assertEquals(addCandidate.getFirstNameError(),contacInfo[1]);
            logPassStep("Getting error " + addCandidate.getFirstNameError());
        }

        if(addCandidate.lastNameErrorExist(driver)){
            Assert.assertEquals(addCandidate.getLastNameError(),contacInfo[3]);
            logPassStep("Getting error " + addCandidate.getLastNameError());
        }

        if(addCandidate.emailErrorExist(driver)){
            Assert.assertEquals(addCandidate.getEmailError(),contacInfo[7]);
            logPassStep("Getting error " + addCandidate.getEmailError());
        }

        if (addCandidate.mobilErrorExist(driver)){
            Assert.assertEquals(addCandidate.getMobileError(),contacInfo[5]);
            logPassStep("Getting error " + addCandidate.getMobileError());
        }

        logInfo("Trying with other information");
        addCandidate.clickCancel();
        SeleniumUtils.waitTime(1);
    }

    @Test(dataProvider = "TurnData", dataProviderClass = TestData.class, priority = 3)
    public void screenshotExample(String userEmail, String userPass){
        startTestCase("Login With Invalid Credentials","Test as dome taking screenshots.");
        login.navigateToTurnPage();
        logInfo("Navigating to: " + Constants.URL);
        login.LoginAs(userEmail).withPassword(userPass).login();
        Assert.assertEquals("Turn Partners Dashboard", dashboard.getPageTitle());
    }
}
