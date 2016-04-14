package pragmaticdevelopment.com.honeydue;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Date;

import pragmaticdevelopment.com.honeydue.HelperClasses.TaskHelper;

public class AddTaskActivity extends AppCompatActivity {
    private int listId;
    private String uToken;

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
        Button button = (Button) findViewById(R.id.btnSubmitTask);

        button.setOnClickListener(new View.OnClickListener(){
           int id = listId;
           String token = uToken;

            public void onClick(View view){
                EditText title = (EditText)findViewById(R.id.editTxtTaskTitle);
                EditText desc = (EditText)findViewById(R.id.editTxtTaskDesc);
                DatePicker datePicker = (DatePicker)findViewById(R.id.datePickTaskDate);
                Date date = new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth());
                TaskHelper.createListItem(id, title.getText().toString(),desc.getText().toString(), date, token);
            }
        });
    }
}
