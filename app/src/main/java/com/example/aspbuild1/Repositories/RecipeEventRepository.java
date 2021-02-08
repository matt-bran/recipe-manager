package com.example.aspbuild1.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.aspbuild1.DAOs.RecipeEventDao;
import com.example.aspbuild1.Entities.Recipe;
import com.example.aspbuild1.Entities.RecipeEvent;
import com.example.aspbuild1.MyDatabase;

import java.util.List;

public class RecipeEventRepository {

    private RecipeEventDao recipeEventDao;
    private LiveData<List<RecipeEvent>> recipeEvents;

    public RecipeEventRepository(Application application){
        MyDatabase database = MyDatabase.getInstance(application);
        recipeEventDao = database.recipeEventDao();
    }

    public void insert(RecipeEvent recipeEvent){
        new InsertRecipeEventAsyncTask(recipeEventDao).execute(recipeEvent);
    }

    public void deleteFromRecipe(String id){
        new DeleteFromRecipeAsyncTask(recipeEventDao).execute(id);
    }
    public void deleteFromEvent(String id){
        new DeleteFromEventAsyncTask(recipeEventDao).execute(id);
    }
    public void update(String id, String name, String image){
        new UpdateAsyncTask(recipeEventDao).execute(image, name, id);
    }
    public LiveData<List<RecipeEvent>> getRecipeEvents(String user_id){
        return recipeEvents = recipeEventDao.getRecipeEvents(user_id);
    }

    private static class InsertRecipeEventAsyncTask extends AsyncTask<RecipeEvent, Void, Void> {
        private RecipeEventDao recipeEventDao;

        private InsertRecipeEventAsyncTask(RecipeEventDao recipeEventDao){
            this.recipeEventDao = recipeEventDao;
        }

        @Override
        protected Void doInBackground(RecipeEvent... recipeEvents) {
            recipeEventDao.insert(recipeEvents[0]);
            return null;
        }
    }
    private static class DeleteFromRecipeAsyncTask extends android.os.AsyncTask<String, Void, Void> {

        private RecipeEventDao recipeEventDao;

        private DeleteFromRecipeAsyncTask(RecipeEventDao recipeEventDao){
            this.recipeEventDao = recipeEventDao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            recipeEventDao.deleteRecipeEventFromRecipe(strings[0]);
            return null;
        }
    }
    private static class DeleteFromEventAsyncTask extends android.os.AsyncTask<String, Void, Void> {

        private RecipeEventDao recipeEventDao;

        private DeleteFromEventAsyncTask(RecipeEventDao recipeEventDao){
            this.recipeEventDao = recipeEventDao;
        }


        @Override
        protected Void doInBackground(String... strings) {
            recipeEventDao.deleteRecipeEventFromEvent(strings[0]);
            return null;
        }
    }
    private static class UpdateAsyncTask extends AsyncTask<String, Void, Void>{

        private RecipeEventDao recipeEventDao;
        private String image;
        private String name;
        private String id;

        private UpdateAsyncTask(RecipeEventDao recipeEventDao){
            this.recipeEventDao = recipeEventDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            image = strings[0];
            name = strings[1];
            id = strings[2];

            recipeEventDao.updateRecipeEvent(image, name, id);
            return null;
        }
    }

}
