package Services;

import Drivers.*;
import Reports.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class GlobalVariables {
    public WebDriver driver;
    public ExtentTest test;
    public ExtentTest testSuite;
    public ExtentTest testTest;
    public ExtentTest testStep;
    public ExtentTest testSubStep;
    public ExtentReports extent;
    public String className;
    public static volatile ExtentManager extentManager;
    public SoftAssert softAssertion;
    public static Logger logger;
    public final String logLevel;
    public CommonFunc commonFunc;
    public String environment=null;
    public String testName;

    public String emailRecepient=null;
    public String senderEmail=null;
    public String senderPassword=null;

    public String browser;
    public static String platform=null;
    public int objTimeout=30;
    public boolean skipFlag=false;
    public String suiteName=null;

    public String URL;

    public GlobalVariables(ExtentManager extentManager2) {

        commonFunc = new CommonFunc(this);
        Properties properties = new Properties();
        try {
            FileInputStream objfile = new FileInputStream(System.getProperty("user.dir") + "\\application.properties");
            properties.load(objfile);
        }
        catch (Exception e){
            System.out.println("Exception - Trying to read application.properties file, GlobalVariables.Java|GlobalVariables");
        }
        systemVariablesUpdate(properties);
        className="";
        logLevel= properties.getProperty("logLevel");
        if (environment==null) {
            environment = properties.getProperty("Environment");
        }
        if (platform==null){
            platform= properties.getProperty("platform");
        }
        if (environment.equals("DEV")){
            URL = "Dev URL Here";
        }
        else if (environment.equals("QA")) {
            URL = "https://www.orbitz.com/";

        }
        else if (environment.equals("PRODUCTION")) {
            URL = "Prod URL Here";

        }
        else
            System.out.println("FAILED: ENVIRONMENT");

        setBrowser();
        extentManager=extentManager2;
        extent=extentManager.GetExtent();
    }

    public ExtentTest getTest(){
        return test;
    }

    private void automationVariablesUpdate(Properties obj){
        senderEmail =obj.getProperty("senderEmail");
        senderPassword =obj.getProperty("senderPassword");
        emailRecepient =obj.getProperty("emailRecepient");
    }

    private void systemVariablesUpdate(Properties obj){
        browser=obj.getProperty("browser");
        environment = System.getProperty("Environment");
        platform = System.getProperty("platform");
    }

    private void setBrowser(){
            Chrome chromeHandler = new Chrome();
            driver = chromeHandler.chromeDriver();

        this.driver=driver;
        driver.manage().timeouts().implicitlyWait(this.objTimeout, TimeUnit.SECONDS);
    }
}
