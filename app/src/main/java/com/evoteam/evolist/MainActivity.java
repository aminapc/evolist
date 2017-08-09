package com.evoteam.evolist;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher, AdapterView.OnItemClickListener {

    TextView noTaskTextView;
    TextView usernameTextView;
    ImageButton addTaskImageButton;
    EditText searchTasksEditText;
    ListView taskListView;

    public static ArrayList<Task> tasksArrayList;

    public static DataBaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setTaskLists();
        setList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTaskLists();
        setList();
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

        //arrayList
        tasksArrayList = new ArrayList<>();

        //dataBase manager
        dbManager = new DataBaseManager(this);
    }

    //menu stuff
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sendDataMenuItem:
                try {
                    JSONArray datas = dbManager.getDataBaseInJson();
                    if(datas.length() > 0){
                        makeSureToSendDatas(datas.toString());
                    }else{
                        Toast.makeText(this, "اطلاعاتی برای فرستادن وجود ندارد.", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.getDataMenuItem:
                makeSureToGetDatas();
                break;
            case R.id.deleteAllMenuItem:
                makeSureToDelete();
                break;
            case R.id.signOutMenuItem:
                makeSureToSignOut();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setTaskLists() {
        tasksArrayList = dbManager.getTasks();
    }

    private void setList() {
        if(tasksArrayList.size() != 0){
            TaskAdapter adapter =
                    new TaskAdapter(this, tasksArrayList);
            taskListView.setAdapter(adapter);
            taskListView.setOnItemClickListener(this);

        }

        textViewVisibility();
    }

    private void textViewVisibility() {
        if (tasksArrayList.size() != 0) {
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
        Task clickedTask = tasksArrayList.get(position);
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

    //requests to server
    private class Request extends AsyncTask<String, String, String>{

        private ProgressDialog authProgressDialog;
        private String job;

        private Request(Activity appActivity) {
            authProgressDialog = new ProgressDialog(appActivity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            authProgressDialog.setMessage("در حال برقراری ارتباط با سرور ...");
            authProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String result;
            job = params[0];
            switch (job){
                case "post":
                    result = HttpConnectionManager.postData(params[1]);
                    Log.d("***", params[1]);
                    Log.d("***responsesend", result);
                    break;
                case "get":
                    result = HttpConnectionManager.getData();
                    Log.d("***responseget", result);
                    break;
                default:
                    break;
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            authProgressDialog.cancel();
            SignUpActivity.response = s;
            super.onPostExecute(s);
        }
    }

    private void makeSureToSendDatas(final String datas) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder .setTitle("Send")
                .setCancelable(true)
                .setMessage("آیا تمایل دارید که اطلاعات خود را به سرور بفرستید؟")
                .setIcon(R.drawable.calender)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(HttpConnectionManager.isOnline(MainActivity.this)) {
                            Request connection = new Request(MainActivity.this);
                            connection.execute("post", datas);
                        }else{
                            Toast.makeText(MainActivity.this, "لطفا دسترسی خود را به اینترنت چک کنید.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("No", null);

        builder.show();
    }

    private void makeSureToGetDatas() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder .setTitle("Refresh")
                .setCancelable(true)
                .setMessage("آیا از به روز کردن اطلاعات خود اطمینان دارید؟")
                .setIcon(R.drawable.calender)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(HttpConnectionManager.isOnline(MainActivity.this)) {
                            Request connection = new Request(MainActivity.this);
                            connection.execute("get");
                        }else{
                            Toast.makeText(MainActivity.this, "لطفا دسترسی خود را به اینترنت چک کنید.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("No", null);

        builder.show();
    }

    private void makeSureToDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder .setTitle("Delete Dialog")
                .setCancelable(true)
                .setMessage("آیا از اینکه میخواهید اطلاعات خود را پاک کنید اطمینان دارید؟")
                .setIcon(R.drawable.calender)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbManager.deleteAllDataBase();
                        MainActivity.this.recreate();
                    }
                })
                .setNegativeButton("No", null);

        builder.show();
    }

    private void makeSureToSignOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder .setTitle("Sign Out")
                .setCancelable(true)
                .setMessage("آیا میخواهید از حساب کاربری خود خارج شوید؟")
                .setIcon(R.drawable.avatar2)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbManager.deleteAllDataBase();
                        Intent intent = new Intent(MainActivity.this, SignUpOrInActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", null);

        builder.show();
    }
}
