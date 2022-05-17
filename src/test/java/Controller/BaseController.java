package Controller;

import Services.*;
import org.apache.log4j.PropertyConfigurator;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebDriver;
import org.testng.IHookCallBack;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import org.testng.internal.TestResult;


@Listeners(Listener.class)
public class BaseController implements org.testng.IHookable{
    private WebDriver driver;
    protected volatile GlobalVariables globalVariables;

    public BaseController(){
    }

    @BeforeSuite()
    public void Starter(ITestContext context) {     //Starter starts the browser on which testing is to be conducted
        globalVariables=GlobalVariablesManager.objectCreationMethod().getGlobalVariable();
        String log4jConfPath = System.getProperty("user.dir") + "/log4j2.properties";
        PropertyConfigurator.configure(log4jConfPath);
        System.out.println(java.lang.Thread.currentThread());
        System.out.println(java.lang.Thread.activeCount());
        globalVariables.suiteName=context.getSuite().getName();
    }

    @BeforeClass
    public  void beforeClassrun(){
    }

    @BeforeTest()
    public void testStarter(ITestContext testContext){
        globalVariables=GlobalVariablesManager.objectCreationMethod().getGlobalVariable();
        globalVariables.testName=testContext.getName();
    }

    @AfterMethod()
    public void logoutAfterTest(){

    }

    @AfterSuite()
    public void Closer() throws Exception {//kills browser after batch execution is complete
        SendMailUtil sendMailUtil=new SendMailUtil();
       globalVariables.driver.close();
        try{globalVariables.driver.quit();
            sendMailUtil.sendMail(globalVariables.senderEmail, globalVariables.senderPassword,globalVariables.emailRecepient);
        }
        catch (Exception e){
            globalVariables.commonFunc.logInfo(null,"Exception - Trying to quit driver after closing and sending email, BaseController.Java|Closer");
        }
        globalVariables.driver=null;

    }

    public void run(IHookCallBack iHookCallBack, ITestResult iTestResult) {
        //****BEFORE START OF TEST CASE*************
        globalVariables.softAssertion=new SoftAssert();
        if (globalVariables.testSuite==null || !globalVariables.testName.equals(globalVariables.className)) {
            globalVariables.testSuite = globalVariables.extent.createTest(globalVariables.testName);
            globalVariables.className=globalVariables.testName;
        }
        globalVariables.test=globalVariables.testSuite.createNode(iTestResult.getMethod().getMethodName());
        globalVariables.testTest=globalVariables.test;
        globalVariables.testStep=globalVariables.test;
        globalVariables.testSubStep=globalVariables.test;
        globalVariables.test.assignCategory(globalVariables.testName);
        globalVariables.logger= LogManager.getLogger(iTestResult.getClass());
        globalVariables.skipFlag=false;
        globalVariables.logger.info(globalVariables.suiteName+" | "+"******************************************************");
        globalVariables.logger.info(globalVariables.suiteName+" | "+"************Starting Test Case: " + iTestResult.getMethod().getMethodName() + "************");
        globalVariables.logger.info(globalVariables.suiteName+" | "+"******************************************************");
        iHookCallBack.runTestMethod(iTestResult);   //****TEST CASE EXECUTING************
        //****AFTER TEST CASE*************
        if (iTestResult.getThrowable()!=null){
            if (!iTestResult.getThrowable().getCause().getClass().toString().equals("class java.lang.AssertionError"))
                globalVariables.commonFunc.logError(iTestResult,"");
        }
        if (globalVariables.skipFlag){
            iTestResult.setStatus(TestResult.SKIP);
        }
        globalVariables.extentManager.GetExtent().flush();
        globalVariables.softAssertion.assertAll();
    }

}
