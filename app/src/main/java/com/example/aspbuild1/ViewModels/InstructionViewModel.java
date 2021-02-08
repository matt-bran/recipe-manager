package com.example.aspbuild1.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.aspbuild1.Entities.Ingredient;
import com.example.aspbuild1.Entities.Instruction;
import com.example.aspbuild1.Repositories.InstructionRepository;

import java.util.ArrayList;
import java.util.List;

public class InstructionViewModel extends AndroidViewModel {

    private InstructionRepository repository;

    public InstructionViewModel(@NonNull Application application){
        super(application);
        repository = new InstructionRepository(application);
    }
    public void insert(ArrayList<Instruction> instructions){
        repository.insert(instructions);
    }
    public void delete(String id){
        repository.delete(id);
    }
    public void update(String instructionText , String instruction_id){
        repository.update(instructionText, instruction_id);
    }
    public LiveData<List<Instruction>> getRecipeInsructions(String instructionId){
        return repository.getRecipeInstructions(instructionId);
    }


}
