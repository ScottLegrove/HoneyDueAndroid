package pragmaticdevelopment.com.honeydue.HelperClasses;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import pragmaticdevelopment.com.honeydue.DBSource.APIConsumer;
import pragmaticdevelopment.com.honeydue.DBSource.TaskModel;

/**
 * Created by Scott Legrove on 3/17/2016.
 */
public class TaskHelper {

    //TODO Scott: need to parse date before inserting within method don't rely on precondition.
    // Creates a single task in a list.*** Be sure to parse the date as a precondition.***
    public static void createListItem(int listId, String title, String content, Date dueDate, String uToken){
        APIConsumer.createListItemJson(listId, title, content, dueDate, uToken);
    }

    // Deletes a single task in a list.
    public static void deleteListItem(int listId, int itemId, String uToken){
        APIConsumer.deleteListItemJson(listId, itemId, uToken);
    }

    // Returns an object of a single, specified, task.
    public static TaskModel getSingleTaskItem(int listId, int itemId, String uToken){
        try{
            String json = APIConsumer.getListItemJson(listId, itemId, uToken);
            JSONObject user = new JSONObject(json);

            TaskModel task=  new TaskModel(user.getInt("listId"), user.getInt("id"), user.getString("title"), user.getString("content"),
                    user.getString("dueDate"), user.getBoolean("complete"));

            return task;
        }catch (JSONException e){
            e.getMessage();
            return null;
        }
    }

    // Returns an array of Task Model objects
    public static TaskModel[] getAllTasks(int listId, String uToken){
        try{
            String json = APIConsumer.getListItemsJson(listId, uToken);
            JSONObject jTasks = new JSONObject(json);
            JSONArray jTasksArray = jTasks.getJSONArray("items");

            TaskModel[] taskListItems = new TaskModel[jTasksArray.length()];
            int id;
            int jListId;
            String title;
            String content;
            String targetDate;
            Boolean complete;

                for(int i = 0; i < taskListItems.length; i++){
                    id = Integer.parseInt(jTasksArray.getJSONObject(i).getString("id"));
                    jListId = Integer.parseInt(jTasksArray.getJSONObject(i).getString("listId"));
                    title = jTasksArray.getJSONObject(i).getString("title");
                    content = jTasksArray.getJSONObject(i).getString("content");
                    targetDate = jTasksArray.getJSONObject(i).getString("dueDate");
                    complete = jTasksArray.getJSONObject(i).getBoolean("complete");

                    TaskModel task = new TaskModel(id, jListId, title, content, targetDate, complete);
                    taskListItems[i] = task;
                }

            return taskListItems;
        }catch (JSONException e){
            e.getMessage();
            return null;
        }
    }
}
