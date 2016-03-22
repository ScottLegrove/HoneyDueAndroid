package pragmaticdevelopment.com.honeydue.HelperClasses;

import org.json.JSONException;
import org.json.JSONObject;

import pragmaticdevelopment.com.honeydue.DBSource.APIConsumer;
import pragmaticdevelopment.com.honeydue.DBSource.UserModel;

/**
 * Created by Scott Legrove on 3/17/2016.
 */
public class UserHelper {

    // Returns a User Model of the user how owns the app. * NOTE: * user token will contain an error message if the user was not found. *
    public static UserModel getUser(String username, String password){
        String uToken = UserHelper.getUserToken(username, password);
        UserModel uModel = new UserModel(username, uToken);

        return uModel;
    }

    // gets user token to build the app owner User Model
    private static String getUserToken(String username, String password){
        try{
            String json = APIConsumer.getLoginJson(username, password);
            JSONObject user = new JSONObject(json);
            return user.getString("token");
        }catch(JSONException e){
            return e.getMessage();
        }
    }

    //TODO SCOTT: handle an reg exp email validator
    // Creates a record to register a new user.
    public static void registerUser(String username, String password, String email){
        APIConsumer.doRegisterJson(username, password, email);
    }

    // Returns a boolean to validate whether the user credentials are correct. ***Also UserModel itself can be used for login***
    // TODO SCOTT: this needs to be modified as if they aren't valid i'll receive an error message not a null value.
    public static boolean validateCredentials(String username, String password){
        try{
            String json = APIConsumer.getLoginJson(username, password);
            JSONObject validation = new JSONObject(json);

            if(validation.getString("token") != null){
                return true;
            }else{
                return false;
            }
        }catch (JSONException e){
            e.getMessage();
            return false;
        }
    }

    //TODO NOTE SCOTT:need an api method to update a password
    //public static void changePassword(String uToken, String newPassword){

    //}

    //TODO NOTE SCOTT: need an api method to update an email
    //public static void changeEmail(String uToken, String newEmail){

    //}
}
