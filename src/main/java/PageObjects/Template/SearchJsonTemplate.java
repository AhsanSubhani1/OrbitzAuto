package PageObjects.Template;

import org.json.JSONObject;

public class SearchJsonTemplate {

    public String GoingTo = "null";
    public String SortBy = "null";
    public String SearchByPropertyName = "null";
    public String FilterBy = "null";
    public String PaymentType = "null";


    public JSONObject jsonCreation(){
        JSONObject obj = new JSONObject();
        obj.put("GoingTo", GoingTo);
        obj.put("SortBy", SortBy);
        obj.put("SearchByPropertyName", SearchByPropertyName);
        obj.put("FilterBy", FilterBy);
        obj.put("PaymentType", PaymentType);
        return obj;
    }
}
