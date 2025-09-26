package com.example.gestorgastos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TaskListActivity extends AppCompatActivity {

    private RecyclerView taskRecyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private FloatingActionButton fabAddTask;
    private Button buttonClearCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        taskList = new ArrayList<>();
        taskRecyclerView = findViewById(R.id.task_recycler_view);
        fabAddTask = findViewById(R.id.fab_add_task);
        buttonClearCompleted = findViewById(R.id.button_clear_completed);

        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapter(taskList);
        taskRecyclerView.setAdapter(taskAdapter);

        fabAddTask.setOnClickListener(v -> showAddTaskDialog());

        buttonClearCompleted.setOnClickListener(v -> clearCompletedTasks());
        
        addSampleTasks();
    }

    private void addSampleTasks() {
        taskList.add(new Task("Hacer la cama", "8:00 AM"));
        taskList.add(new Task("Preparar el desayuno", "8:15 AM"));
        taskAdapter.notifyDataSetChanged();
    }

    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_task, null);
        builder.setView(dialogView);

        EditText editTextTaskName = dialogView.findViewById(R.id.edit_text_task_name);
        EditText editTextTaskTime = dialogView.findViewById(R.id.edit_text_task_time);
        Button buttonCancel = dialogView.findViewById(R.id.button_cancel);
        Button buttonAdd = dialogView.findViewById(R.id.button_add);

        AlertDialog dialog = builder.create();

        buttonCancel.setOnClickListener(v -> dialog.dismiss());

        buttonAdd.setOnClickListener(v -> {
            String taskName = editTextTaskName.getText().toString().trim();
            String taskTime = editTextTaskTime.getText().toString().trim();

            if (!taskName.isEmpty()) {
                taskList.add(new Task(taskName, taskTime));
                taskAdapter.notifyItemInserted(taskList.size() - 1);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void clearCompletedTasks() {
        Iterator<Task> iterator = taskList.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task.isCompleted()) {
                iterator.remove();
            }
        }
        taskAdapter.notifyDataSetChanged();
    }
}