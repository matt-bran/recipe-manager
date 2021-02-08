package com.example.aspbuild1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aspbuild1.Entities.Recipe;
import com.example.aspbuild1.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    private Context mContext;
    private List<Recipe> mRecipes;
    private OnRecipeListener mOnRecipeListener;

    public RecyclerViewAdapter(Context mContext, List<Recipe> mData, OnRecipeListener onRecipeListener) {
        this.mContext = mContext;
        this.mRecipes = mData;
        this.mOnRecipeListener = onRecipeListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_recipe,parent,false);
        MyViewHolder vHolder = new MyViewHolder(v, mOnRecipeListener);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tv_name.setText(mRecipes.get(position).getName());
        holder.tv_description.setText(mRecipes.get(position).getDescription());
        //holder.img.setImageBitmap(mRecipes.get(position).getPhoto());

    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public void setRecipes(List<Recipe> recipes){
        this.mRecipes = recipes;
        notifyDataSetChanged();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_name;
        private TextView tv_description;
        private ImageView img;

        OnRecipeListener onRecipeListener;

        public MyViewHolder(View itemView, OnRecipeListener onRecipeListener) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.recipe_name);
            tv_description = itemView.findViewById(R.id.recipe_description);
            img = itemView.findViewById(R.id.img_recipe);
            this.onRecipeListener = onRecipeListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRecipeListener.onRecipeClick(getAdapterPosition());
        }
    }

    public interface OnRecipeListener{
        void onRecipeClick(int position);
    }

}
