package PageObjects;

import Services.Action;
import Services.GlobalVariables;
import Services.Verification;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchResultsPage {
    //**********GLOBAL OBJECTS************
    private GlobalVariables globalVariables;
    private final Action action;
    private final Verification verification;
    //**********Page Objects *************

//  Search Page elements
    private final By SortBy = By.xpath("//select[@id='sort']");
    private final By SearchByPropertyName = By.xpath("//legend[normalize-space()='Search by property name']");
    private final By FilterBy = By.xpath("//h3[normalize-space()='Filter by']");
    private final By PaymentType = By.xpath("//h4[normalize-space()='Payment type']");

    public SearchResultsPage(GlobalVariables gv) {
        globalVariables = gv;
        action = new Action(globalVariables);
        verification = new Verification(globalVariables);
    }

    public void Assertions(JSONObject jsonObject){
        if (!jsonObject.getString("SortBy").equals("null")){
            verification.verifyDropDownValue(globalVariables.commonFunc.findElement(SortBy),jsonObject.getString("SortBy"));
            verification.verifyObjText(globalVariables.commonFunc.findElement(FilterBy),jsonObject.getString("FilterBy"),jsonObject.getString("FilterBy"));
            verification.verifyObjText(globalVariables.commonFunc.findElement(PaymentType),jsonObject.getString("PaymentType"),jsonObject.getString("PaymentType"));
            verification.verifyObjText(globalVariables.commonFunc.findElement(SearchByPropertyName),jsonObject.getString("SearchByPropertyName"),jsonObject.getString("SearchByPropertyName"));
        }
    }
}

