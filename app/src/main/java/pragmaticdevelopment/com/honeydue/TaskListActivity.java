package pragmaticdevelopment.com.honeydue;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

import pragmaticdevelopment.com.honeydue.Adapters.TasksListAdapter;
import pragmaticdevelopment.com.honeydue.DBSource.TaskModel;

import static pragmaticdevelopment.com.honeydue.HelperClasses.TaskHelper.*;

public class TaskListActivity extends AppCompatActivity {
    private ArrayList<TaskModel> tasks;
    private TasksListTask tlTask = null;
    private int listId;
    private String uToken;
    private String listName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Task List");
        toolbar.showOverflowMenu();

        setSupportActionBar(toolbar);

        if(savedInstanceState != null){
            uToken = savedInstanceState.getString("uToken");
            listId = savedInstanceState.getInt("listId");
            listName = savedInstanceState.getString("listName");
        } else {
            // Get user token
            SharedPreferences sp = getSharedPreferences(getString(R.string.shared_pref_id), Context.MODE_PRIVATE);
            uToken = sp.getString("token", null);

            // Get passed list ID
            Bundle extras = getIntent().getExtras();
            listId = extras.getInt("LIST_ID_EXTRA");
            listName = extras.getString("LIST_TITLE_EXTRA");
        }

        // Set title
        TextView listTitle = (TextView) findViewById(R.id.lblListTitle);
        listTitle.setText(listName);

        // Attempt to get Tasks
        tlTask = new TasksListTask(listId, uToken);
        tlTask.execute((Void) null);

        // Wait for tasks to be retrieved;
        try{ tlTask.get(); }catch(Exception e){}

        // Populate ExpandableListView
        ExpandableListView elv = (ExpandableListView)findViewById(R.id.elvTasks);
        elv.setAdapter(new TasksListAdapter(tasks, getLayoutInflater()));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().show();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putString("uToken", uToken);
        savedInstanceState.putInt("listId", listId);
        savedInstanceState.putString("listName", listName);

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        uToken = savedInstanceState.getString("uToken");
        listId = savedInstanceState.getInt("listId");
        listName = savedInstanceState.getString("listName");

        setContentView(R.layout.activity_task_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Task List");
        toolbar.showOverflowMenu();

        setSupportActionBar(toolbar);

        // Set title
        TextView listTitle = (TextView) findViewById(R.id.lblListTitle);
        listTitle.setText(listName);

        // Attempt to get Tasks
        tlTask = new TasksListTask(listId, uToken);
        tlTask.execute((Void)null);

        // Wait for tasks to be retrieved;
        try{ tlTask.get(); }catch(Exception e){}

        // Populate ExpandableListView
        ExpandableListView elv = (ExpandableListView)findViewById(R.id.elvTasks);
        elv.setAdapter(new TasksListAdapter(tasks, getLayoutInflater()));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_list) {
            Intent intent = new Intent(this, AddTaskActivity.class);
            intent.putExtra("listId", this.listId);
            intent.putExtra("uToken", this.uToken);
            startActivity(intent);
            return true;
        }
        return false;
    }

    private class TasksListTask extends AsyncTask<Void, Void, Boolean>{
        private int uListId;
        private String uToken;

        TasksListTask(int listId, String token){
            uListId = listId;
            uToken = token;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try{
                tasks = new ArrayList<TaskModel>(Arrays.asList(getAllTasks(uListId, uToken)));

                return true;
            }catch(Exception e){
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success){
            tlTask = null;

            // If there are no lists
            if(!success) Toast.makeText(getApplicationContext(), "No tasks in this list", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onCancelled(){
            tlTask = null;
        }
    }
}
