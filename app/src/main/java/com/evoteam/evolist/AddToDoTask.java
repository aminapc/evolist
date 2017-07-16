package com.evoteam.evolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class AddToDoTask extends AppCompatActivity implements View.OnClickListener {

    EditText taskName, taskDay, taskDate, taskTime, taskDescription;
    CheckBox taskImportance;
    Button taskDateButton, submit;

    Task currentTask;

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

        taskImportance = (CheckBox) findViewById(R.id.taskImportanceCheckBox);

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
                    Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
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
        currentTask = new Task();
        currentTask.setName(taskName.getText().toString());
        currentTask.setDay(taskDay.getText().toString());
        currentTask.setDate(taskDate.getText().toString());
        currentTask.setTime(taskTime.getText().toString());
        currentTask.setDescription(taskDescription.getText().toString());
        currentTask.setImportant(taskImportance.isChecked());
    }
}
