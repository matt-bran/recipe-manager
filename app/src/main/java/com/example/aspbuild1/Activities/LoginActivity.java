package com.example.aspbuild1.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.example.aspbuild1.databinding.ActivityLoginBinding;

import com.example.aspbuild1.R;
import com.example.aspbuild1.Entities.User;
import com.example.aspbuild1.ViewModels.UserViewModel;

public class LoginActivity extends AppCompatActivity{

    EditText editTextUsername;
    EditText editTextPassword;
    Button loginButton;
    Button registerButton;
    public static final int REGISTER_REQUEST = 1;
    private UserViewModel userViewModel;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar();
        setTitle("Recipe Manager");

        editTextPassword = findViewById(R.id.login_ET_password);
        editTextUsername = findViewById(R.id.login_ET_username);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.login_register_button);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                validateUserLogin(username, password);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, REGISTER_REQUEST);
            }
        });

    }

    @Override   //on result of register page, catch result and register user into the db
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REGISTER_REQUEST && resultCode == RESULT_OK) {

            String username = data.getStringExtra(RegisterActivity.EXTRA_USER_NAME);
            String password = data.getStringExtra(RegisterActivity.EXTRA_PASSWORD);
            String firstName = data.getStringExtra(RegisterActivity.EXTRA_FIRST_NAME);
            String lastName = data.getStringExtra(RegisterActivity.EXTRA_LAST_NAME);

            User user = new User(username, password, firstName, lastName);
            // insert into db
            userViewModel.insert(user);
            Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "LoginActivity, OnActivityResult - get userId value: " + user.getId());

        } else {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    private void validateUserLogin(String username, String password){

            User userLoginInfo = userViewModel.getUserLoginInfo(username, password);

            if (userLoginInfo == null){
                Toast.makeText(this, "Account not found", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (userLoginInfo.getUsername().equals(username) && userLoginInfo.getPassword().equals(password)){

                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();

                Intent loginIntent = new Intent(this, MainActivity.class);
                loginIntent.putExtra(MainActivity.EXTRA_USER_USERNAME, username);
                loginIntent.putExtra(MainActivity.EXTRA_USER_PASSWORD, password);

                startActivity(loginIntent);
            }
    }
}
