package pragmaticdevelopment.com.honeydue.DBSource;

/**
 * Created by Scott Legrove on 3/17/2016.
 */
public class TaskModel {

    private int id;
    private int listId;
    private String title;
    private String description;
    private String dueDate;
    private Boolean complete;

    public TaskModel(int id, int listId, String title, String description, String dueDate, Boolean complete){
        this.id = id;
        this.listId =  listId;
        this.title = title;
        this.description = description;
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

    public String getDescription() {
        return this.description;
    }

    public String getDueDate() {
        return this.dueDate;
    }

    public Boolean getComplete() { return this.complete; }
}
