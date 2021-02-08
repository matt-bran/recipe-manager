package com.example.aspbuild1.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.aspbuild1.Activities.AddRecipeActivity;
import com.example.aspbuild1.Activities.AddRecipeEventActivity;
import com.example.aspbuild1.Entities.RecipeEvent;
import com.example.aspbuild1.R;
import com.example.aspbuild1.ViewModels.RecipeEventViewModel;
import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class MealPlanFragment extends Fragment {

    public static final int ADD_EVENT_REQUEST = 1;

    private RecipeEventViewModel recipeEventViewModel;
    private String user_id;
    private String username;
    private String password;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_meal_plan, container, false);
        recipeEventViewModel = ViewModelProviders.of(this).get(RecipeEventViewModel.class);

        Bundle bundle = this.getArguments();
        if (bundle != null){
            user_id = bundle.getString("user_id");
            username = bundle.getString("username");
            password = bundle.getString("password");
        }

        Date today= new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        CalendarPickerView datePicker = view.findViewById(R.id.calendar);
        datePicker.init(today, nextYear.getTime()).withSelectedDate(today);

        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);
                Calendar calendarSelected = Calendar.getInstance();
                calendarSelected.setTime(date);
                String selectedStringDate = "" + calendarSelected.get(Calendar.MONTH) + "/" + (calendarSelected.get(Calendar.DAY_OF_MONTH) + 1) + "/" + calendarSelected.get(Calendar.YEAR);

                Intent intent = new Intent(getActivity(), AddRecipeEventActivity.class);
                intent.putExtra("selectedDate", selectedDate);
                intent.putExtra("selectedStringDate", selectedStringDate);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                intent.putExtra("user_id", user_id);
                startActivityForResult(intent, ADD_EVENT_REQUEST);
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_EVENT_REQUEST && resultCode == RESULT_OK){

            String recipe_id = data.getStringExtra("recipe_id");
            String user_id = data.getStringExtra("user_id");
            String date = data.getStringExtra("date");
            String recipe_name = data.getStringExtra("recipe_name");
            String recipe_image = data.getStringExtra("recipe_image");
            String note = data.getStringExtra("event_note");

            RecipeEvent recipeEvent = new RecipeEvent(recipe_id, user_id, date, recipe_image, recipe_name, note);
            recipeEventViewModel.insert(recipeEvent);
            Toast.makeText(getActivity(), "Task created", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}
