package com.turn.utils;

import com.turn.config.Constants;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.testng.Assert;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SeleniumUtils {
    public static String SSDate;
    public static String SSDateTime;
    public static String file;

    public static void fnHighlightMe(WebElement element) {
        //Creating JavaScriptExecuter Interface
        JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getInstance().getDriver();//driver;
        for (int iCnt = 0; iCnt < 3; iCnt++) {
            //Execute javascript
            try {
                js.executeScript("arguments[0].setAttribute('style','background: yellow')", element);
                Thread.sleep(20);
                js.executeScript("arguments[0].setAttribute('style','background:')", element);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                Assert.fail("Class SeleniumUtils | Method fnHighlightMe | Exception desc: Exception", e);
            }
        }
    }

    public static String takeSnapShot(WebDriver webdriver, String snapshotError) {
        SSDate = new SimpleDateFormat("yyyyMMdd_HH").format(Calendar.getInstance().getTime()).toString();
        SSDateTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()).toString();
        file = createFolder(Constants.ScreenShots_Folder + SSDate)+ "/" + snapshotError + SSDateTime + ".png";
        //Convert web driver object to TakeScreenshot
        TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
        try { //Call getScreenshotAs method to create image file
            File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
            //Move image file to new destination
            File DestFile = new File(file);
            //Copy file at destination
            FileUtils.copyFile(SrcFile, DestFile);

            //***********************************************
//			ExcelUtils.hyperlinkScreenshot(file); --- Commented by space error on path Illegal character in path at index 17: C:/Users/gilberto sanchez/
        } catch (Exception e) {
            Assert.fail("Class SeleniumUtils | Method takeSnapShot | Exception desc: " + e.getMessage());
        }
        return file;
    }

    private  static String createFolder(String folderName) {
        File theDir = new File(folderName);

        // if the directory does not exist, create it
        if (!theDir.exists()) {
            System.out.println("creating directory: " + theDir.getName());
            boolean result = false;

            try {
                theDir.mkdir();
                result = true;
            } catch (SecurityException se) {
                new Exception("Class SeleniumUtils | Method createFolder | Exception: " + se.getMessage());
            }
            if (result) {
                System.out.println("DIR created");
            }
        }
        return theDir.toString();
    }

    public static void waitTime(int time){
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
    }
}
