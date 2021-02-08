package com.example.aspbuild1.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aspbuild1.Activities.AddRecipeActivity;
import com.example.aspbuild1.Entities.Ingredient;
import com.example.aspbuild1.Entities.Instruction;
import com.example.aspbuild1.Entities.Recipe;
import com.example.aspbuild1.Entities.RecipeEvent;
import com.example.aspbuild1.R;
import com.example.aspbuild1.Adapters.RecipeAdapter;
import com.example.aspbuild1.ViewModels.IngredientViewModel;
import com.example.aspbuild1.ViewModels.InstructionViewModel;
import com.example.aspbuild1.ViewModels.RecipeEventViewModel;
import com.example.aspbuild1.ViewModels.RecipeViewModel;
import com.example.aspbuild1.ViewModels.RecipeViewModelFactory;
import com.example.aspbuild1.Activities.ViewEditRecipeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class RecipesFragment extends Fragment /* implements RecipeAdapter.OnItemClickListener */ {


    private RecyclerView myrecyclerview;
    private RecipeViewModel recipeViewModel;
    private IngredientViewModel ingredientViewModel;
    private InstructionViewModel instructionViewModel;
    private RecipeEventViewModel recipeEventViewModel;
    public static final int ADD_RECIPE_REQUEST = 1;
    public static final int VIEW_RECIPE_REQUEST = 15;
    private String user_id;
    private String username;
    private String password;
    private ArrayList<String> stringIngredientArrayList;
    private ArrayList<String> stringInstructionArrayList;
    private ArrayList<Ingredient> ingredientArrayList;
    private ArrayList<Instruction> instructionArrayList;
    private String TAG = "Recipes Fragment";

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_recipes, container, false); //inflate view to the fragment

        myrecyclerview = v.findViewById(R.id.recipes_recycler_view);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setHasFixedSize(true);
        final RecipeAdapter adapter = new RecipeAdapter();
        myrecyclerview.setAdapter(adapter);     //bind recyclerview to adapter

        Bundle bundle = this.getArguments();
        if (bundle != null){
            user_id = bundle.getString("user_id");
            username = bundle.getString("username");
            password = bundle.getString("password");
            //Toast.makeText(getActivity(), "bundle accessed" + user_id, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "bundle not accessed" + user_id, Toast.LENGTH_SHORT).show();
        }

        FloatingActionButton buttonAddRecipe = v.findViewById(R.id.button_add_recipe); // brings to add recipe activity
        buttonAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddRecipeActivity.class); // intent assigned to button which brings user to add recipe activity
                intent.putExtra("int_value", user_id);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                startActivityForResult(intent, ADD_RECIPE_REQUEST); // request result from  add recipe activity
            }
        });

        // Handles live data
        recipeEventViewModel = ViewModelProviders.of(this).get(RecipeEventViewModel.class);
        ingredientViewModel = ViewModelProviders.of(this).get(IngredientViewModel.class);
        instructionViewModel = ViewModelProviders.of(this).get(InstructionViewModel.class);
        recipeViewModel = ViewModelProviders.of(this, new RecipeViewModelFactory(getActivity().getApplication(), user_id)).get(RecipeViewModel.class);
        recipeViewModel.findRecipesForUser().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) { // when change is made to data in the db refresh the list
                adapter.setRecipes(recipes); //send new list of recipes to adapter
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                Recipe recipe = adapter.getRecipeAt(viewHolder.getAdapterPosition());
                String recipeId = recipe.getId();

                recipeEventViewModel.delete(recipeId);
                recipeViewModel.delete(adapter.getRecipeAt(viewHolder.getAdapterPosition()));

                Toast.makeText(getActivity(), "Recipe deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(myrecyclerview);

        adapter.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Recipe recipe) {
                Intent intent = new Intent(getActivity(), ViewEditRecipeActivity.class);
                intent.putExtra(ViewEditRecipeActivity.EXTRA_TITLE, recipe.getName());
                intent.putExtra(ViewEditRecipeActivity.EXTRA_DESC, recipe.getDescription());
                intent.putExtra(ViewEditRecipeActivity.EXTRA_IMAGE, recipe.getPhoto());
                intent.putExtra(ViewEditRecipeActivity.EXTRA_ID, recipe.getId());
                intent.putExtra(ViewEditRecipeActivity.EXTRA_USER_ID, user_id);
                intent.putExtra(ViewEditRecipeActivity.EXTRA_USERNAME, username);
                intent.putExtra(ViewEditRecipeActivity.EXTRA_PASSWORD, password);
                startActivityForResult(intent, VIEW_RECIPE_REQUEST);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {    // Handles result of the Add Recipe Activity

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_RECIPE_REQUEST && resultCode == RESULT_OK) {  // Check request code and make sure result is successfull
            String title = data.getStringExtra(AddRecipeActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddRecipeActivity.EXTRA_DESC);  // retrieve data from recipe
            String image = data.getStringExtra(AddRecipeActivity.EXTRA_IMAGE);
            stringIngredientArrayList = data.getStringArrayListExtra("ingredientArrayList");
            stringInstructionArrayList = data.getStringArrayListExtra("instructionArrayList");
            user_id = data.getStringExtra("user_id");
            username = data.getStringExtra("username");
            password = data.getStringExtra("password");

            Recipe recipe = new Recipe(title, description, image, user_id);  //create new recipe object
            Log.d(TAG, "Recipe Id(should be foreign key): " + recipe.getId());
            String recipeId = recipe.getId();
            ingredientArrayList = new ArrayList<>();
            instructionArrayList = new ArrayList<>();

            for (int i = 0; i<stringIngredientArrayList.size(); i++){
                String ingredientText = stringIngredientArrayList.get(i);
                Ingredient ingredientItem = new Ingredient(ingredientText, recipeId);
                ingredientArrayList.add(ingredientItem);
            }

            for (int i = 0; i < stringInstructionArrayList.size(); i++){
                String instructionText = stringInstructionArrayList.get(i);
                Instruction instructionItem = new Instruction(instructionText, recipeId);
                instructionArrayList.add(instructionItem);
            }
            recipeViewModel.insert(recipe); // insert recipe into the db
            ingredientViewModel.insert(ingredientArrayList);
            instructionViewModel.insert(instructionArrayList);
            Toast.makeText(getActivity(), "Recipe saved", Toast.LENGTH_SHORT).show();

        }
        else if (requestCode == VIEW_RECIPE_REQUEST && resultCode == RESULT_CANCELED){
            user_id = data.getStringExtra("user_id");
            username = data.getStringExtra("username");
            password = data.getStringExtra("password");
        }

        else {
            user_id = data.getStringExtra("user_id");
            username = data.getStringExtra("username");
            password = data.getStringExtra("password");
        }
    }
}
