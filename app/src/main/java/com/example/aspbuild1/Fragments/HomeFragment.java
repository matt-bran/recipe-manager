package com.example.aspbuild1.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aspbuild1.Adapters.HomeAdapter;
import com.example.aspbuild1.Entities.Ingredient;
import com.example.aspbuild1.Entities.RecipeEvent;
import com.example.aspbuild1.R;
import com.example.aspbuild1.ViewModels.RecipeEventViewModel;
import com.example.aspbuild1.ViewModels.RecipeViewModel;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecipeEventViewModel recipeEventViewModel;

    private String user_id;
    private String username;
    private String password;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            user_id = bundle.getString("user_id");
            username = bundle.getString("username");
            password = bundle.getString("password");
        }

        recyclerView = view.findViewById(R.id.home_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        final HomeAdapter adapter = new HomeAdapter();
        recyclerView.setAdapter(adapter);

        recipeEventViewModel = ViewModelProviders.of(this).get(RecipeEventViewModel.class);
        recipeEventViewModel.getRecipeEvents(user_id).observe(this, new Observer<List<RecipeEvent>>() {
            @Override
            public void onChanged(List<RecipeEvent> recipeEvents) {
                adapter.setEvents(recipeEvents);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                RecipeEvent recipeEvent = adapter.getEventAt(viewHolder.getAdapterPosition());
                String id = recipeEvent.getId();
                recipeEventViewModel.deleteFromEvent(id);
                Toast.makeText(getActivity(), "Task cleared", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        return view;
    }
}
