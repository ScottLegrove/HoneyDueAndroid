package pragmaticdevelopment.com.honeydue.DBSource;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Scott Legrove on 3/15/2016.
 */
public class UserModel {

    private String uToken;

    public UserModel(String username, String password){
        String json = APIConsumer.getLoginJson(username, password);
        try{
            JSONObject user = new JSONObject(json);
            this.uToken = user.getString("token");
        }catch(JSONException e){
            e.getMessage();
        }

    }

    // All identifiers for a user us an identification token to validate via the api.
    // The only requisite needed for a user after authentication is the use of this token.
    public String getUToken(){
        return this.uToken;
    }
}
