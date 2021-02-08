package com.example.aspbuild1.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.aspbuild1.DAOs.RecipeDao;
import com.example.aspbuild1.Entities.Recipe;
import com.example.aspbuild1.MyDatabase;

import java.util.List;

public class RecipeRepository {

    private RecipeDao recipeDao;
    //private LiveData<List<Recipe>> allRecipes;
    private LiveData<List<Recipe>> userRecipes;

    public RecipeRepository(Application application, String userId){
        MyDatabase database = MyDatabase.getInstance(application);
        recipeDao = database.recipeDao();
        //allRecipes = recipeDao.getAllRecipes(userId);
        userRecipes = recipeDao.findRecipesForUser(userId);
    }
    public RecipeRepository(Application application){
        MyDatabase database = MyDatabase.getInstance(application);
        recipeDao = database.recipeDao();
    }

    public void insert(Recipe recipe){
        new InsertRecipeAsyncTask(recipeDao).execute(recipe);
    }

    /*public void update(Recipe recipe){
        new UpdateRecipeAsyncTask(recipeDao).execute(recipe);
    }*/

    public void updateUserRecipe(String recipeId, String userId, String name, String description, String image){
        new UpdateUserRecipeAsyncTask(recipeDao, recipeId, userId, name, description, image).execute();
    }

    public void delete(Recipe recipe){
        new DeleteRecipeAsyncTask(recipeDao).execute(recipe);
    }

   /* public LiveData<List<Recipe>> getAllRecipes(){
        return allRecipes;
    }*/

    public LiveData<List<Recipe>> findRecipesForUser(){
        return userRecipes;
    }

    private static class InsertRecipeAsyncTask extends AsyncTask<Recipe, Void, Void>{
        private RecipeDao recipeDao;
        private InsertRecipeAsyncTask(RecipeDao recipeDao){
            this.recipeDao = recipeDao;
        }

        @Override
        protected Void doInBackground(Recipe... recipes) {
            recipeDao.insert(recipes[0]);
            return null;
        }
    }

    private static class UpdateRecipeAsyncTask extends AsyncTask<Recipe, Void, Void>{
        private RecipeDao recipeDao;
        private UpdateRecipeAsyncTask(RecipeDao recipeDao){
            this.recipeDao = recipeDao;
        }


        @Override
        protected Void doInBackground(Recipe... recipes) {
            recipeDao.update(recipes[0]);
            return null;
        }
    }

    private static class DeleteRecipeAsyncTask extends AsyncTask<Recipe, Void, Void>{
        private RecipeDao recipeDao;
        private DeleteRecipeAsyncTask(RecipeDao recipeDao){
            this.recipeDao = recipeDao;
        }


        @Override
        protected Void doInBackground(Recipe... recipes) {
            recipeDao.delete(recipes[0]);
            return null;
        }
    }

    private static class UpdateUserRecipeAsyncTask extends AsyncTask<String, Void, Void>{
        private RecipeDao recipeDao;
        private String recipeId;
        private String userId;
        private String name;
        private String description;
        private String image;

        private UpdateUserRecipeAsyncTask(RecipeDao recipeDao, String recipeId, String userId, String name, String description, String image){
            this.recipeDao = recipeDao;
            this.recipeId = recipeId;
            this.userId = userId;
            this.name = name;
            this.description = description;
            this.image = image;
        }


        @Override
        protected Void doInBackground(String... params) {
            recipeDao.updateUserRecipe(recipeId, userId, name, description, image);
            return null;
        }
    }

}
