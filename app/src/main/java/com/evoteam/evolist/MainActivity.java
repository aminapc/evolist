package com.evoteam.evolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher, AdapterView.OnItemClickListener {

    TextView noTaskTextView;
    TextView usernameTextView;
    ImageButton addTaskImageButton;
    EditText searchTasksEditText;
    ListView taskListView;

    public static ArrayList<Task> tasks = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setList();
        textViewVisibility();
    }

    private void init() {
        //text views
        usernameTextView = (TextView) findViewById(R.id.usernameTextViewMainActivity);
        noTaskTextView = (TextView) findViewById(R.id.noTaskTextView);

        //edit texts
        searchTasksEditText = (EditText) findViewById(R.id.searchTaskEditText);
        searchTasksEditText.addTextChangedListener(this);

        //image buttons
        addTaskImageButton = (ImageButton) findViewById(R.id.addTaskImageButton);
        addTaskImageButton.setOnClickListener(this);

        //list view
        taskListView = (ListView) findViewById(R.id.tasksListView);
    }

    private void setList() {
        if(tasks.size() != 0){
            ArrayAdapter<Task> adapter =
                    new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
            taskListView.setAdapter(adapter);
            taskListView.setOnItemClickListener(this);

        }

        textViewVisibility();
    }


    private void textViewVisibility() {
        if (tasks.size() != 0) {
            taskListView.setVisibility(View.VISIBLE);
            noTaskTextView.setVisibility(View.INVISIBLE);
        } else {
            taskListView.setVisibility(View.INVISIBLE);
            noTaskTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addTaskImageButton:
                Intent intent = new Intent(this, AddToDoTaskActivity.class);
                startActivity(intent);
                break;
            default:
                return;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("****", "you clicked the lists");
        Task clickedTask = tasks.get(position);
        Intent intent = new Intent(this, TaskActivity.class);
        Bundle taskBundle = putTheTaskInBundle(clickedTask);
        intent.putExtra("task", taskBundle);
        startActivity(intent);

    }

    public Bundle putTheTaskInBundle(Task task){
        Bundle taskValues = new Bundle();
        taskValues.putString("name"        , task.getName())       ;
        taskValues.putString("day"         , task.getDay())        ;
        taskValues.putString("date"        , task.getDate())       ;
        taskValues.putString("time"        , task.getTime())       ;
        taskValues.putString("description" , task.getDescription());
        taskValues.putBoolean("importance" , task.isImportant())   ;
        return taskValues;
    }
}
