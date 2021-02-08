package com.example.aspbuild1.Adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aspbuild1.Entities.Recipe;
import com.example.aspbuild1.R;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    private List<Recipe> recipes = new ArrayList<>(); //instantiate array for recipes
    private OnItemClickListener listener; //instantiate listener


    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false); //infalte the view
        return new RecipeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder holder, int position) {

        Recipe currentRecipe = recipes.get(position);

        holder.textViewName.setText(currentRecipe.getName());
        holder.textViewDesc.setText(currentRecipe.getDescription());
        holder.imageViewPhoto.setImageURI(Uri.parse(currentRecipe.getPhoto()));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setRecipes(List<Recipe> recipes) { //populate array of existing recipes
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public Recipe getRecipeAt(int position) {
        return recipes.get(position);
    }

    class RecipeHolder extends RecyclerView.ViewHolder { //instantiate variables for xml resources
        private TextView textViewName;
        private TextView textViewDesc;
        private ImageView imageViewPhoto;

        public RecipeHolder(@NonNull View itemView) {      //assign resources to java variables
            super(itemView);
            textViewName = itemView.findViewById(R.id.recipe_name);
            textViewDesc = itemView.findViewById(R.id.recipe_description);
            imageViewPhoto = itemView.findViewById(R.id.img_recipe);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(recipes.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Recipe recipe);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
