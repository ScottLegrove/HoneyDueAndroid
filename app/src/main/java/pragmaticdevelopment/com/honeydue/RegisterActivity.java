package pragmaticdevelopment.com.honeydue;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

import static pragmaticdevelopment.com.honeydue.HelperClasses.UserHelper.registerUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText email;
    private EditText pwPrimary;
    private EditText pwSecondary;

    RegisterUserTask ruTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Register");
        setSupportActionBar(toolbar);

        username = (EditText) findViewById(R.id.txtUsername);
        email = (EditText) findViewById(R.id.txtEmail);
        pwPrimary = (EditText) findViewById(R.id.txtPWPrimary);
        pwSecondary = (EditText) findViewById(R.id.txtPWSecondary);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button register = (Button) findViewById(R.id.btnRegisterSubmit);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });
    }

    private void attemptRegister() {
        if(ruTask != null){
            return;
        }

        // cancel flag
        boolean cancel = false;
        View focusView = null;

        // reset any errors
        username.setError(null);
        email.setError(null);
        pwPrimary.setError(null);

        // Get values
        String un = username.getText().toString();
        String uEmail = email.getText().toString();
        String pwPrim = pwPrimary.getText().toString();
        String pwSec = pwSecondary.getText().toString();

        // Check username isn't empty
        if (TextUtils.isEmpty(un)) {
            username.setError("Invalid Username");
            username.requestFocus();
        } else if (TextUtils.isEmpty(uEmail)) {
            email.setError("Please enter an email");
            email.requestFocus();
        } else if (!uEmail.contains("@")) {
            email.setError("Invalid Email");
            focusView = email;
        }else if (TextUtils.isEmpty(pwPrim)){
            pwPrimary.setError("Please enter a password");
            pwPrimary.requestFocus();
        }else if (TextUtils.isEmpty(pwSec)) {
            pwSecondary.setError("Please confirm your password");
            pwSecondary.requestFocus();
        }else if (!TextUtils.equals(pwPrim, pwSec)){
            pwPrimary.setError("Passwords do not match");
            pwPrimary.requestFocus();
        }else{
            ruTask = new RegisterUserTask(un, uEmail, pwPrim);
            ruTask.execute((Void) null);
        }
    }

    private class RegisterUserTask extends AsyncTask<Void, Void, Boolean>{
        private final String mUsername;
        private final String mEmail;
        private final String mPassword;

        RegisterUserTask(String username, String email, String password){
            mUsername = username;
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try{
                return registerUser(mUsername, mPassword, mEmail);
            }catch(Exception e){
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success){
            ruTask = null;

            if(success){
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                i.setFlags(i.FLAG_ACTIVITY_NEW_TASK | i.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }else{
                Toast.makeText(getApplicationContext(), "Invalid credentials, please try again", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            ruTask = null;
        }
    }
}
