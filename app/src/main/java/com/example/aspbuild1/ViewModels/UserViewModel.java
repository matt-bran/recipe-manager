package com.example.aspbuild1.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.aspbuild1.Entities.User;
import com.example.aspbuild1.Repositories.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository repository;
    private LiveData<List<User>> allUsers;
    private User userLoginInfo;
    private User userInfo;

    public LiveData<List<User>> getAllUsers() {
        allUsers = repository.getAllUsers();
        return allUsers;
    }

    public User getUserLoginInfo(String username, String password){
        userLoginInfo = repository.getUserLoginInfo(username, password);
        return userLoginInfo;
    }

    public User getUserInfo(String user_id){
        userInfo = repository.getUserInfo(user_id);
        return userInfo;
    }

    public UserViewModel(@NonNull Application application){
        super(application);
        repository = new UserRepository(application);
        //allUsers = repository.getAllUsers();
    }

    public void insert(User user){
        repository.insert(user);
    }
    public void update(User user){
        repository.update(user);
    }
    public void delete(User user){
        repository.delete(user);
    }
}

