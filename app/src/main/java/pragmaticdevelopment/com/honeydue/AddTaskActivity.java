package pragmaticdevelopment.com.honeydue;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import pragmaticdevelopment.com.honeydue.HelperClasses.TaskHelper;

public class AddTaskActivity extends AppCompatActivity {
    private int listId;
    private String uToken;
    private String LIST_TITLE_EXTRA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Task");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.listId = getIntent().getExtras().getInt("listId");
        this.uToken = getIntent().getExtras().getString("uToken");
        this.LIST_TITLE_EXTRA = getIntent().getStringExtra("LIST_TITLE_EXTRA");
        Button button = (Button) findViewById(R.id.btnSubmitTask);

        button.setOnClickListener(new View.OnClickListener(){
           int id = listId;
           String token = uToken;

            public void onClick(View view){
                EditText title = (EditText)findViewById(R.id.editTxtTaskTitle);
                EditText desc = (EditText)findViewById(R.id.editTxtTaskDesc);
                DatePicker datePicker = (DatePicker)findViewById(R.id.datePickTaskDate);

                Calendar cal = GregorianCalendar.getInstance();
                cal.set(datePicker.getMonth(), datePicker.getDayOfMonth(), 1900 + datePicker.getYear());
                Date date = cal.getTime();
                TaskHelper.createListItem(id, title.getText().toString(),desc.getText().toString(), date, token);

                Toast.makeText(getApplicationContext(), "Task Succesfully Added", Toast.LENGTH_LONG).show();
                //finish();

                Intent intent = new Intent(getApplicationContext(), TaskListActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("LIST_ID_EXTRA", listId);
                intent.putExtra("LIST_TITLE_EXTRA", LIST_TITLE_EXTRA);
                startActivity(intent);
            }
        });
    }
}
