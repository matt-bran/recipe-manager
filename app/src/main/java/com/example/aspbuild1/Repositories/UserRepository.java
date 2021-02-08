package com.example.aspbuild1.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.aspbuild1.DAOs.UserDao;
import com.example.aspbuild1.Entities.User;
import com.example.aspbuild1.MyDatabase;

import java.util.List;

public class UserRepository {

    private UserDao userDao;
    private LiveData<List<User>> allUsers;
    private static User userLoginInfo;
    private static User userInfo;

    public UserRepository(Application application) {
        MyDatabase database = MyDatabase.getInstance(application);
        userDao = database.userDao();
        //allUsers = userDao.getAllUsers();
    }

    public void insert(User user){
        new InsertUserAsyncTask(userDao).execute(user);
    }
    public void update(User user){
        new UpdateUserAsyncTask(userDao).execute(user);
    }
    public void delete(User user){
        new DeleteUserAsyncTask(userDao).execute(user);
    }
    public LiveData<List<User>> getAllUsers(){
        allUsers = userDao.getAllUsers();
        return allUsers;
    }
    public User getUserLoginInfo(String username, String password){
        new getUserLoginInfoAsyncTask(userDao, username, password).execute();
        return userLoginInfo;
    }
    public User getUserInfo(String userid){
        new getUserInfoAsyncTask(userDao).execute(userid);
        return userInfo;
    }

    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void>{
        private UserDao userDao;
        private InsertUserAsyncTask(UserDao userDao){
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }

    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void>{
        private UserDao userDao;
        private UpdateUserAsyncTask(UserDao userDao){
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.update(users[0]);
            return null;
        }
    }
    private static class DeleteUserAsyncTask extends AsyncTask<User, Void, Void>{
        private UserDao userDao;
        private DeleteUserAsyncTask(UserDao userDao){
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.delete(users[0]);
            return null;
        }
    }
    private static class getUserLoginInfoAsyncTask extends AsyncTask<String, Void, Void>{
        private UserDao userDao;
        private String username;
        private String password;
        //private User user;

        private getUserLoginInfoAsyncTask(UserDao userDao, String username, String password){
            this.userDao = userDao;
            this.username = username;
            this.password = password;
        }

        @Override
        protected Void doInBackground(String... strings) {
            userLoginInfo = userDao.getUserLoginInfo(username, password);
            return null;
        }
    }
    private static class getUserInfoAsyncTask extends AsyncTask<String, Void, Void>{
        private UserDao userDao;
        //private int user_id;

        private  getUserInfoAsyncTask(UserDao userDao){
            this.userDao = userDao;
            //this.user_id = user_id;
        }

        @Override
        protected Void doInBackground(String... params) {
            String user_id = params[0];
            userInfo = userDao.getUserInfo(user_id);
            return null;
        }
    }
}
