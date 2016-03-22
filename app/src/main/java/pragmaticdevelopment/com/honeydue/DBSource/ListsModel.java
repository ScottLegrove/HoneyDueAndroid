package pragmaticdevelopment.com.honeydue.DBSource;

/**
 * Created by Scott Legrove on 3/17/2016.
 */
public class ListsModel {

    private int id;
    private String title;

    public ListsModel(int id, String title){
        this.id = id;
        this.title = title;
    }

    public int getId(){
        return this.id;
    }

    public String getTitle(){
        return this.title;
    }
}
