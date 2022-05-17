import Controller.BaseController;
import PageObjects.*;
import PageObjects.Template.SearchJsonTemplate;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class Test_SearchResultsPage extends BaseController {


    @Test(priority = 1,  description = "Verify Searched Results Page")
    public void VerifySearchedResultsPage(){
        HomePage homePage = new HomePage(globalVariables);
        SearchResultsPage searchResultsPage = new SearchResultsPage(globalVariables);
        globalVariables.commonFunc.getUrl(globalVariables.URL);

        assert homePage.IsAt();
        homePage.Search(search());
        searchResultsPage.Assertions(search());
    }

    public JSONObject search(){
        SearchJsonTemplate obj = new SearchJsonTemplate();
        obj.GoingTo = "Medina";
        obj.SortBy = "Recommended";
        obj.FilterBy= "Filter by";
        obj.PaymentType = "Payment type";
        obj.SearchByPropertyName = "Search by property name";
        return obj.jsonCreation();
    }
}
