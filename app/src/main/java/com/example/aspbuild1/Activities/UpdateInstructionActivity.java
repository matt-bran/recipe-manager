package com.example.aspbuild1.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aspbuild1.R;

public class UpdateInstructionActivity extends AppCompatActivity {

    public static final String EXTRA_USERNAME =
            "com.example.aspbuild1.EXTRA_USERNAME";
    public static final String EXTRA_PASSWORD =
            "com.example.aspbuild1.EXTRA_PASSWORD";

    private EditText editTextInstruction;
    private String title;
    private String id;
    private int RESULT_DELETE = 50;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_instruction);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        id = intent.getStringExtra("id");

        editTextInstruction = findViewById(R.id.edit_text_update_instruction);
        editTextInstruction.setText(title);

        // create back button in the action bar to return to previous screen
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Update Instruction");
    }

    public void updateInstruction() {

        title = editTextInstruction.getText().toString();

        if (title.trim().isEmpty()){
            Toast.makeText(this, "Please insert a title", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra("title", title);
        data.putExtra("id", id);
        setResult(RESULT_OK, data);
        finish();
    }

    public void deleteInstruction() {
        Intent data = new Intent();
        data.putExtra("id", id);
        setResult(RESULT_DELETE, data);
        finish();
    }

    private void dontUpdate(){
        Intent cancel = new Intent();
        setResult(RESULT_CANCELED, cancel);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.update_instruction_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.update_instruction:
                updateInstruction();
                return true;
            case R.id.delete_instruction :
                deleteInstruction();
            default:
                dontUpdate();
                return true;
        }
    }
}

