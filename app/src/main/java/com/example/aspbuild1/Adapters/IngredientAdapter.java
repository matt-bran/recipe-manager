package com.example.aspbuild1.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aspbuild1.Entities.Ingredient;
import com.example.aspbuild1.Entities.Recipe;
import com.example.aspbuild1.R;

import java.util.ArrayList;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientHolder> {

    private List<Ingredient> ingredients = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public IngredientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new IngredientHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientHolder holder, int position) {
        Ingredient currentIngredient = ingredients.get(position);
        holder.textViewName.setText(currentIngredient.getText());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void setIngredients(List<Ingredient> ingredients){
        this.ingredients = ingredients;
    }

    public Ingredient getIngredientAt(int position){
        return ingredients.get(position);
    }

    class IngredientHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;

        public IngredientHolder (@NonNull View itemView){
            super(itemView);
            textViewName = itemView.findViewById(R.id.item_ingredient_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(ingredients.get(position));
                    }
                }
            });

        }
    }

    public void removeAt(int position) {
        ingredients.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, ingredients.size());
    }

    public interface OnItemClickListener {
        void onItemClick(Ingredient ingredient);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
