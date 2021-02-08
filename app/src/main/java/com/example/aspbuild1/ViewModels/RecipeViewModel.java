package com.example.aspbuild1.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.aspbuild1.Entities.Recipe;
import com.example.aspbuild1.Repositories.RecipeRepository;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

    private RecipeRepository repository;
    private LiveData<List<Recipe>> allRecipes;
    private LiveData<List<Recipe>> userRecipes;
    private static final String TAG = "RecipeViewModel";

    public LiveData<List<Recipe>> getAllRecipes() {
        return allRecipes;
    }
    public LiveData<List<Recipe>> findRecipesForUser() {
        return userRecipes;
    }

    public RecipeViewModel(@NonNull Application application, String userId) {
        super(application);
        repository = new RecipeRepository(application, userId);
        Log.i(TAG, "RecipeViewModel Constructor - get userId value: " + userId);
        //allRecipes = repository.getAllRecipes(userId);
        userRecipes = repository.findRecipesForUser();
    }
    public RecipeViewModel(@NonNull Application application) {
        super(application);
        repository = new RecipeRepository(application);
    }

    public void insert(Recipe recipe){
        repository.insert(recipe);
    }

    /*public void update(Recipe recipe){
        repository.update(recipe);
    }*/

    public void updateUserRecipe(String recipeId, String userId, String name, String description, String image){
        repository.updateUserRecipe(recipeId, userId, name, description, image);
    }

    public void delete(Recipe recipe){
        repository.delete(recipe);
    }
}
