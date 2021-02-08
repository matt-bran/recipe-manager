package com.example.aspbuild1.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.aspbuild1.Entities.Ingredient;
import com.example.aspbuild1.Entities.RecipeEvent;
import com.example.aspbuild1.Repositories.RecipeEventRepository;

import java.util.List;

public class RecipeEventViewModel extends AndroidViewModel {

    private RecipeEventRepository repository;

    public RecipeEventViewModel(Application application){
        super(application);
        repository = new RecipeEventRepository(application);
    }

    public void insert(RecipeEvent recipeEvent){
        repository.insert(recipeEvent);
    }

    public void update(String id, String name, String image){
        repository.update(id, name, image);
    }

    public void delete(String id){
        repository.deleteFromRecipe(id);
    }
    public void deleteFromEvent(String id){
        repository.deleteFromEvent(id);
    }

    public LiveData<List<RecipeEvent>> getRecipeEvents(String user_id){
        return repository.getRecipeEvents(user_id);
    }

}
