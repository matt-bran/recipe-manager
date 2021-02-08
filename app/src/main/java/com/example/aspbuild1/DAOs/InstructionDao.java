package com.example.aspbuild1.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.aspbuild1.Entities.Instruction;

import java.util.List;

@Dao
public interface InstructionDao {

    @Insert
    void insert(Instruction instruction);

    @Update
    void update(Instruction ingredient);

    @Delete
    void delete(Instruction instruction);

    @Query("SELECT * FROM instructions WHERE recipe_id = :id")
    LiveData<List<Instruction>> getRecipeInstructions(final String id);

    @Query("UPDATE instructions SET instruction_text = :instruction_text WHERE instruction_id = :instruction_id")
    int updateRecipeInstruction(final String instruction_text, final String instruction_id);

    @Query("DELETE FROM instructions WHERE instruction_id = :instruction_id ")
    int deleteInstruction(final String instruction_id);
}

