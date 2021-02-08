package com.example.aspbuild1.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.aspbuild1.Entities.Recipe;
import com.example.aspbuild1.Entities.RecipeEvent;

import java.util.List;

@Dao
public interface RecipeEventDao {

    @Insert
    void insert(RecipeEvent recipeEvent);

    @Update
    void update(RecipeEvent recipeEvent);

    @Delete
    void delete(RecipeEvent recipeEvent);

    @Query("SELECT * FROM recipe_events WHERE user_id = :user_id ORDER BY date ASC")
    LiveData<List<RecipeEvent>> getRecipeEvents(final String user_id);

    @Query("DELETE FROM recipe_events WHERE recipe_id = :recipe_id")
    int deleteRecipeEventFromRecipe(final String recipe_id);

    @Query("DELETE FROM recipe_events WHERE id = :event_id")
    int deleteRecipeEventFromEvent(final String event_id);

    @Query("UPDATE recipe_events SET event_image = :image, event_name = :name WHERE recipe_id = :recipe_id")
    int updateRecipeEvent(final String image, final String name, final String recipe_id);
}
