package com.turn.pages;

import com.turn.config.Constants;
import com.turn.utils.DriverFactory;
import com.turn.utils.SeleniumUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends SeleniumUtils{

    @FindBy(name = "username")
    WebElement txtUserName;

    @FindBy(name = "password")
    WebElement txtPassword;

    @FindBy(className = "auth0-lock-submit")
    WebElement btnLogin;

    public LoginPage(){
        PageFactory.initElements(DriverFactory.getInstance().getDriver(),this );
    }

    private void setUserName(String user){
        fnHighlightMe(txtUserName);
        txtUserName.sendKeys(user);
    }

    private void setPassword(String pass){
        fnHighlightMe(txtPassword);
        txtPassword.sendKeys(pass);
    }

    private void clickLogIn(){
        fnHighlightMe(btnLogin);
        btnLogin.click();
    }

    public LoginCommand LoginAs(String userName){
        return new LoginCommand(userName);
    }

    public void navigateToTurnPage(){
        DriverFactory.getInstance().getDriver().navigate().to(Constants.URL);
    }

    public class LoginCommand{
        private String userName;
        private String password;

        public LoginCommand(String userName){
            this.userName = userName;
        }

        public LoginCommand withPassword(String password){
            this.password = password;
            return this;
        }

        public void login(){
            setUserName(userName);
            setPassword(password);
            clickLogIn();
        }
    }
}
