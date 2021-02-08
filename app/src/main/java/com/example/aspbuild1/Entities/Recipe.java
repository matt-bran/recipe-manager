package com.example.aspbuild1.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "recipe_table", foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "user_Id",
        childColumns = "user_id",
        onDelete=ForeignKey.CASCADE), indices = {@Index(value = "user_id")})

public class Recipe /*implements Parcelable*/ {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "recipe_Id")
    private String id;

    @ColumnInfo(name = "user_id")
    private String userId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "image")
    private String photo;


    public Recipe(String name, String description, String photo, String userId){
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.userId = userId;
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
