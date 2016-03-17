package pragmaticdevelopment.com.honeydue.HelperClasses;

import org.json.JSONObject;

import pragmaticdevelopment.com.honeydue.DBSource.APIConsumer;

/**
 * Created by Scott Legrove on 3/17/2016.
 */
public class UserHelper {

    //TODO SCOTT: handle an reg exp email validator
    // Creates a record to register a new user.
    public static void registerUser(String username, String password, String email){
        APIConsumer.doRegisterJson(username, password, email);
    }

    // Returns a boolean to validate whether the user credentials are correct. ***Also UserModel itself can be used for login***
    public static boolean validateCredentials(String username, String password){
        String json = APIConsumer.getLoginJson(username, password);
            try {
                JSONObject validation = new JSONObject(json);

                if (validation.getString("token") != null)
                    return true;
            }catch(Exception e){
                e.getMessage();
            }
        return  false;
    }

    //TODO NOTE SCOTT: need an api method to update a password
    //public static void changePassword(String uToken, String newPassword){

    //}

    //TODO NOTE SCOTT: need an api method to update an email
    //public static void changeEmail(String uToken, String newEmail){

    //}
}
