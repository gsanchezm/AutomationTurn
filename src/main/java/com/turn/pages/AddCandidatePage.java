package com.turn.pages;

import com.turn.utils.DriverFactory;
import com.turn.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AddCandidatePage extends SeleniumUtils{

    WebDriverWait wait;

    @FindBy(id = "worker_firstname")
    WebElement txtFirstName;

    @FindBy(id = "worker_lastname")
    WebElement txtLastName;

    @FindBy(id = "worker_phone")
    WebElement txtMobile;

    @FindBy(id = "worker_email")
    WebElement txtEmail;

    @FindBy(id = "worker_ssn")
    WebElement txtSSN;

    @FindBy(xpath = "//span[contains(text(),'Next')]")
    WebElement btnNext;

    @FindBy(xpath = "//span[contains(text(),'Submit')]")
    WebElement btnSubmit;

    @FindBy(xpath = "//span[contains(text(),'Cancel')]")
    WebElement btnCancel;

    @FindBy(xpath = "//span[contains(text(),'Back')]")
    WebElement btnBack;

    @FindBy(xpath = "//div[2]/div/div[1]/div/div/div[1]/div/div[1]/div[1]/div/div[2]")
    WebElement lblFirstNameError;

    @FindBy(xpath = "//div[2]/div/div[1]/div/div/div[1]/div/div[1]/div[2]/div/div[2]")
    WebElement lblLastNameError;

    @FindBy(xpath = "//div[2]/div/div[1]/div/div/div[1]/div/div[3]/div[3]")
    WebElement lblEmailError;

    @FindBy(xpath = "//div[2]/div/div[1]/div/div/div[1]/div/div[2]/div[3]")
    WebElement lblMobileError;

    public AddCandidatePage(){
        PageFactory.initElements(DriverFactory.getInstance().getDriver(),this);
    }

    public AddCandidateCommand addCandidate(String firstName){
        return new AddCandidateCommand(firstName);
    }

    private void setFirstName(String firstName){
        //fnHighlightMe(txtFirstName);
        txtFirstName.clear();
        txtFirstName.sendKeys(firstName);
    }

    private void setLastName(String lastName){
        //fnHighlightMe(txtLastName);
        txtLastName.clear();
        txtLastName.sendKeys(lastName);
    }

    private void setMobile(String mobile){
        //fnHighlightMe(txtMobile);
        txtMobile.clear();
        txtMobile.sendKeys(mobile);
    }

    private void setEmail(String email){
        //fnHighlightMe(txtEmail);
        txtEmail.clear();
        txtEmail.sendKeys(email);
    }

    private void setSecuritySocialNumber(String securitySocialNum){
        //fnHighlightMe(txtSSN);
        txtSSN.clear();
        txtSSN.sendKeys(securitySocialNum);
    }

    private void clickNext(){
        //fnHighlightMe(btnNext);
        btnNext.click();
    }

    public boolean submtExist(){
        wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), 15);
        wait.until(ExpectedConditions.visibilityOf(btnSubmit));
        fnHighlightMe(btnSubmit);
        return true;
    }

    public void clickCancel(){
        fnHighlightMe(btnCancel);
        btnCancel.click();
    }

    private void clickBack(){
        fnHighlightMe(btnBack);
        btnBack.click();
    }

    public boolean fisrtNameErrorExist(WebDriver driver){
        try {
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            lblFirstNameError.isDisplayed();
            fnHighlightMe(lblFirstNameError);
            return true;
        }catch (NoSuchElementException ne){
            return false;
        }
    }

    public String getFirstNameError(){
        return lblFirstNameError.getText();
    }

    public boolean lastNameErrorExist(WebDriver driver){
        try {
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            lblLastNameError.isDisplayed();
            fnHighlightMe(lblLastNameError);
            return true;
        }catch (NoSuchElementException ne){
            return false;
        }
    }

    public String getLastNameError(){
       return lblLastNameError.getText();
    }

    public boolean emailErrorExist(WebDriver driver){
        try {
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            lblEmailError.isDisplayed();
            fnHighlightMe(lblEmailError);
            return true;
        }catch (NoSuchElementException ne){
            return false;
        }
    }

    public String getEmailError(){
        return lblEmailError.getText();
    }

    public boolean mobilErrorExist(WebDriver driver){
        try {
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            lblMobileError.isDisplayed();
            fnHighlightMe(lblMobileError);
            return true;
        }catch (NoSuchElementException ne){
            return false;
        }
    }

    public String getMobileError(){
        return lblMobileError.getText();
    }

    public ReturnDashCommand returnDashboard(){
        return new ReturnDashCommand();
    }

    public List<WebElement> getElementsConfirmationDetails(){
        wait = new WebDriverWait(DriverFactory.getInstance().getDriver(), 15);
        wait.until(ExpectedConditions.visibilityOfAllElements(DriverFactory.getInstance().getDriver().findElements(By.xpath("//div[2]/div/div[1]/div/div/div[1]/div/div/span/div/div"))));
        List<WebElement> confirmationDetails = DriverFactory.getInstance().getDriver().findElements(By.xpath("//div[2]/div/div[1]/div/div/div[1]/div/div/span/div/div"));
        return confirmationDetails;
    }

    public String[] getConfirmationDetails(){
        String details[]= new String[getElementsConfirmationDetails().size()];
        int i = 0;
        for (WebElement element :getElementsConfirmationDetails() ) {
            //fnHighlightMe(element);
            details[i] = element.getText();
            i++;
        }

        return details;
    }

    public class AddCandidateCommand{
        private String firstName;
        private String lastName;
        private String mobileNumber;
        private String email;

        public AddCandidateCommand(String firstName){
            this.firstName = firstName;
        }

        public AddCandidateCommand withLastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        public AddCandidateCommand withMobile(String mobileNumber){
            this.mobileNumber = mobileNumber;
            return this;
        }

        public AddCandidateCommand withEmail(String email){
            this.email = email;
            return this;
        }

        public void add(){
            setFirstName(firstName);
            setLastName(lastName);
            setMobile(mobileNumber);
            setEmail(email);
            clickNext();
        }

    }

    public class ReturnDashCommand{
        public ReturnDashCommand(){

        }

        public ReturnDashCommand backAddCandidate(){
            clickBack();
            return this;
        }

        public void cancel(){
            clickCancel();
        }
    }
}
