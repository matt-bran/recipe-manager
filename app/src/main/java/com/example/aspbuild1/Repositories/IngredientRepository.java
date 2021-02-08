package com.example.aspbuild1.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.aspbuild1.DAOs.IngredientDao;
import com.example.aspbuild1.Entities.Ingredient;
import com.example.aspbuild1.MyDatabase;

import java.util.ArrayList;
import java.util.List;

public class IngredientRepository {

    private IngredientDao ingredientDao;
    private static String TAG = "Ingredient Repository";

    public IngredientRepository(Application application){
        MyDatabase database = MyDatabase.getInstance(application);
        ingredientDao = database.ingredientDao();
    }

    public void insert(ArrayList<Ingredient> ingredients){
        new InsertIngredientAsyncTask(ingredientDao).execute(ingredients);
    }
    public void delete(String id){
        new DeleteIngredientAsyncTask(ingredientDao).execute(id);
    }

    public void update(String ingredientText, String ingredient_id){
        new UpdateIngredientAsyncTask(ingredientDao).execute(ingredientText, ingredient_id);

    }
    public LiveData<List<Ingredient>> getRecipeIngredients(String recipeId){
        return ingredientDao.getRecipeIngredients(recipeId);
    }

    private static class InsertIngredientAsyncTask extends AsyncTask<ArrayList<Ingredient>, Void, Void>{

        private IngredientDao ingredientDao;
        private ArrayList<Ingredient> ingredientList;
        private Ingredient ingredientItem;

        private InsertIngredientAsyncTask(IngredientDao ingredientDao){
            this.ingredientDao = ingredientDao;

        }
        @Override
        protected Void doInBackground(ArrayList<Ingredient>... ingredients) {

            ingredientList = ingredients[0];

            for (int i = 0; i < ingredientList.size(); i++){
                ingredientItem = ingredientList.get(i);
                Log.d(TAG, "Inserted Ingredient: string text = " + ingredientItem.getText()+ " foreign key = "  + ingredientItem.getRecipe_id() + " primary key = " + ingredientItem.getId());
                ingredientDao.insert(ingredientItem);
            }
            return null;
        }
    }

    private static class UpdateIngredientAsyncTask extends AsyncTask<String, Void, Void>{
        private IngredientDao ingredientDao;
        private String text;
        private String id;

        private UpdateIngredientAsyncTask(IngredientDao ingredientDao){
            this.ingredientDao = ingredientDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            text = strings[0];
            id = strings[1];
            ingredientDao.updateRecipeIngredient(text, id);
            return null;
        }
    }

    private static class DeleteIngredientAsyncTask extends AsyncTask<String, Void, Void>{
        private IngredientDao ingredientDao;

        private DeleteIngredientAsyncTask(IngredientDao ingredientDao){
            this.ingredientDao = ingredientDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
             ingredientDao.deleteIngredient(strings[0]);
             return null;
        }
    }
}
