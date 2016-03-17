package pragmaticdevelopment.com.honeydue.DBSource;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Scott Legrove on 3/17/2016.
 */
public class TaskListsModel {

    private Map<Integer, String> taskLists;

    public TaskListsModel(String userToken){
        String uToken = APIConsumer.getListsJson(userToken);
        try{
            JSONObject list = new JSONObject(uToken);
            JSONArray listContents = list.getJSONArray("lists");
            taskLists = new HashMap<Integer, String>();

            for(int i = 0; i < listContents.length(); i++){
                Integer id = Integer.parseInt(listContents.getJSONObject(i).getString("id"));
                String title = listContents.getJSONObject(i).getString("title");
                taskLists.put(id, title);
            }
        }catch(Exception e){
            e.getMessage();
        }
    }

    // Returns a map(similar to associative array) of all lists associated with a user. index->value = list title;
    public Map<Integer, String> getTaskLists(){
        return this.taskLists;
    }
}
