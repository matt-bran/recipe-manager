package com.example.aspbuild1.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aspbuild1.Adapters.IngredientAdapter;
import com.example.aspbuild1.Adapters.InstructionAdapter;
import com.example.aspbuild1.Entities.Ingredient;
import com.example.aspbuild1.Entities.Instruction;
import com.example.aspbuild1.R;
import com.example.aspbuild1.ViewModels.IngredientViewModel;
import com.example.aspbuild1.ViewModels.InstructionViewModel;

import java.util.ArrayList;
import java.util.List;

public class UpdateRecipeActivity extends AppCompatActivity {

    /*public static final String EXTRA_ID =
            "com.example.aspbuild1.EXTRA_ID";
    public static final String EXTRA_USER_ID =
            "com.example.aspbuild1.EXTRA_USER_ID";
    public static final String EXTRA_TITLE =
            "com.example.aspbuild1.EXTRA_TITLE";
    public static final String EXTRA_DESC =
            "com.example.aspbuild1.EXTRA_DESC";
    public static final String EXTRA_IMAGE =
            "com.example.aspbuild1.EXTRA_IMAGE";*/
    public static final String EXTRA_USERNAME =
            "com.example.aspbuild1.EXTRA_USERNAME";
    public static final String EXTRA_PASSWORD =
            "com.example.aspbuild1.EXTRA_PASSWORD";
    public static final int PICK_IMAGE = 1;

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextIngredient;
    private Button buttonIngredient;
    private EditText editTextInstruction;
    private Button buttonInstruction;
    private Button buttonImageSelect;
    private ImageView recipeImage;
    private String imageUri;
    private RecyclerView ingredientRecyclerView;
    private RecyclerView instructionRecyclerView;
    private IngredientViewModel ingredientViewModel;
    private IngredientAdapter ingredientAdapter;
    private InstructionViewModel instructionViewModel;
    private InstructionAdapter instructionAdapter;
    private ArrayList<Ingredient> arrayIngredientList;
    public static final int UPDATE_INGREDIENT_REQUEST = 4;
    private ArrayList<Instruction> arrayInstructionList;
    public static final int UPDATE_INSTRUCTION_REQUEST = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_recipe);

        Intent intent = getIntent();
        String EXTRA_TITLE = intent.getStringExtra("title");
        String EXTRA_DESC = intent.getStringExtra("description");
        String EXTRA_IMAGE = intent.getStringExtra("image");
        final String recipeId = intent.getStringExtra("recipeId");

        editTextTitle = findViewById(R.id.edit_text_update_title);
        editTextTitle.setText(EXTRA_TITLE);
        editTextDescription = findViewById(R.id.edit_text_update_description);
        editTextDescription.setText(EXTRA_DESC);
        editTextIngredient = findViewById(R.id.edit_text_update_add_ingredient);
        buttonIngredient = findViewById(R.id.button_update_add_ingredient);
        editTextInstruction = findViewById(R.id.edit_text_update_add_instruction);
        buttonInstruction = findViewById(R.id.button_update_add_instruction);
        buttonImageSelect = findViewById(R.id.button_update_image);
        recipeImage = findViewById(R.id.img_update_recipe);
        recipeImage.setImageURI(Uri.parse(EXTRA_IMAGE));
        ingredientRecyclerView = findViewById(R.id.recycler_update_ingredients);
        instructionRecyclerView = findViewById(R.id.recycler_update_instructions);
        imageUri = intent.getStringExtra("image");

        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ingredientRecyclerView.setHasFixedSize(true);
        ingredientAdapter = new IngredientAdapter();
        ingredientRecyclerView.setAdapter(ingredientAdapter);

        instructionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        instructionRecyclerView.setHasFixedSize(true);
        instructionAdapter = new InstructionAdapter();
        instructionRecyclerView.setAdapter(instructionAdapter);

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

        // create back button in the action bar to return to previous screen
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Update Recipe");

        //Handle select image from gallery
        buttonImageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // create an intent for the gallery
                Intent gallery = new Intent();

                gallery.setType("image/*");

                gallery.setAction(Intent.ACTION_GET_CONTENT);

                //request result from gallery activity
                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);

            }
        });

        arrayIngredientList = new ArrayList<>();
        buttonIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editTextIngredient.getText().toString();
                if (text.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Invalid entry", Toast.LENGTH_SHORT).show();
                    return;
                }
                Ingredient ingredient = new Ingredient(text, recipeId);
                arrayIngredientList.add(ingredient);
                ingredientViewModel.insert(arrayIngredientList);
                editTextIngredient.setText("");
                arrayIngredientList.remove(0);
                recreate();
            }
        });

        arrayInstructionList = new ArrayList<>();
        buttonInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editTextInstruction.getText().toString();
                if (text.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Invalid entry", Toast.LENGTH_SHORT).show();
                    return;
                }
                Instruction instruction = new Instruction(text, recipeId);
                arrayInstructionList.add(instruction);
                instructionViewModel.insert(arrayInstructionList);
                editTextInstruction.setText("");
                arrayInstructionList.remove(0);
                recreate();
            }
        });

        ingredientAdapter.setOnItemClickListener(new IngredientAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Ingredient ingredient) {
                Intent intent = new Intent(UpdateRecipeActivity.this, UpdateIngredientActivity.class);
                intent.putExtra("title", ingredient.getText());
                intent.putExtra("id", ingredient.getId());
                startActivityForResult(intent, UPDATE_INGREDIENT_REQUEST);
            }
        });

        instructionAdapter.setOnItemClickListener(new InstructionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Instruction instruction) {
                Intent intent = new Intent(UpdateRecipeActivity.this, UpdateInstructionActivity.class);
                intent.putExtra("title", instruction.getText());
                intent.putExtra("id", instruction.getId());
                startActivityForResult(intent, UPDATE_INSTRUCTION_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Check the request we are responding to
        if (requestCode == PICK_IMAGE) {

            //Make sure request was successfull
            if (resultCode== RESULT_OK){

                //get the uri that points to the selected image
                Uri acquiredUri = data.getData();

                //Display selected image
                recipeImage.setImageURI(acquiredUri);

                //transfer URI to class level variable
                //show file name
                imageUri = acquiredUri.toString();
            }
        }
        else if (requestCode == UPDATE_INGREDIENT_REQUEST && resultCode == RESULT_OK){
            String title = data.getStringExtra("title");
            String id = data.getStringExtra("id");
            IngredientViewModel ingredientViewModel = ViewModelProviders.of(this).get(IngredientViewModel.class);
            ingredientViewModel.update(title, id);
            recreate();
        }
        else if (requestCode == UPDATE_INSTRUCTION_REQUEST && resultCode == RESULT_OK){
            String title = data.getStringExtra("title");
            String id = data.getStringExtra("id");
            InstructionViewModel instructionViewModel = ViewModelProviders.of(this).get(InstructionViewModel.class);
            instructionViewModel.update(title, id);
            recreate();
        }
        else if (requestCode == UPDATE_INGREDIENT_REQUEST && resultCode == 0) {
            String id = data.getStringExtra("id");
            IngredientViewModel ingredientViewModel = ViewModelProviders.of(this).get(IngredientViewModel.class);
            ingredientViewModel.delete(id);
            recreate();
        }
        else if (requestCode == UPDATE_INSTRUCTION_REQUEST && resultCode == 50) {
            String id = data.getStringExtra("id");
            InstructionViewModel instructionViewModel = ViewModelProviders.of(this).get(InstructionViewModel.class);
            instructionViewModel.delete(id);
            recreate();
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private void updateRecipe(){

        Intent intent = getIntent();

        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String image = imageUri;
        String recipeId = intent.getStringExtra("recipeId");
        String userId = intent.getStringExtra("userId");
        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");

        //Check if any fields are left empty before saving
        if (title.trim().isEmpty() || description.trim().isEmpty() || image.isEmpty() ) {
            Toast.makeText(this, "Please insert a title and description and photo", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();     //Create intent to return initial call for result in recipes fragment
        data.putExtra("title", title);
        data.putExtra("description", description); // put recipe info in intent
        data.putExtra("image", image);
        data.putExtra("recipeId", recipeId);
        data.putExtra("userId", userId);
        data.putExtra("username", username);
        data.putExtra("password", password);
        setResult(RESULT_OK, data);     // pass data and finish
        finish();
    }

    private void dontUpdateRecipe() {
        Intent cancel = new Intent();
        setResult(RESULT_CANCELED, cancel);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.update_recipe_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.update_recipe:
                updateRecipe();
                return true;
            default:
                //return super.onOptionsItemSelected(item);
                dontUpdateRecipe();
                return true;
        }

    }


}

