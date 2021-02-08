package com.example.aspbuild1.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.aspbuild1.Entities.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM users_table ORDER BY user_id ASC")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM users_table WHERE username=:userName AND password=:passW")
    User getUserLoginInfo(final String userName, final String passW);

    @Query("SELECT * FROM users_table WHERE user_Id=:userId")
    User getUserInfo(final String userId);
}
