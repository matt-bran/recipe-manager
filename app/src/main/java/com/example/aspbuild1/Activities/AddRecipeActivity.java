package com.example.aspbuild1.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aspbuild1.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AddRecipeActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE =
            "com.example.aspbuild1.EXTRA_TITLE";
    public static final String EXTRA_DESC =
            "com.example.aspbuild1.EXTRA_DESC";
    public static final String EXTRA_IMAGE =
            "com.example.aspbuild1.EXTRA_IMAGE";
    public static final int PICK_IMAGE = 1;

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextIngredient;
    private EditText editTextInstruction;
    private Button buttonImageSelect;
    private Button buttonAddIngredient;
    private Button buttonAddInstruction;
    private ListView listViewIngredients;
    private ListView listViewInstructions;
    private TextView tv_image_name;
    private ImageView recipeImage;
    private String imageString = "";
    private ArrayList<String> ingredientArrayList;
    private ArrayList<String> instructionArrayList;
    private ArrayAdapter<String> ingredientAdapter;
    private ArrayAdapter<String> instructionAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_recipe);

        //assign variable to layout files
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        editTextIngredient = findViewById(R.id.edit_text_add_ingredient);
        editTextInstruction = findViewById(R.id.edit_text_add_instruction);
        buttonImageSelect = findViewById(R.id.button_add_image);
        buttonAddIngredient = findViewById(R.id.button_add_ingredient);
        buttonAddInstruction = findViewById(R.id.button_add_instruction);
        //recipeImage = findViewById(R.id.img_add_recipe);
        listViewIngredients = findViewById(R.id.list_view_add_ingredients);
        listViewInstructions = findViewById(R.id.list_view_add_instructions);

        // create back button in the action bar to return to previous screen
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Recipe");

        ingredientArrayList = new ArrayList<>();
        instructionArrayList = new ArrayList<>();
        ingredientAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, ingredientArrayList);
        instructionAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, instructionArrayList);
        listViewIngredients.setAdapter(ingredientAdapter);
        listViewInstructions.setAdapter(instructionAdapter);

        //Handle select image from gallery
        buttonImageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // create an intent for the gallery
                Intent gallery = new Intent();

                gallery.setType("image/*");

                gallery.setAction(Intent.ACTION_OPEN_DOCUMENT); //intent recieves item selected by user

                //request result from gallery activity
                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);

            }
        });

        buttonAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editTextIngredient.getText().toString();
                if (text.isEmpty()){
                    Toast.makeText(AddRecipeActivity.this, "Invalid entry", Toast.LENGTH_SHORT).show();
                    return;
                }
                ingredientArrayList.add(text);
                ingredientAdapter.notifyDataSetChanged();
                editTextIngredient.setText("");
            }
        });
        buttonAddInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editTextInstruction.getText().toString();
                if (text.isEmpty()){
                    Toast.makeText(AddRecipeActivity.this, "Invalid entry", Toast.LENGTH_SHORT).show();
                    return;
                }
                instructionArrayList.add(text);
                instructionAdapter.notifyDataSetChanged();
                editTextInstruction.setText("");
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

                    try {
                        final Uri imageUri = data.getData();
                        imageString = imageUri.toString();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        Toast.makeText(this, "Image saved", Toast.LENGTH_SHORT).show();
                        //recipeImage.setImageBitmap(selectedImage);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                    //ContentResolver resolver = resolver.openAssetFileDescriptor()
                }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private String getPath(Uri acquiredUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(acquiredUri, proj, null, null, null);
        if(cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    private void saveRecipe(){

        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String image = imageString;

        //Check if any fields are left empty before saving
        if (title.trim().isEmpty() || description.trim().isEmpty() || image.isEmpty() ) {
            Toast.makeText(this, "Please insert a title and description and photo", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent getIntent = getIntent();
        String user_id = getIntent.getStringExtra("int_value");
        String username = getIntent.getStringExtra("username");
        String password = getIntent.getStringExtra("password");

        Intent data = new Intent();     //Create intent to return initial call for result in recipes fragment
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESC, description); // put recipe info in intent
        data.putExtra(EXTRA_IMAGE, image);
        data.putExtra("ingredientArrayList", ingredientArrayList);
        data.putExtra("instructionArrayList", instructionArrayList);
        data.putExtra("user_id", user_id);
        data.putExtra("username", username);
        data.putExtra("password", password);
        setResult(RESULT_OK, data);     // pass data and finish
        finish();
    }
    private void dontSaveRecipe(){

        Intent getIntent = getIntent();
        String user_id = getIntent.getStringExtra("int_value");
        String username = getIntent.getStringExtra("username");
        String password = getIntent.getStringExtra("password");

        Intent data = new Intent();
        data.putExtra("user_id", user_id);
        data.putExtra("username", username);
        data.putExtra("password", password);
        setResult(RESULT_CANCELED, data);     // pass data and finish
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_recipe_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_recipe:
                saveRecipe();
                return true;
            default:
                //return super.onOptionsItemSelected(item);
                dontSaveRecipe();
                return true;
        }

    }

}
