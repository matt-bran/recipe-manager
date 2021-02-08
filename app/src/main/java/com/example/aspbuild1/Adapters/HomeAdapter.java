package com.example.aspbuild1.Adapters;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aspbuild1.Entities.RecipeEvent;
import com.example.aspbuild1.R;
import com.example.aspbuild1.ViewModels.RecipeEventViewModel;
import com.example.aspbuild1.ViewModels.RecipeViewModel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.EventHolder> {

    private List<RecipeEvent> events = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_event, parent, false); // inflate view to card view
        return new EventHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {

        RecipeEvent event = events.get(position);

        holder.textViewName.setText(event.getEvent_name());
        holder.textViewDate.setText("Event planned for " + event.getDate());
        holder.imageView.setImageURI(Uri.parse(event.getEvent_image()));
        holder.textViewNote.setText("Event note: " + event.getEvent_note());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void setEvents(List<RecipeEvent> events){
        this.events = events;
        notifyDataSetChanged();
    }
    public RecipeEvent getEventAt(int position) {
        return events.get(position);
    }

    class EventHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewDate;
        private ImageView imageView;
        private TextView textViewNote;


        public EventHolder(@NonNull View itemView){
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_event_name);
            textViewDate = itemView.findViewById(R.id.text_view_event_date);
            imageView = itemView.findViewById(R.id.image_view_event_image);
            textViewNote = itemView.findViewById(R.id.text_view_event_note);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(events.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(RecipeEvent recipeEvent);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
