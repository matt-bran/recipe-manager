package com.example.aspbuild1.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aspbuild1.Entities.Recipe;
import com.example.aspbuild1.R;

public class ViewRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        Intent intent = getIntent();
        Recipe recipeItem = intent.getParcelableExtra("Recipe Item");

        String imageRes = recipeItem.getPhoto();
        String recipeItemName = recipeItem.getName();
        String recipeItemDesc = recipeItem.getDescription();

        ImageView imageView = findViewById(R.id.img_view_activity);
        //imageView.setImageResource(imageRes);

        TextView tvName = findViewById(R.id.name_view_activity);
        tvName.setText(recipeItemName);

        TextView tvDesc = findViewById(R.id.desc_view_activity);
        tvDesc.setText(recipeItemDesc);
    }
}
