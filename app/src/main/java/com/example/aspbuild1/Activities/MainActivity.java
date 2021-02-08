package com.example.aspbuild1.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aspbuild1.Fragments.HomeFragment;
import com.example.aspbuild1.Fragments.MealPlanFragment;
import com.example.aspbuild1.R;
import com.example.aspbuild1.Fragments.RecipesFragment;
import com.example.aspbuild1.Entities.User;
import com.example.aspbuild1.ViewModels.UserViewModel;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private TextView nameTextView;
    private TextView usernameTextView;
    private UserViewModel userViewModel;
    private String user_id;
    private String username;
    private String password;

    public static final String EXTRA_USER_USERNAME =
            "com.example.aspbuild1.EXTRA_USER_USERNAME";
    public static final String EXTRA_USER_PASSWORD =
            "com.example.aspbuild1.EXTRA_USER_PASSWORD";
    private static final String KEY_USERNAME = "username_key";
    private static final String KEY_PASSWORD = "password_key";
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        nameTextView = headerView.findViewById(R.id.nav_name);
        usernameTextView = headerView.findViewById(R.id.nav_username);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigtaion_drawer_open, R.string.navigtaion_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
            getSupportActionBar().setTitle("Tasks");
        }

        if (savedInstanceState != null) {
            username = savedInstanceState.getString(KEY_USERNAME);
            password = savedInstanceState.getString(KEY_PASSWORD);
            //Toast.makeText(this, "saved instance executed", Toast.LENGTH_SHORT).show();
        }

        Intent intent = getIntent();

        if (intent.getStringExtra(EXTRA_USER_USERNAME) != null && intent.getStringExtra(EXTRA_USER_PASSWORD) != null ){
            username = intent.getStringExtra(EXTRA_USER_USERNAME);
            password = intent.getStringExtra(EXTRA_USER_PASSWORD);
            //Toast.makeText(this, "getintent executed", Toast.LENGTH_SHORT).show();
        }
        //Toast.makeText(this, "user: " + username + " + id: " + user_id, Toast.LENGTH_SHORT).show();

        User user = userViewModel.getUserLoginInfo(username, password);

        String displayUsername = user.getUsername();
        String displayFirstandLast = user.getFirstName() + " " + user.getLastName();
        user_id = user.getId();

        Log.i(TAG, "LoginActivity, OnActivityResult - get userId value: " + user.getId());
        //Toast.makeText(this, "user_id: " + user_id, Toast.LENGTH_SHORT).show();

        nameTextView.setText(displayFirstandLast);
        usernameTextView.setText(displayUsername);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                Bundle homeArgs = new Bundle();
                homeArgs.putString("user_id", user_id);
                homeArgs.putString("username",username);
                homeArgs.putString("password",password);
                HomeFragment homeFragment = new HomeFragment();
                homeFragment.setArguments(homeArgs);
                getSupportActionBar().setTitle("Tasks");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
                break;
            case R.id.nav_recipes:
                Bundle args = new Bundle();
                args.putString("user_id", user_id);
                args.putString("username",username);
                args.putString("password",password);
                RecipesFragment recipesFragment = new RecipesFragment();
                recipesFragment.setArguments(args);
                getSupportActionBar().setTitle("Recipe Book");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, recipesFragment).commit();
                break;
            case R.id.nav_meal_plan:
                Bundle mealArgs = new Bundle();
                mealArgs.putString("user_id", user_id);
                mealArgs.putString("username",username);
                mealArgs.putString("password",password);
                MealPlanFragment mealPlanFragment = new MealPlanFragment();
                mealPlanFragment.setArguments(mealArgs);
                getSupportActionBar().setTitle("Meal Planning");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mealPlanFragment).commit();
                break;
            case R.id.nav_signOut:
                Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString(KEY_USERNAME, username);
        outState.putString(KEY_PASSWORD, password);

        super.onSaveInstanceState(outState);
    }
}
