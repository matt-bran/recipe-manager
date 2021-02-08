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

public class UpdateIngredientActivity extends AppCompatActivity {

    public static final String EXTRA_USERNAME =
            "com.example.aspbuild1.EXTRA_USERNAME";
    public static final String EXTRA_PASSWORD =
            "com.example.aspbuild1.EXTRA_PASSWORD";

    private EditText editTextIngredient;
    private String title;
    private String id;
    private int RESULT_DELETE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_ingredient);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        id = intent.getStringExtra("id");

        editTextIngredient = findViewById(R.id.edit_text_update_ingredient);
        editTextIngredient.setText(title);

        // create back button in the action bar to return to previous screen
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Update Ingredient");
    }

    public void updateIngredient() {

        title = editTextIngredient.getText().toString();

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

    public void deleteIngredient() {
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
        menuInflater.inflate(R.menu.update_ingredient_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.update_ingredient:
                updateIngredient();
                return true;
            case R.id.delete_ingredient :
                deleteIngredient();
            default:
                dontUpdate();
                return true;
        }
    }
}
