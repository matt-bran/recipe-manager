package com.example.aspbuild1.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.aspbuild1.Entities.Ingredient;
import com.example.aspbuild1.Repositories.IngredientRepository;

import java.util.ArrayList;
import java.util.List;

public class IngredientViewModel extends AndroidViewModel {

    private IngredientRepository repository;

    public IngredientViewModel(@NonNull Application application) {
        super(application);
        repository = new IngredientRepository(application);
    }
    public void insert(ArrayList<Ingredient> ingredients){
        repository.insert(ingredients);
    }
    public void delete(String id){
        repository.delete(id);
    }
    public void update(String ingredientText , String ingredient_id){
        repository.update(ingredientText, ingredient_id);
    }
    public LiveData<List<Ingredient>> getRecipeIngredients(String recipeId){
        return repository.getRecipeIngredients(recipeId);
    }
}
