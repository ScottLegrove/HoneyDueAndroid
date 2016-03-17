package pragmaticdevelopment.com.honeydue.DBSource;

/**
 * Created by Scott Legrove on 3/17/2016.
 */
public class TaskModel {

    // **** This is the representative object of a single task ****
    private int id;
    private int listId;
    private String title;
    private String content;
    private String dueDate;
    private Boolean complete;

    public TaskModel(int id, int listId, String title, String content, String dueDate, Boolean complete){
        this.id = id;
        this.listId =  listId;
        this.title = title;
        this.content = content;
        this.dueDate = dueDate;
        this.complete = complete;
    }

    public int getId() {
        return this.id;
    }

    public int getListId(){
        return this.listId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public String getDueDate() {
        return this.dueDate;
    }

    public Boolean getComplete() {
        return this.complete;
    }
}
