package Services;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public class Verification {
    private GlobalVariables globalVariables;

    public Verification(GlobalVariables globalVariables2){
        globalVariables=globalVariables2;
    }

    public void verifyObjText(WebElement obj,String name,String data) {
        if (obj != null) {
            JavascriptExecutor jse = (JavascriptExecutor) globalVariables.driver;
            jse.executeScript("arguments[0].scrollIntoView()", obj);
            globalVariables.softAssertion.assertEquals(obj.getText().replace(" \n","\n").trim(), data.replace(" \n","\n").replace("&amp;","&").trim());
            if (obj.getText().replace(" \n","\n").trim().compareTo(data.replace(" \n","\n").replace("&amp;","&").trim()) == 0) {
                globalVariables.commonFunc.logPass(obj, "<b>Object: </b>" + name + "<br /><b>Verification: </b>" + data.trim());
            } else {
                globalVariables.commonFunc.logFail(obj, "<b>Object: </b>" + name + "<br /><b>Expected: </b>" + data.trim() + "<br /><b>Actual: </b>" + obj.getText().trim());
            }
        }
        else
            System.out.println("WebElement Provided for Verification is NULL");
    }

    //***********************************************************
    //*************DROPDOWN FUNCTIONS************************
    //***********************************************************
    public void verifyDropDownValue(WebElement obj,String data) {
        if (obj != null) {
            Select dropDownObj = new Select(obj);
            if (dropDownObj.getFirstSelectedOption().getText().compareTo(data) == 0) {
                globalVariables.commonFunc.logPass(obj, "<b>Object: </b>" + globalVariables.commonFunc.getObjectDisplayName(obj) + "<br /><b>Verification:</b> Selected Value <br /><b>Actual: </b>" + dropDownObj.getFirstSelectedOption().getText() + "<br /><b>Expected: </b>" + data);
            } else {
                globalVariables.commonFunc.logFail(obj, "<b>Object: </b>" + globalVariables.commonFunc.getObjectDisplayName(obj) + "<br /><b>Verification:</b> Selected Value <br /><b>Actual: </b>" + dropDownObj.getFirstSelectedOption().getText() + "<br /><b>Expected: </b>" + data);
            }
        }
    }
}
