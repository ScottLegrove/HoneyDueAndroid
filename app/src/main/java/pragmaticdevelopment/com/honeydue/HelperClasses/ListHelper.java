package pragmaticdevelopment.com.honeydue.HelperClasses;

import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pragmaticdevelopment.com.honeydue.DBSource.APIConsumer;
import pragmaticdevelopment.com.honeydue.DBSource.ListsModel;
import pragmaticdevelopment.com.honeydue.DBSource.UserModel;

/**
 * Created by Scott Legrove on 3/17/2016.
 */
public class ListHelper {

    public static ListsModel[] getAllLists(String userToken){
        try{
            String uToken = APIConsumer.getListsJson(userToken);
            JSONObject list = new JSONObject(uToken);
            JSONArray listContents = list.getJSONArray("lists");
            ListsModel[] lModel= new ListsModel[listContents.length()];

            for(int i = 0; i < lModel.length; i++){
                ListsModel listsModel = new ListsModel(listContents.getJSONObject(i).getInt("id"), listContents.getJSONObject(i).getString("title"));
                lModel[i] = listsModel;
            }

            return lModel;
        }catch (JSONException e) {
            e.getMessage();
            return null;
        }
    }

    // Create a single list.
    public static void createList(String title, String uToken){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try{
            APIConsumer.createListJson(title, uToken);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    // Deletes a single list
    public static void deleteList(int id, String token){
        APIConsumer.deleteListJson(id, token);
    }

    // Removes a single collaborative user from the an associated shared list. it requires the "shared users" user id. the "self" users token and the list id
    public static void removeCollabUserFromList(int listId, int userId, String token){
        APIConsumer.removeUserJson(listId, userId, token);
    }

    // Returns a List Model of a single list, requires users token
    public static ListsModel getSingleList(int listID, String uToken){
        try{
            String json = APIConsumer.getListJson(listID, uToken);
            JSONObject list = new JSONObject(json);

            ListsModel listModel = new ListsModel(list.getJSONObject("list").getInt("id"), list.getJSONObject("list").getString("title"));
            return listModel;
        }catch (JSONException e){
            e.getMessage();
            return null;
        }
    }

    // Returns a User Model of a single "collaborative" user on a shared list. requires "Collaborateive" users
    public static UserModel getSingleCollabUser(int listId, int collaborativeUserId, String uToken){
        try{
            String json = APIConsumer.getUserJson(listId, collaborativeUserId, uToken);
            JSONObject user = new JSONObject(json);

            UserModel cUser = new UserModel(user.getInt("id"), user.getString("username"));

            return cUser;
        }catch (JSONException e){
            e.getMessage();
            return null;
        }
    }

    // Returns an array of User Model objects with all the users shared on a list.
    public static UserModel[] getCollabUsers(int listId, String uToken){
        try{
            String json = APIConsumer.getUsersJson(listId, uToken);
            JSONObject cUsers =  new JSONObject(json);
            JSONArray jUsers = cUsers.getJSONArray("items");
            UserModel[] collabUsers = new UserModel[jUsers.length()];

            for(int i = 0; i < collabUsers.length; i++){
                UserModel user = new UserModel(jUsers.getJSONObject(i).getInt("id"), jUsers.getJSONObject(i).getString("username"));
                collabUsers[i] = user;
            }

            return collabUsers;
        }catch (JSONException e) {
            e.getMessage();
            return null;
        }
    }
}
