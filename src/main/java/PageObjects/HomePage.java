package PageObjects;
import Services.Action;
import Services.GlobalVariables;
import Services.Verification;
import org.json.JSONObject;
import org.openqa.selenium.*;

import java.util.List;

public class HomePage {
    //**********GLOBAL OBJECTS************
    private GlobalVariables globalVariables;
    private final Action action;
    private final Verification verification;
    //**********Page Objects *************

//  Home/Search Page
    private final By SearchButton = By.xpath("//button[normalize-space()='Search']");
    private final By GoingTo = By.xpath("//button[@aria-label='Going to']");
    private final By WhereAreYouGoing = By.xpath("//input[@id='location-field-destination']");
    private final By SearchResults = By.xpath("//div[@class='uitk-tabs-content']//li//button");
    private final By CheckIn = By.xpath("//label[@for='d1']//following-sibling::button[@data-name='d1']");
    private final By CheckOut = By.xpath("//label[@for='d2']//following-sibling::button[@data-name='d2']");
    private final By Travelers = By.xpath("//label[normalize-space()='Travelers']//following-sibling::button");
    private final By AddFlight = By.xpath("//label[@for='add-flight-switch']/ancestor::div[2]/input");
    private final By AddCar = By.xpath("//label[@for='add-car-switch']/ancestor::div[2]/input");

    public HomePage(GlobalVariables gv) {
        globalVariables = gv;
        action = new Action(globalVariables);
        verification = new Verification(globalVariables);
    }

    public Boolean IsAt(){
        WebElement pageHeading = globalVariables.commonFunc.findElement(SearchButton);
        if (pageHeading != null){
            return true;
        }
        return false;
    }

    public void Search(JSONObject jsonObject){
        if (!jsonObject.getString("GoingTo").equals("null"))
        {
                action.click(globalVariables.commonFunc.findElement(GoingTo));
                globalVariables.commonFunc.findElement(WhereAreYouGoing).clear();
                action.setTextBoxValue(
                        globalVariables.commonFunc.findElement(WhereAreYouGoing),
                        jsonObject.getString("GoingTo"));
                action.waitInMilliSec(2000);
                List<WebElement> listOfElements = globalVariables.commonFunc.findElements(SearchResults);
                action.click(listOfElements.get(0));
        }
        action.click(globalVariables.commonFunc.findElement(SearchButton));
    }
}

