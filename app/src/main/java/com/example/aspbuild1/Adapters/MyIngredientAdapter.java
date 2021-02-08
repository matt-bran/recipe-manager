package com.example.aspbuild1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.aspbuild1.Entities.Ingredient;
import com.example.aspbuild1.R;

import java.util.List;

public class MyIngredientAdapter extends ArrayAdapter<Ingredient> {

    List<Ingredient> ingredients;
    private OnItemClickListener listener;

    public MyIngredientAdapter(@NonNull Context context,  @NonNull List<Ingredient> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Ingredient ingredient = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_ingredient, parent, false);
        }
        TextView tvName = convertView.findViewById(R.id.item_ingredient_name);
        tvName.setText(ingredient.getText());
        return convertView;
    }


    public int getItemCount() {
        return ingredients.size();
    }
    public void setIngredients(List<Ingredient> ingredients){
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }
    public Ingredient getIngredientAt(int position){
        return ingredients.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(Ingredient ingredient);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
