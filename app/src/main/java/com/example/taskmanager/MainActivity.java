package com.example.taskmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TaskAdapter adapter;
    ArrayList<Task> taskList;
    Button addTaskBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.taskRecyclerView);
        addTaskBtn = findViewById(R.id.addTaskBtn);
        taskList = new ArrayList<>();

        adapter = new TaskAdapter(this, taskList, position -> {
            taskList.remove(position);
            adapter.notifyItemRemoved(position);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        addTaskBtn.setOnClickListener(v -> showAddTaskDialog());
    }

    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Task");

        final EditText input = new EditText(this);
        input.setHint("Enter task title");
        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String taskTitle = input.getText().toString().trim();
            if (!taskTitle.isEmpty()) {
                taskList.add(new Task(taskTitle));
                adapter.notifyItemInserted(taskList.size() - 1);
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }
}
