package com.example.aspbuild1.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aspbuild1.Adapters.RecipeAdapter;
import com.example.aspbuild1.Entities.Recipe;
import com.example.aspbuild1.R;
import com.example.aspbuild1.ViewModels.RecipeViewModel;
import com.example.aspbuild1.ViewModels.RecipeViewModelFactory;

import java.util.List;

public class AddRecipeEventActivity extends AppCompatActivity {

    private RecyclerView recyclerview;
    private RecipeViewModel recipeViewModel;
    private TextView editTextDate;
    private EditText editTextNote;

    private String user_id;
    private String recipe_id;
    private String date;
    private String otherDate;
    private String recipe_name;
    private String recipe_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe_event);

        // create back button in the action bar to return to previous screen
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add Recipe Event");

        editTextNote = findViewById(R.id.edit_text_add_event_note);
        editTextDate = findViewById(R.id.edit_text_add_event_date);
        recyclerview = findViewById(R.id.recycler_view_add_recipe_event);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setHasFixedSize(true);
        final RecipeAdapter adapter = new RecipeAdapter();
        recyclerview.setAdapter(adapter);     //bind recyclerview to adapter

        Intent getIntent = getIntent();
        user_id = getIntent.getStringExtra("user_id");
        date = getIntent.getStringExtra("selectedStringDate");
        otherDate = getIntent.getStringExtra("selectedDate");
        editTextDate.setText(otherDate);


        recipeViewModel = ViewModelProviders.of(this, new RecipeViewModelFactory(this.getApplication(), user_id)).get(RecipeViewModel.class);
        recipeViewModel.findRecipesForUser().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) { // when change is made to data in the db refresh the list
                adapter.setRecipes(recipes); //send new list of recipes to adapter
            }
        });

        adapter.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Recipe recipe) {
                Intent data = new Intent();
                recipe_id = recipe.getId();
                recipe_name = recipe.getName();
                recipe_image = recipe.getPhoto();
                String note = editTextNote.getText().toString();
                if (note.isEmpty()){
                    Toast.makeText(AddRecipeEventActivity.this, "Please add a note", Toast.LENGTH_SHORT).show();
                    return;
                }
                data.putExtra("recipe_id", recipe_id);
                data.putExtra("user_id", user_id);
                data.putExtra("date", date);
                data.putExtra("recipe_name", recipe_name);
                data.putExtra("recipe_image", recipe_image);
                data.putExtra("event_note", note);
                setResult(RESULT_OK, data);
                finish();
            }
        });

    }

    public void dontSave() {
        Intent data = new Intent();
        setResult(RESULT_CANCELED, data);
        finish();
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_recipe_event_menu, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            default:
                dontSave();
        }
        return super.onOptionsItemSelected(item);
    }
}
