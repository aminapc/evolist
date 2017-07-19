package com.evoteam.evolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    TextView noTaskTextView;
    TextView usernameTextView;
    ImageButton addTaskImageButton;
    EditText searchTasksEditText;
    ListView taskListView;

    ArrayList<Task> tasks = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        task();
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

    private void task() {
//        Task t1 = new Task();
//        t1.setName("Farzad");
//        Task t2 = new Task();
//        t2.setName("Mana");
//        Task t3 = new Task();
//        t3.setName("Mostafa");
//        Task t4 = new Task();
//        t4.setName("Parsa");
//        tasks.add(t1);
//        tasks.add(t2);
//        tasks.add(t3);
//        tasks.add(t4);
    }

    private void setList() {
        if(tasks.size() != 0){
            ArrayAdapter<Task> adapter =
                    new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
            taskListView.setAdapter(adapter);

        }
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
                Intent intent = new Intent(this, AddToDoTask.class);
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
}
