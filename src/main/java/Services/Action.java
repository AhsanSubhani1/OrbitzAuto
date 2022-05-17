package Services;

import org.apache.poi.ss.formula.functions.T;
import org.omg.PortableServer.THREAD_POLICY_ID;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Action {
    private GlobalVariables globalVariables;

    public Action(GlobalVariables GV2) {
        globalVariables = GV2;
    }



    private final By loadingObject = By.xpath("//div[" +
            "text()='loading chart...' " +
            "or (@id='searching-msg' and contains(text(),'Searching...')) " +
            "or @class='loading_bg' or (contains(@class,'blockMsg') and not(contains(@style,'display: none')) ) " +
            "or (@class='dataTables_processing' and @style='visibility: visible;') " +
            "or (/html/body/div[3]/div/div/div/div/p) " +
            "or (/html/body/div[4]/div/div/div/div/p)" +
            "or (@id='divOverlay' and @class='overlay' and @style='display: block;')" +
            " ]");

    //***********************************************************
    //*************TEXTBOX FUNCTIONS******************************
    //***********************************************************

    public void setTextBoxValue(WebElement obj, String data) {
        if (obj != null) {
            obj.clear();
            obj.sendKeys(Keys.HOME);
            obj.sendKeys(data);
            globalVariables.commonFunc.logInfo(obj,
                    "<b>Object: </b>" + globalVariables.commonFunc.getObjectDisplayName(obj)
                            + "<br /><b>Action: </b>Written <br /><b>Data: </b>" + data);
        }
    }

    //***********************************************************
    //*********************HELPER FUNCTIONS**************************
    //***********************************************************
    public void click(WebElement obj) {
     if (obj != null) {
            loadChecker();
            globalVariables.commonFunc.logInfo(obj,
                    "<b>Object: </b>" + globalVariables.commonFunc.getObjectDisplayName(obj)
                            + "<br /><b>Action: </b> Click");
            JavascriptExecutor jse = (JavascriptExecutor) globalVariables.driver;
            jse.executeScript("arguments[0].scrollIntoView()", obj);
            if (obj.isEnabled()) {
                try {
                    jse.executeScript("arguments[0].click()", obj);

                } catch (Exception e) {
                    try {
                        click(obj);
                    } catch (Exception ex) {
                        globalVariables.commonFunc.logInfo(null, ex.getMessage());
                    }
                }
            } else {
                globalVariables.commonFunc.logWarning(obj, "<b>Object: </b>" + globalVariables.commonFunc.getObjectDisplayName(obj)
                        + "<br /> is disabled");
            }
            loadChecker();
            }
        }

    public void loadChecker() {
        int count = 0;
        if (!globalVariables.commonFunc.isAlertPresent()) {
            int flag = 0;
            JavascriptExecutor jse = (JavascriptExecutor) globalVariables.driver;
            jse.executeScript("return document.readyState").toString().equals("complete");
            while (flag == 0 && !jse.executeScript("return document.readyState").toString().equals("complete")) {
                globalVariables.logger.info("WAITING FOR PAGE LOADING TO COMPLETE");
                globalVariables.commonFunc.delay(500);
                count = count + 1;
                if (count >= 200) {
                    globalVariables.commonFunc.logWarning(null, "Timeout loading for page using readyState");
                    flag = 1;
                }
            }
            count = 0;
            flag = 0;

            while (flag == 0) {
                globalVariables.driver.manage().timeouts().implicitlyWait(200, TimeUnit.MILLISECONDS);
                List<WebElement> result = globalVariables.commonFunc.findElements(loadingObject);
                globalVariables.driver.manage().timeouts().implicitlyWait(globalVariables.objTimeout, TimeUnit.SECONDS);
                try {
                    if (result.size() > 0) {
                        for (int i = 0; i < result.size(); i++) {
                            if (result.get(i).getText().toLowerCase().contains("enter a new group name") || result.get(i).getText().equals("Are you sure you want to delete group \"zzAutoGroup\"?\n" + "Yes No"))
                                flag = 1;
                        }
                    }
                    if ((result.size() == 0) || (result.size() == 1 && result.get(0).isDisplayed() == false)) {
                        flag = 1;
                    } else {
                        try {
                            globalVariables.logger.info("WAIT OBJECT: " + result.get(0).getAttribute("outerHTML"));
                            globalVariables.commonFunc.delay(500);
                        } catch (StaleElementReferenceException e) {
                            globalVariables.commonFunc.logInfo(null, "Wait Element cleared - " + loadingObject.toString());
                        } catch (Exception e) {
                            globalVariables.commonFunc.logInfo(null, "Exception - Trying to log waiting object, Action.Java");
                        }
                    }

                } catch (StaleElementReferenceException e) {

                }
                count = count + 1;
                if (count >= 200) {
                    globalVariables.commonFunc.logWarning(null, "Timeout loading for page");
                    flag = 1;
                    refreshPage();
                }
            }
        }
    }

    public void refreshPage() {
        globalVariables.driver.navigate().refresh();
        globalVariables.commonFunc.logInfo(null,
                "<b>Action: </b>Page Refreshed<br /><b>Data: </b>" + globalVariables.driver.getCurrentUrl());
        loadChecker();
    }

    public void waitInMilliSec(int TimeoutInMillis){
     try {
            Thread.sleep(TimeoutInMillis);
        }
        catch (Exception e){

        }
    }
}