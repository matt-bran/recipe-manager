package com.example.aspbuild1.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aspbuild1.Adapters.IngredientAdapter;
import com.example.aspbuild1.Adapters.InstructionAdapter;
import com.example.aspbuild1.Adapters.MyIngredientAdapter;
import com.example.aspbuild1.Entities.Ingredient;
import com.example.aspbuild1.Entities.Instruction;
import com.example.aspbuild1.R;
import com.example.aspbuild1.ViewModels.IngredientViewModel;
import com.example.aspbuild1.ViewModels.InstructionViewModel;
import com.example.aspbuild1.ViewModels.RecipeEventViewModel;
import com.example.aspbuild1.ViewModels.RecipeViewModel;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ViewEditRecipeActivity extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.example.aspbuild1.EXTRA_ID";
    public static final String EXTRA_USER_ID =
            "com.example.aspbuild1.EXTRA_USER_ID";
    public static final String EXTRA_TITLE =
            "com.example.aspbuild1.EXTRA_TITLE";
    public static final String EXTRA_DESC =
            "com.example.aspbuild1.EXTRA_DESC";
    public static final String EXTRA_IMAGE =
            "com.example.aspbuild1.EXTRA_IMAGE";
    public static final String EXTRA_USERNAME =
            "com.example.aspbuild1.EXTRA_USERNAME";
    public static final String EXTRA_PASSWORD =
            "com.example.aspbuild1.EXTRA_PASSWORD";
    public static final int PICK_IMAGE = 1;
    public static final int UPDATE_RECIPE_REQUEST = 3;

    private IngredientViewModel ingredientViewModel;
    private InstructionViewModel instructionViewModel;


    private ImageView imageView;
    private TextView recipeTitleTV;
    private TextView recipeDescTV;
    private RecyclerView ingredientRecyclerView;
    private RecyclerView instructionRecyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit_recipe);

        // create back button in the action bar to return to previous screen
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("View Recipe");

        imageView = findViewById(R.id.img_photo_id);
        recipeTitleTV = findViewById(R.id.tv_ve_title);
        recipeDescTV = findViewById(R.id.tv_ve_desc);
        ingredientRecyclerView = findViewById(R.id.recycler_ingredients);
        instructionRecyclerView = findViewById(R.id.recycler_instructions);

        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ingredientRecyclerView.setHasFixedSize(true);
        final IngredientAdapter ingredientAdapter = new IngredientAdapter();
        ingredientRecyclerView.setAdapter(ingredientAdapter);

        instructionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        instructionRecyclerView.setHasFixedSize(true);
        final InstructionAdapter instructionAdapter = new InstructionAdapter();
        instructionRecyclerView.setAdapter(instructionAdapter);

        Intent intent = getIntent();
        String recipeId = intent.getStringExtra(EXTRA_ID);

        ingredientViewModel = ViewModelProviders.of(this).get(IngredientViewModel.class);
        ingredientViewModel.getRecipeIngredients(recipeId).observe(this, new Observer<List<Ingredient>>() {
            @Override
            public void onChanged(List<Ingredient> list) {
                ingredientAdapter.setIngredients(list);
            }
        });

        instructionViewModel = ViewModelProviders.of(this).get(InstructionViewModel.class);
        instructionViewModel.getRecipeInsructions(recipeId).observe(this, new Observer<List<Instruction>>() {
            @Override
            public void onChanged(List<Instruction> list) {
                instructionAdapter.setInstructions(list);
            }
        });

        // set resource values
        recipeTitleTV.setText(intent.getStringExtra(EXTRA_TITLE));
        recipeDescTV.setText(intent.getStringExtra(EXTRA_DESC));
        try {
            final Uri imageUri = Uri.parse(intent.getStringExtra((EXTRA_IMAGE)));
            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            imageView.setImageBitmap(selectedImage);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void editRecipe(){

        Intent getIntent = getIntent();

        String title = getIntent.getStringExtra(EXTRA_TITLE);
        String description = getIntent.getStringExtra(EXTRA_DESC);
        String image = getIntent.getStringExtra(EXTRA_IMAGE);
        String recipe_id = getIntent.getStringExtra(EXTRA_ID);
        String user_id = getIntent.getStringExtra(EXTRA_USER_ID);
        String username = getIntent.getStringExtra(EXTRA_USERNAME);
        String password = getIntent.getStringExtra(EXTRA_PASSWORD);

        Intent intent = new Intent(ViewEditRecipeActivity.this, UpdateRecipeActivity.class);     //Create intent to return initial call for result in recipes fragment
        intent.putExtra("title", title);
        intent.putExtra("description", description); // put recipe info in intent
        intent.putExtra("image", image);
        intent.putExtra("recipeId", recipe_id);
        intent.putExtra("userId", user_id);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        startActivityForResult(intent, UPDATE_RECIPE_REQUEST);
    }
    private void donteditRecipe(){
        Intent getIntent = getIntent();
        String user_id = getIntent.getStringExtra(EXTRA_USER_ID);
        String username = getIntent.getStringExtra(EXTRA_USERNAME);
        String password = getIntent.getStringExtra(EXTRA_PASSWORD);

        Intent data = new Intent();
        data.putExtra("userId", user_id);
        data.putExtra("username", username);
        data.putExtra("password", password);
        setResult(RESULT_CANCELED, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.view_recipe_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit_recipe:
                editRecipe();
                return true;
            default:
                donteditRecipe();
                return true;
        }
        //return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == UPDATE_RECIPE_REQUEST && resultCode == RESULT_OK) {

            //changeFlag = "changed";
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("description");  // retrieve data from recipe
            String image = data.getStringExtra("image");
            String recipeId = data.getStringExtra("recipeId");
            String userId = data.getStringExtra("userId");
            String username = data.getStringExtra("username");
            String password = data.getStringExtra("password");

            RecipeViewModel recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
            recipeViewModel.updateUserRecipe(recipeId, userId, title, description, image);
            RecipeEventViewModel recipeEventViewModel = ViewModelProviders.of(this).get(RecipeEventViewModel.class);
            recipeEventViewModel.update(recipeId, title, image);
            Toast.makeText(this, "Recipe updated", Toast.LENGTH_SHORT).show();

            /*Intent getIntent = getIntent();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(MainActivity.EXTRA_USER_USERNAME, getIntent.getStringExtra(EXTRA_USERNAME));
            intent.putExtra(MainActivity.EXTRA_USER_PASSWORD, getIntent.getStringExtra(EXTRA_PASSWORD));
            startActivity(intent);*/
            donteditRecipe();
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}

