package pragmaticdevelopment.com.honeydue.DBSource;

/**
 * Created by Scott Legrove on 3/15/2016.
 */

//  ** NOTE: ** overloaded constructors, shared users and a self user have different data available to them, overloaded with default values is required.
public class UserModel {

    private String uName;
    private String uToken = "";
    private int id = 0;

    // Constructor for collab users, they do not have tokens available
    public UserModel(int id, String userName){
        this.id = id;
        this.uName = userName;
    }

    // Constructor for self user, does not have user id available
    public UserModel(String username, String userToken){

        this.uName = username;
        this.uToken = userToken;
    }

    public String getUToken(){
        return this.uToken;
    }

    public String getUserName(){
        return this.uName;
    }

    public int getUID(){ return this.id; }
}
