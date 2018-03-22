package com.turn.pages;

import com.turn.utils.DriverFactory;
import com.turn.utils.SeleniumUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DashboardPage extends SeleniumUtils{

    @FindBy(id = "check_worker")
    WebElement btnAddCandidate;

    public DashboardPage(){
        PageFactory.initElements(DriverFactory.getInstance().getDriver(),this);
    }

    public String getPageTitle(){
            return DriverFactory.getInstance().getDriver().getTitle();
    }

    public void clickAddCandidate(){
        fnHighlightMe(btnAddCandidate);
        btnAddCandidate.click();
    }
}
