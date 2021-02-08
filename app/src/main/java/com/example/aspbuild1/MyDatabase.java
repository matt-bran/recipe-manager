package com.example.aspbuild1;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.aspbuild1.DAOs.IngredientDao;
import com.example.aspbuild1.DAOs.InstructionDao;
import com.example.aspbuild1.DAOs.RecipeDao;
import com.example.aspbuild1.DAOs.RecipeEventDao;
import com.example.aspbuild1.DAOs.UserDao;
import com.example.aspbuild1.Entities.Ingredient;
import com.example.aspbuild1.Entities.Instruction;
import com.example.aspbuild1.Entities.Recipe;
import com.example.aspbuild1.Entities.RecipeEvent;
import com.example.aspbuild1.Entities.User;

@Database(entities = {Recipe.class, User.class, Ingredient.class, RecipeEvent.class, Instruction.class}, version = 31, exportSchema = false)

public abstract class MyDatabase extends RoomDatabase {

    private static MyDatabase instance;
    public abstract RecipeDao recipeDao();
    public abstract UserDao userDao();
    public abstract IngredientDao ingredientDao();
    public abstract RecipeEventDao recipeEventDao();
    public abstract InstructionDao instructionDao();

    public static synchronized MyDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), MyDatabase.class, "my_database").fallbackToDestructiveMigration().addCallback(roomCallBack).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private RecipeDao recipeDao;
        private UserDao userDao;

        private PopulateDbAsyncTask(MyDatabase db){
            recipeDao = db.recipeDao();
            userDao = db.userDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //recipeDao.insert(new Recipe("Title 1", "description 1", "content://com.android.providers.media.documents/document/image%3A73"));
            //recipeDao.insert(new Recipe("Title 2", "description 2", "content://com.android.providers.media.documents/document/image%3A73"));
            //recipeDao.insert(new Recipe("Title 3", "description 3", "content://com.android.providers.media.documents/document/image%3A73"));
            return null;
        }
    }

}
