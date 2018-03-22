package com.turn.config;

import com.turn.utils.ExcelUtils;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.Random;

public class TestData {

    @DataProvider(name = "TurnData")
    public static Object[][] getDataFromDataProvider(Method testMethod) throws Exception {
        Object[][] testArray;
        if(testMethod.getName().equalsIgnoreCase("userCanLogin")){
            testArray = ExcelUtils.getTableArray(Constants.EXCEL_DOC, Constants.LOGIN_USER_INFO);
        }else if(testMethod.getName().equalsIgnoreCase("addCandidateSuccessfully")){
            testArray = ExcelUtils.getTableArray(Constants.EXCEL_DOC, Constants.VALID_DATA);
        }else if(testMethod.getName().equalsIgnoreCase("introduceInvalidCredentials")){
            testArray = ExcelUtils.getTableArray(Constants.EXCEL_DOC, Constants.INVALID_DATA);
        } else {
            testArray = new Object[][]{
                    {"gilberto.aspros+001@gmail.com", "Test612"}
            };
        }
        return testArray;
    }
}
