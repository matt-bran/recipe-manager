package com.example.aspbuild1.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aspbuild1.R;

public class RegisterActivity extends AppCompatActivity {

    public static final String EXTRA_USER_NAME =
            "com.example.aspbuild1.EXTRA_USER_NAME";
    public static final String EXTRA_PASSWORD =
            "com.example.aspbuild1.EXTRA_PASSWORD";
    public static final String EXTRA_FIRST_NAME =
            "com.example.aspbuild1.EXTRA_FIRST_NAME";
    public static final String EXTRA_LAST_NAME =
            "com.example.aspbuild1.EXTRA_LAST_NAME";

    EditText editTextUsername;
    EditText editTextPassword;
    EditText editTextCnfPassword;
    EditText editTextFirstName;
    EditText editTextLastName;
    Button createAccountButton;

    private String username;
    private String password;
    private String passwordCnf;
    private String firstName;
    private String lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar();
        setTitle("Create Account");

        editTextUsername = findViewById(R.id.register_username);
        editTextPassword = findViewById(R.id.register_pw);
        editTextCnfPassword = findViewById(R.id.register_cnf_pw);
        editTextFirstName = findViewById(R.id.editText_firstName);
        editTextLastName = findViewById(R.id.editText_lastName);
        createAccountButton = findViewById(R.id.register_account_button);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser() {
        username = editTextUsername.getText().toString();
        password = editTextPassword.getText().toString();
        passwordCnf = editTextCnfPassword.getText().toString();
        firstName = editTextFirstName.getText().toString();
        lastName = editTextLastName.getText().toString();

        if (username.isEmpty() || password.isEmpty() || passwordCnf.isEmpty() ||
            firstName.isEmpty() || lastName.isEmpty()){
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.equals(passwordCnf)){
            Intent data = new Intent();
            data.putExtra(EXTRA_USER_NAME, username);
            data.putExtra(EXTRA_PASSWORD, password);
            data.putExtra(EXTRA_FIRST_NAME, firstName);
            data.putExtra(EXTRA_LAST_NAME, lastName);
            setResult(RESULT_OK, data);     // pass data and finish
            finish();
        } else {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
