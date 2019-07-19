package com.premar.muvi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.premar.muvi.R;
import com.premar.muvi.model.Todos;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    private List<Todos> todosList;
    private Context context;

    public TodoAdapter(List<Todos> todos, Context context) {
        this.todosList = todos;
        this.context = context;

    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {
       TextView text;
       TextView complete;

        public TodoViewHolder(@NonNull View v) {
            super(v);
            text = v.findViewById(R.id.tv_title);
            complete = v.findViewById(R.id.tv_complete);

        }
    }
    @NonNull
    @Override
    public TodoAdapter.TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_todo, parent, false);
        return new TodoAdapter.TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder todoViewHolder, int i) {
        boolean checkComplete = todosList.get(i).isComplete();
        if(checkComplete){
            todoViewHolder.complete.setText("Completed");
        }
        else {
            todoViewHolder.complete.setText("Not Complete");
        }
       //todoViewHolder.complete.setText(String.valueOf(todosList.get(i).getId()) );
       todoViewHolder.text.setText(todosList.get(i).getText());
    }


    @Override
    public int getItemCount() {
        return todosList.size();
    }


}
