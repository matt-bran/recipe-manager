package com.example.aspbuild1.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.aspbuild1.DAOs.InstructionDao;
import com.example.aspbuild1.Entities.Instruction;
import com.example.aspbuild1.MyDatabase;

import java.util.ArrayList;
import java.util.List;

public class InstructionRepository {

    private InstructionDao instructionDao;

    public InstructionRepository(Application application){
        MyDatabase database = MyDatabase.getInstance(application);
        instructionDao = database.instructionDao();
    }

    public void insert(ArrayList<Instruction> instructions){
        new InsertInstructionAsyncTask(instructionDao).execute(instructions);
    }
    public void delete(String id){
        new DeleteInstructionAsyncTask(instructionDao).execute(id);
    }

    public void update(String instructionText, String instruction_id){
        new UpdateInstructionAsyncTask(instructionDao).execute(instructionText, instruction_id);

    }
    public LiveData<List<Instruction>> getRecipeInstructions(String recipeId){
        return instructionDao.getRecipeInstructions(recipeId);
    }

    private static class InsertInstructionAsyncTask extends AsyncTask<ArrayList<Instruction>, Void, Void>{

        private InstructionDao instructionDao;
        private ArrayList<Instruction> instructionList;
        private Instruction instructionItem;

        private InsertInstructionAsyncTask(InstructionDao instructionDao){
            this.instructionDao = instructionDao;

        }
        @Override
        protected Void doInBackground(ArrayList<Instruction>... instructions) {

            instructionList = instructions[0];

            for (int i = 0; i < instructionList.size(); i++){
                instructionItem = instructionList.get(i);
                instructionDao.insert(instructionItem);
            }
            return null;
        }
    }

    private static class UpdateInstructionAsyncTask extends AsyncTask<String, Void, Void> {
        private InstructionDao instructionDao;
        private String text;
        private String id;

        private UpdateInstructionAsyncTask(InstructionDao instructionDao){
            this.instructionDao = instructionDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            text = strings[0];
            id = strings[1];
            instructionDao.updateRecipeInstruction(text, id);
            return null;
        }
    }

    private static class DeleteInstructionAsyncTask extends AsyncTask<String, Void, Void>{
        private InstructionDao instructionDao;

        private DeleteInstructionAsyncTask(InstructionDao instructionDao){
            this.instructionDao = instructionDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            instructionDao.deleteInstruction(strings[0]);
            return null;
        }
    }

}
