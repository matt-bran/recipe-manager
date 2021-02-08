package com.example.aspbuild1.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.aspbuild1.Entities.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {

    @Insert
    void insert(Recipe recipe);

    @Update
    void update(Recipe recipe);

    @Delete
    void delete(Recipe recipe);

    @Query("SELECT * FROM recipe_table WHERE user_Id=:userId ORDER BY name ASC")
    LiveData<List<Recipe>> getAllRecipes(final String userId);

    @Query("SELECT * FROM recipe_table WHERE user_Id=:userId ORDER BY name ASC")
    LiveData<List<Recipe>> findRecipesForUser(final String userId);

    @Query("UPDATE recipe_table SET name = :recipeName, description = :recipeDesc, image = :recipeImage WHERE recipe_Id = :recipe_id AND user_id = :user_Id")
    int updateUserRecipe(final String recipe_id, final String user_Id, final String recipeName, final String recipeDesc, final String recipeImage);

}
