package pragmaticdevelopment.com.honeydue.HelperClasses;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pragmaticdevelopment.com.honeydue.DBSource.APIConsumer;

/**
 * Created by Scott Legrove on 3/17/2016.
 */
public class ListHelper {

    // Create a single list.
    public static void createList(String title, String uToken){
        APIConsumer.createListJson(title, uToken);
    }

    // Deletes a single list
    public static void deleteList(int id, String token){
        APIConsumer.deleteListJson(id, token);
    }

    // Removes a single collaborative user from the an associated list.
    // ***User id's can be obtained via (the index) getCollabUsers method***
    public static void removeCollabUserFromList(int listId, int userId, String token){
        APIConsumer.removeUserJson(listId, userId, token);
    }

    // Returns parsed json object of a a single list.
    public static JSONObject getSingleList(int id, String uToken){
        String json = APIConsumer.getListJson(id, uToken);
            try {
                JSONObject list = new JSONObject(json);
                return list;
            }catch (JSONException e) {
                e.getMessage();
            }
        return null;
    }

    // Returns a parsed json object of a single, specified, collaborative user associated with a list.
    public static JSONObject getSingleCollabUser(int listId, int userId, String uToken){
        String json = APIConsumer.getUserJson(listId, userId, uToken);
            try{
                JSONObject user = new JSONObject(json);
                return user;
            }catch (JSONException e){
                e.getMessage();
            }
       return null;
    }

    // Returns a map - user id as index and username. Of all the collaborative users associated with a list.
    public static Map<Integer, String> getCollabUsers(int listId, String uToken){
        String json = APIConsumer.getUsersJson(listId, uToken);
            try{
                Map<Integer, String> collabUsers = new HashMap<Integer, String>();
                JSONObject cUsers =  new JSONObject(json);
                JSONArray jUsers = cUsers.getJSONArray("items");

                for(int i = 0; i < jUsers.length(); i++){
                    collabUsers.put(jUsers.getJSONObject(i).getInt("id"), jUsers.getJSONObject(i).getString("username"));
                }

                return collabUsers;
            }catch (JSONException e){
                e.getMessage();
            }
       return null;
    }

    // Removes a collaborative user from a list
    public static void removeCollabUser(int listId, int userId, String uToken){
        APIConsumer.removeUserJson(listId, userId, uToken);
    }
}
