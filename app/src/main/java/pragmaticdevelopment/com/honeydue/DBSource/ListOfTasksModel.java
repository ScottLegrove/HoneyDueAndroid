package pragmaticdevelopment.com.honeydue.DBSource;

import java.util.HashMap;
import java.util.Map;
import org.json.*;
/**
 * Created by Scott Legrove on 3/17/2016.
 */
public class ListOfTasksModel {

    private Map<Integer,TaskModel> taskListItems;

    public ListOfTasksModel(int listId, String userToken){
        String tasks = APIConsumer.getListItemsJson(listId, userToken);
        try {
            JSONObject jTasks = new JSONObject(tasks);
            JSONArray jTasksContents = jTasks.getJSONArray("items");
            taskListItems = new HashMap<Integer, TaskModel>();
            int id;
            int jListId;
            String title;
            String content;
            String targetDate;
            Boolean complete;

            for (int i = 0; i < jTasksContents.length(); i++) {
                id = Integer.parseInt(jTasksContents.getJSONObject(i).getString("id"));
                jListId = Integer.parseInt(jTasksContents.getJSONObject(i).getString("listId"));
                title = jTasksContents.getJSONObject(i).getString("title");
                content = jTasksContents.getJSONObject(i).getString("content");
                targetDate = jTasksContents.getJSONObject(i).getString("dueDate");
                complete = jTasksContents.getJSONObject(i).getBoolean("complete");

                taskListItems.put(id, new TaskModel(id, jListId, title, content, targetDate, complete));
            }
        }catch(Exception e){
            e.getMessage();
        }
    }

    // Returns a map (similar to associative array) of all the tasks in a list. the index will return a task object itself.
    public Map<Integer, TaskModel> getTaskListItems() {
        return this.taskListItems;
    }
}
