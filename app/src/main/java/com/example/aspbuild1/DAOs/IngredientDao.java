package com.example.aspbuild1.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.aspbuild1.Entities.Ingredient;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface IngredientDao {

    @Insert
    void insert(Ingredient ingredient);

    @Update
    void update(Ingredient ingredient);

    @Delete
    void delete(Ingredient ingredient);

    @Query("SELECT * FROM ingredients WHERE recipe_id = :id")
    LiveData<List<Ingredient>> getRecipeIngredients(final String id);

    @Query("UPDATE ingredients SET ingredient_text = :ingredient_text WHERE ingredient_id = :ingredient_id")
    int updateRecipeIngredient(final String ingredient_text, final String ingredient_id);

    @Query("DELETE FROM ingredients WHERE ingredient_id = :ingredient_id ")
    int deleteIngredient(final String ingredient_id);
}
