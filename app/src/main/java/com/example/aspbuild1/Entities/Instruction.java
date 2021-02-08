package com.example.aspbuild1.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "instructions", foreignKeys = @ForeignKey(entity = Recipe.class,
        parentColumns = "recipe_Id",
        childColumns = "recipe_id",
        onDelete = ForeignKey.CASCADE) , indices = {@Index(value = "recipe_id")})
public class Instruction {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "instruction_id")
    private String id;

    @ColumnInfo(name = "recipe_id")
    private String recipe_id;

    @ColumnInfo(name = "instruction_text")
    private String text;

    public Instruction(String text, String recipe_id){
        this.text = text;
        this.recipe_id = recipe_id;
        this.id =  UUID.randomUUID().toString();
    }
    @Ignore
    public Instruction(String text){
        this.text = text;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(String recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
