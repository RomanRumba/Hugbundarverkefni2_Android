package hi.hugbo.verywowchat.controllers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import hi.hugbo.verywowchat.models.implementations.AccountService;

/**
 * @Author : Róman
 * This Activity is responsible for displaying and maintaining the registration form
 * Taking inputs from the user and forwarding HTTP requests to the api_called
 * */
public class RegisterActivity extends AppCompatActivity {

    /**
     * The Registration form consists of 6 widgets,
     * and we need to have references to them so we could work with them.
     * the letter 'm' in front of the variable indicates that this is a member of this classes activity
     * */
    private TextView mRegisterUsername;
    private TextView mRegisterDisplayName;
    private TextView mRegisterPassword;
    private TextView mRegisterPasswordRepeat;
    private TextView mRegisterEmail;
    private Button mBtnRegister;

    /**
     * The service that will handle the Register in functionality
     * */
    private AccountService mAccountService = AccountService.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // set a referance to all the widgets
        mRegisterUsername = findViewById(R.id.edit_register_username);
        mRegisterDisplayName = findViewById(R.id.edit_register_displayname);
        mRegisterPassword = findViewById(R.id.edit_register_password);
        mRegisterPasswordRepeat = findViewById(R.id.edit_register_password_repeat);
        mRegisterEmail = findViewById(R.id.edit_register_email);

        /*
         *Good practice to reference a object and then assign a listener to it.
         *when the user clicks on the register button we will first check if the
         *form is not empty then the form data will be mapped and passed to the api_controller
         *to make the HTTP request
         **/
        mBtnRegister = findViewById(R.id.btn_register_submit);
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            /*
             * !!!PLEASE NOTE!!!
             *The reason why this onClick function has all the code in it and not delegated
             *to a service f.x is that most of the functionality f.x like Showing Toast, storing user info in sharedpreferances
             *and starting new activity happens in a Activity the only thing that can be delegated to the service is
             *the api call then it would return something and you have to implement somekind of control flow based of the data received
             **/
            @Override
            public void onClick(View v) {
                // if the user left field or fields empty we dont make the HTTP Request
                if(mRegisterDisplayName.getText().toString().isEmpty() ||
                   mRegisterUsername.getText().toString().isEmpty() ||
                   mRegisterPassword.getText().toString().isEmpty() ||
                   mRegisterPasswordRepeat.getText().toString().isEmpty() ||
                   mRegisterEmail.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please fill all the fields",Toast.LENGTH_LONG).show();
                    return;
                }
                // if Both passwords dont Match then we dont make the HTTP Request
                if(!mRegisterPassword.getText().toString().equals(mRegisterPasswordRepeat.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Both passwords must match",Toast.LENGTH_LONG).show();
                    return;
                }

                // Create a Map from the data provided by the user
                Map<String, String> params = new HashMap<String, String>();
                params.put("displayName",mRegisterDisplayName.getText().toString());
                params.put("email",mRegisterEmail.getText().toString());
                params.put("password",mRegisterPassword.getText().toString());
                params.put("passwordReap",mRegisterPasswordRepeat.getText().toString());
                params.put("userName",mRegisterUsername.getText().toString());

                Toast.makeText(getApplicationContext(),mAccountService.Register(params),Toast.LENGTH_LONG).show();
            }
        });
    }

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, RegisterActivity.class);
        return i;
    }
}
