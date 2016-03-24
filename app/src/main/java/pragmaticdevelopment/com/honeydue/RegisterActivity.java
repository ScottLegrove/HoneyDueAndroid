package pragmaticdevelopment.com.honeydue;

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

import org.w3c.dom.Text;

import static pragmaticdevelopment.com.honeydue.HelperClasses.UserHelper.registerUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText email;
    private EditText pwPrimary;
    private EditText pwSecondary;

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
        }else if (!TextUtils.isEmpty(pwPrim) || !TextUtils.isEmpty(pwSec)){
            pwPrimary.setError("Please enter a password");
            pwPrimary.requestFocus();
        }else if (!TextUtils.equals(pwPrim, pwSec)){
            pwPrimary.setError("Passwords do not match");
            pwPrimary.requestFocus();
        }else{
            // Wrap in IF this is true, go to main activity, ELSE focusView = username;
            registerUser(un, pwPrim, uEmail);
        }
    }
}
