package com.example.aspbuild1.ViewModels;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.aspbuild1.ViewModels.RecipeViewModel;

public class RecipeViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private String userId;

    public RecipeViewModelFactory(Application app, String userId){
        mApplication = app;
        this.userId = userId;
    }

    public RecipeViewModelFactory(Application app){
        mApplication = app;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass){
        if (userId.isEmpty()){
            return (T) new RecipeViewModel(mApplication);
        } else {
            return (T) new RecipeViewModel(mApplication, userId);
        }
    }

}
