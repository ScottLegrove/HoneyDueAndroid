package pragmaticdevelopment.com.honeydue;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TaskListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Task List");
        setSupportActionBar(toolbar);

        int listId;
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            listId = extras.getInt("LIST_ID_EXTRA");
        } else {
            listId = 9001;
        }

        TextView taskId = (TextView) this.findViewById(R.id.lblTaskListID);
        taskId.setText(Integer.toString(listId));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
