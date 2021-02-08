package com.example.aspbuild1.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

@Entity (tableName = "recipe_events" , foreignKeys = {@ForeignKey(entity = User.class,
                                        parentColumns = "user_Id",
                                        childColumns = "user_id") ,
                                                      @ForeignKey(entity = Recipe.class ,
                                        parentColumns = "recipe_Id",
                                        childColumns = "recipe_id")}, indices = {@Index(value = "user_id"), @Index(value = "recipe_id")})
public class RecipeEvent {

    @NonNull
    @PrimaryKey
    @ColumnInfo (name = "id")
    private String id;

    @ColumnInfo (name = "recipe_id")
    private String recipe_id;

    @ColumnInfo (name = "date")
    private String date;

    @ColumnInfo (name = "user_id")
    private String user_id;

    @ColumnInfo (name = "event_image")
    private String event_image;

    @ColumnInfo (name = "event_note")
    private String event_note;

    @ColumnInfo (name = "event_name")
    private String event_name;

    public RecipeEvent (String recipe_id , String user_id, String date, String event_image, String event_name, String event_note) {
        this.recipe_id = recipe_id;
        this.user_id = user_id;
        this.date = date;
        this.id =  UUID.randomUUID().toString();
        this.event_image = event_image;
        this.event_name = event_name;
        this.event_note = event_note;
    }

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(String recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEvent_image() {
        return event_image;
    }

    public void setEvent_image(String event_image) {
        this.event_image = event_image;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_note() {
        return event_note;
    }

    public void setEvent_note(String event_note) {
        this.event_note = event_note;
    }
}
