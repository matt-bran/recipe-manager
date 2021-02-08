package com.example.aspbuild1.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aspbuild1.Entities.Instruction;
import com.example.aspbuild1.R;

import java.util.ArrayList;
import java.util.List;

public class InstructionAdapter extends RecyclerView.Adapter<InstructionAdapter.InstructionHolder> {

    private List<Instruction> instructions = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public InstructionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_instruction, parent, false);
        return new InstructionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionHolder holder, int position) {
        Instruction currentInstruction = instructions.get(position);
        holder.textViewName.setText(currentInstruction.getText());
    }

    @Override
    public int getItemCount() {
        return instructions.size();
    }

    public void setInstructions(List<Instruction> instructions){
        this.instructions = instructions;
    }

    public Instruction getInstructionAt(int position){
        return instructions.get(position);
    }

    class InstructionHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;

        public InstructionHolder (@NonNull View itemView){
            super(itemView);
            textViewName = itemView.findViewById(R.id.item_instruction);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(instructions.get(position));
                    }
                }
            });

        }
    }

    public void removeAt(int position) {
        instructions.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, instructions.size());
    }

    public interface OnItemClickListener {
        void onItemClick(Instruction instruction);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}
