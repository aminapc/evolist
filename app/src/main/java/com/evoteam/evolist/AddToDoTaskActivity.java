package com.evoteam.evolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class AddToDoTaskActivity extends AppCompatActivity implements View.OnClickListener {

    EditText taskName, taskDay, taskDate, taskTime, taskDescription;
    CheckBox taskImportance;
    Button taskDateButton, submit;

    Task currentTask = new Task();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do_task);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Add a Task");
        }

        init();
    }

    private void init() {
        taskName = (EditText) findViewById(R.id.taskNameEditText);
        taskDay = (EditText) findViewById(R.id.taskDayEditText);
        taskDate = (EditText) findViewById(R.id.taskDateEditText);
        taskTime = (EditText) findViewById(R.id.taskTimeEditText);
        taskDescription = (EditText) findViewById(R.id.taskDescriptionEditText);

        taskImportance = (CheckBox) findViewById(R.id.taskImportanceCheckBoxInTaskActivity);

        taskDateButton = (Button) findViewById(R.id.taskDateButton);
        submit = (Button) findViewById(R.id.taskSubmitButton);

        taskDateButton.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.taskSubmitButton:
                makingReadyTheTask();
                if (isCorrectTask()){
                    MainActivity.tasks.add(currentTask);
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(this, "Your Task has been added.", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(this, "لطفا فیلدهای کارتان را کامل وارد کنید.", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private boolean isCorrectTask() {
        if(currentTask.getName() != " " && currentTask.getDay() != " "
                && currentTask.getDate() != " " && currentTask.getTime() != " ")
            return true;
        return false;
    }

    private void makingReadyTheTask() {

        currentTask = new Task(taskName.getText().toString(),
                taskDay.getText().toString(),
                taskDate.getText().toString(),
                taskTime.getText().toString(),
                taskDescription.getText().toString(),
                taskImportance.isChecked()
                );

    }
}
