package Services;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class CommonFunc {
    private GlobalVariables globalVariable;
    private final Action action;
    private final Verification verification;

    public CommonFunc(GlobalVariables globalVariable2) {
        globalVariable = globalVariable2;
        action = new Action(globalVariable);
        verification = new Verification(globalVariable);
    }

    //************************************************************
    //**************GET OBJECT NAME FOR REPORTING****************
    //************************************************************
    public String getObjectDisplayName(WebElement obj) {
        String Name = null;
        int flag = 0;
        if (obj.getAttribute("type") != null && (obj.getAttribute("type").compareTo("text") == 0 || obj.getAttribute("type").compareTo("password") == 0 || obj.getAttribute("type").compareTo("textarea") == 0 || obj.getAttribute("type").compareTo("email") == 0 || obj.getAttribute("type").compareTo("search") == 0)) {     //Type is Text OR Password
            flag = 1;
            if (obj.getAttribute("placeholder").length() != 0) {
                Name = obj.getAttribute("placeholder");
            } else if (obj.getAttribute("id").length() != 0) {
                Name = obj.getAttribute("id");
            } else if (obj.getAttribute("name").length() != 0) {
                Name = obj.getAttribute("name");
            } else if (obj.getAttribute("class").length() != 0) {
                Name = obj.getAttribute("class");
            } else {
                Name = "Object Name Not Found";
                globalVariable.test.log(Status.WARNING, "Object Name Not Found For:" + obj.findElement(By.xpath(".")).toString());
            }

        }
        if (flag == 0 && obj.getAttribute("type") != null && obj.getAttribute("type").compareTo("select-one") == 0) {     //Type is Text OR Password
            flag = 1;
            if (obj.getAttribute("id").length() != 0) {
                Name = obj.getAttribute("id");
                flag = 1;
            } else if (obj.getAttribute("name").length() != 0) {
                Name = obj.getAttribute("name");
                flag = 1;
            } else {
                Name = "Object Name Not Found";
                globalVariable.test.log(Status.WARNING, "Object Name Not Found For:" + obj.findElement(By.xpath(".")).toString());
            }

        }

        if (flag == 0 && obj.getAttribute("type") != null && (obj.getAttribute("type").compareTo("button") == 0 || obj.getAttribute("type").compareTo("submit") == 0 || obj.getAttribute("type").compareTo("image") == 0)) {     //Type is button OR submit
            flag = 1;
            if (obj.getText().length() != 0) {
                Name = obj.getText();
            } else if (obj.getAttribute("value").length() != 0) {
                Name = obj.getAttribute("value");
            } else if (obj.getAttribute("name").length() != 0) {
                Name = obj.getAttribute("name");
            } else if (obj.getAttribute("class").length() != 0) {
                Name = obj.getAttribute("class");
            } else {
                Name = "Object Name Not Found";
                globalVariable.test.log(Status.WARNING, "Object Name Not Found For:" + obj.findElement(By.xpath(".")).toString());
            }

        }

        if (flag == 0 && obj.getAttribute("type") != null && (obj.getAttribute("type").compareTo("checkbox") == 0 || obj.getAttribute("type").compareTo("radio") == 0)) {  //Type is Checkbox
            flag = 1;
            if (obj.getAttribute("id").length() != 0) {
                Name = obj.getAttribute("id");
            } else {
                Name = "Object Name Not Found";
                globalVariable.test.log(Status.WARNING, "Object Name Not Found For:" + "");
            }
        }

        if (flag == 0 && obj.getAttribute("type") != null && (obj.getAttribute("type").compareTo("") == 0)) {  //Type is empty
            flag = 1;
            Name = obj.getText();
        }

        if (flag == 0 && (obj.getTagName().compareTo("img") == 0 || obj.getTagName().compareTo("h1") == 0 || obj.getTagName().compareTo("h3") == 0 || obj.getTagName().compareTo("h4") == 0 || obj.getTagName().compareTo("h5") == 0 || obj.getTagName().compareTo("h6") == 0 || obj.getTagName().compareTo("p") == 0)) {              //Type is paragraph OR Heading
            flag = 1;
            if (obj.findElement(By.xpath("..")).getAttribute("data-item") != null) {
                Name = obj.findElement(By.xpath("..")).getAttribute("data-item");
            } else if (obj.findElement(By.xpath("..")).getAttribute("id") != null && obj.findElement(By.xpath("..")).getAttribute("id").length() != 0) {
                Name = obj.findElement(By.xpath("..")).getAttribute("id");
            } else if (obj.findElement(By.xpath("..")).getAttribute("class") != null && obj.findElement(By.xpath("..")).getAttribute("class").length() != 0) {
                Name = obj.findElement(By.xpath("..")).getAttribute("class");
            } else if (obj.getText().length() > 0) {
                Name = obj.getText();
            } else {
                Name = "Object Name Not Found";
                globalVariable.test.log(Status.WARNING, "Object Name Not Found For:" + obj.findElement(By.xpath(".")).toString());
            }
        }

        if (flag == 0 && obj.getTagName().compareTo("span") == 0) {                                                                                         //Span
            flag = 1;
            if (obj.getAttribute("id").length() != 0) {
                Name = obj.getAttribute("id");
            } else if (obj.getAttribute("class").length() != 0) {
                Name = obj.getAttribute("class");
            } else {
                Name = "Object Name Not Found";
                globalVariable.test.log(Status.WARNING, "Object Name Not Found For:" + obj.findElement(By.xpath(".")).toString());
            }
        }

        if (flag == 0 && obj.getTagName().compareTo("div") == 0) {                                                                                         //DIV
            flag = 1;
            if (obj.getAttribute("id").length() != 0) {
                Name = obj.getAttribute("id");
            } else if (obj.getAttribute("class").length() != 0) {
                Name = obj.getAttribute("class");
            } else if (obj.getAttribute("innerText").length() != 0) {
                Name = obj.getAttribute("innerText");
            } else {
                Name = "Object Name Not Found";
                globalVariable.test.log(Status.WARNING, "Object Name Not Found For:" + obj.findElement(By.xpath(".")).toString());
            }
        }

        if (flag == 0 && obj.getTagName().compareTo("table") == 0) {                                                                                         //TABLE
            flag = 1;
            if (obj.getAttribute("id").length() != 0) {
                Name = obj.getAttribute("id");
            } else {
                Name = "Table";
                globalVariable.test.log(Status.WARNING, "Object Name Not Found For:" + obj.findElement(By.xpath(".")).toString());
            }
        }

        if (flag == 0 && obj.getTagName().compareTo("select") == 0) {  //Type is DropDown
            flag = 1;
            if (obj.getAttribute("id").length() != 0) {
                Name = obj.getAttribute("id");
            }
            if (obj.getAttribute("name").length() != 0) {
                Name = obj.getAttribute("name");
            } else {
                Name = "Object Name Not Found";
                globalVariable.test.log(Status.WARNING, "Object Name Not Found For:" + obj.findElement(By.xpath(".")).toString());
            }
        }

        if (flag == 0 && (obj.getTagName().equals("i") || obj.getTagName().equals("li"))) {
            flag = 1;
            if (obj.getAttribute("class").length() != 0) {
                Name = obj.getAttribute("class");
            } else {
                Name = "Object Name Not Found";
                globalVariable.test.log(Status.WARNING, "Object Name Not Found For:" + obj.findElement(By.xpath(".")).toString());
            }

        }

        if (flag == 0 && obj.getAttribute("type") == null) {
            flag = 1;
            Name = obj.getText();
        }

        if (flag == 0) {//NOT FOUND
            if (obj.getAttribute("name") != null && !obj.getAttribute("name").equals("")) {
                Name = obj.getAttribute("name");
            } else
                globalVariable.test.log(Status.WARNING, "Object Identification failed for :" + obj.findElement(By.xpath(".")).toString());
        }
        return Name;
    }

    public void getUrl(String url) {
        logInfo(null, "Navigating to URL: " + url);
        globalVariable.driver.get(url);
        globalVariable.commonFunc.delay(1000);
    }

    public WebElement findElement(By finder) {
        WebElement obj;
        try {
            WebDriverWait wait = new WebDriverWait(globalVariable.driver, globalVariable.objTimeout);
            obj = wait.until(ExpectedConditions.visibilityOfElementLocated(finder));
            JavascriptExecutor jse = (JavascriptExecutor) globalVariable.driver;
            jse.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", obj);
            jse.executeScript("arguments[0].setAttribute('style', '');", obj);
            return obj;
        } catch (NoSuchElementException e) {
            logWithScreenshot(null, e.getMessage(), "FAIL");
            logInfo(null, "<b>Calling Function: </b>" + Thread.currentThread().getStackTrace()[2].getMethodName());
            logInfo(null, "<b>Calling Class: </b>" + Thread.currentThread().getStackTrace()[2].getClassName() + " (" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ")");
            Assert.fail(e.getMessage());
            return null;
        }
    }

    public List<WebElement> findElements(By finder) {
        List<WebElement> obj;
        try {
            obj = globalVariable.driver.findElements(finder);
            return obj;
        } catch (NoSuchElementException e) {
            logWithScreenshot(null, e.getMessage(), "FAIL");
            logInfo(null, "<b>Calling Function: </b>" + Thread.currentThread().getStackTrace()[2].getMethodName());
            logInfo(null, "<b>Calling Class: </b>" + Thread.currentThread().getStackTrace()[2].getClassName() + " (" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ")");
            Assert.fail(e.getMessage());
            return null;
        }
    }

    public void logInfo(WebElement Obj, String description) {

        if (globalVariable.logLevel.contains("2")) {     //Save image of each Passing Object as well
            logWithScreenshot(Obj, description, "INFO");
        } else {     //Dont Save image of each Passing Object as well
            globalVariable.test.log(Status.INFO, description);
            globalVariable.logger.info(globalVariable.suiteName + " | " + "ACTION: " + description.replace("<b>", "").replace("</b>", "").replace("<br />", "\r\n"));
        }
    }

    public void logPass(WebElement Obj, String description) {

        if (globalVariable.logLevel.contains("1")) {     //Save image of each Passing Object as well
            logWithScreenshot(Obj, description, "PASS");
        } else {     //Dont Save image of each Passing Object as well
            globalVariable.test.pass(description);
            globalVariable.logger.info(globalVariable.suiteName + " | " + "PASS: " + description.replace("<b>", "").replace("</b>", "").replace("<br />", "\r\n"));
        }
    }

    public void logFail(WebElement Obj, String description) {

        if (globalVariable.logLevel.contains("0")) {     //Save image of each Passing Object as well
            logWithScreenshot(Obj, description, "FAIL");
        } else {
            globalVariable.test.log(Status.FAIL, description);
            globalVariable.logger.info(globalVariable.suiteName + " | " + "FAIL: " + description.replace("<b>", "").replace("</b>", "").replace("<br />", "\r\n"));
        }

    }

    public void logWarning(WebElement Obj, String description) {

        if (globalVariable.logLevel.contains("0")) {     //Save image of each Passing Object as well
            logWithScreenshot(Obj, description, "WARNING");
        } else {
            globalVariable.test.log(Status.WARNING, description);
            globalVariable.logger.info(globalVariable.suiteName + " | " + "WARNING: " + description.replace("<b>", "").replace("</b>", "").replace("<br />", "\r\n"));
        }

    }

    public void logError(ITestResult iTestResult, String description) {
        if (iTestResult != null) {
            logWithScreenshot(null, iTestResult.getThrowable().getCause().toString(), "ERROR");

            StackTraceElement[] stackTrace = iTestResult.getThrowable().getCause().getStackTrace();
            for (int i = 0; i < stackTrace.length; i++)
                globalVariable.test.log(Status.INFO, iTestResult.getThrowable().getCause().getStackTrace()[i].toString());
        } else
            logWithScreenshot(null, description, "ERROR");

    }

    public void logWithScreenshot(WebElement Obj, String description, String result) {
        File source = null;
        if (Obj != null) {
            if (globalVariable.driver instanceof JavascriptExecutor) {
                ((JavascriptExecutor) globalVariable.driver).executeScript("arguments[0].style.border='3px solid red'", Obj);
            }
        }
        try {
            WebDriver augmentedDriver = new Augmenter().augment(globalVariable.driver);
            source = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
        } catch (Exception e) {
            globalVariable.test.log(Status.WARNING, "Cannot initialize Augmented Driver for Chrome Driver");
            globalVariable.logger.warn("Cannot copy Raw screenshot to directory");
        }


        if (Obj != null) {
            if (globalVariable.driver instanceof JavascriptExecutor) {
                ((JavascriptExecutor) globalVariable.driver).executeScript("arguments[0].style.border=''", Obj);
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmssSS").format(Calendar.getInstance().getTime());
        String filePath = System.getProperty("user.dir") + "\\test-output\\extentReports\\" + timeStamp + ".png";

        //Call getScreenshotAs method to create image file

        try {
            if (source != null)
                FileUtils.copyFile(source, new File(filePath));
        } catch (Exception e) {
            globalVariable.test.log(Status.WARNING, "Cannot copy Raw screenshot to directory");
            globalVariable.logger.warn("Cannot copy Raw screenshot to directory");
        }

        try {
            if (result.compareTo("PASS") == 0) {
                //globalVariable.test.log(Status.PASS, description, MediaEntityBuilder.createScreenCaptureFromPath(filePath).build());
                globalVariable.test.log(Status.PASS, description, MediaEntityBuilder.createScreenCaptureFromPath(timeStamp + ".png").build());
                globalVariable.logger.info("PASS: " + description.replace("<b>", "").replace("</b>", "").replace("<br />", "\r\n"));
            }
            if (result.compareTo("FAIL") == 0) {
                globalVariable.test.log(Status.FAIL, description, MediaEntityBuilder.createScreenCaptureFromPath(timeStamp + ".png").build());
                globalVariable.logger.info("FAIL: " + description.replace("<b>", "").replace("</b>", "").replace("<br />", "\r\n"));
            }
            if (result.compareTo("INFO") == 0) {
                globalVariable.test.log(Status.INFO, description, MediaEntityBuilder.createScreenCaptureFromPath(timeStamp + ".png").build());
                globalVariable.logger.info("ACTION: " + description.replace("<b>", "").replace("</b>", "").replace("<br />", "\r\n"));
            }
            if (result.compareTo("ERROR") == 0) {
                globalVariable.test.log(Status.ERROR, description, MediaEntityBuilder.createScreenCaptureFromPath(timeStamp + ".png").build());
                globalVariable.logger.error("ERROR: " + description);
            }
            if (result.compareTo("WARNING") == 0) {
                globalVariable.test.log(Status.WARNING, description, MediaEntityBuilder.createScreenCaptureFromPath(timeStamp + ".png").build());
                globalVariable.logger.error("WARNING: " + description);
            }
        } catch (Exception e) {
            globalVariable.test.log(Status.ERROR, e.getCause().toString());
            globalVariable.logger.error(e.getCause().toString());
        }
    }

    public boolean isAlertPresent() {
        try {
            globalVariable.driver.switchTo().alert();
            return true;
        }// try
        catch (Exception e) {
            return false;
        }// catch
    }

    public void delay(int millisec) {
        globalVariable.logger.info("DELAY: " + "Delaying for " + Double.valueOf(millisec) / 1000 + " seconds");
        try {
            Thread.sleep(millisec);
        } catch (Exception e) {
            globalVariable.commonFunc.logInfo(null, "Exception - Trying to implement sleep request, CommonFunc.Java|isAlertPresent");
        }
    }
}
