package com.evoteam.evolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by user on 8/9/2017.
 */

public class DataBaseManager {

    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase;

    private ArrayList taskList;
    private static TaskCursorWrapper cursor;

    public DataBaseManager(Context context){
        mContext = context.getApplicationContext();
        mSQLiteDatabase = new DataBaseHelper(mContext).getWritableDatabase();
        cursor = queryTask();
    }

    public void addTask(Task currentTask){
        ContentValues value = getTaskValues(currentTask);
        mSQLiteDatabase.insert(DataBaseSchema.Task.NAME, null, value);
    }

    private static ContentValues getTaskValues(Task currentTask){
        ContentValues values = new ContentValues();

        values.put(DataBaseSchema.Task.culs.NAME        , currentTask.getName());
        values.put(DataBaseSchema.Task.culs.DAY         , currentTask.getDay());
        values.put(DataBaseSchema.Task.culs.DATE        , currentTask.getDate());
        values.put(DataBaseSchema.Task.culs.TIME        , currentTask.getTime());
        values.put(DataBaseSchema.Task.culs.DESCRIPTION , currentTask.getDescription());
        values.put(DataBaseSchema.Task.culs.ISIMPORTANT , String.valueOf(currentTask.isImportant()));

        return values;
    }

    public ArrayList getTasks(){
        taskList = new ArrayList<>();

        cursor.moveToFirst();

        while(! cursor.isAfterLast()){
            Task temp = cursor.getTask();
            taskList.add(temp);
            cursor.moveToNext();
        }

        return taskList;
    }

    private TaskCursorWrapper queryTask() {
        Cursor cursor = mSQLiteDatabase.query(DataBaseSchema.Task.NAME
                , null, null, null, null, null, null);

        return new TaskCursorWrapper(cursor);
    }

    public void deleteTask(Task currentTask){
        mSQLiteDatabase.delete(DataBaseSchema.Task.NAME, DataBaseSchema.Task.culs.NAME + "=" + "'" + currentTask.getName() + "'"
                + " and " + DataBaseSchema.Task.culs.DAY + "=" + "'" + currentTask.getDay() + "'"
                + " and " + DataBaseSchema.Task.culs.DATE + "=" + "'" + currentTask.getDate() + "'"
                + " and " + DataBaseSchema.Task.culs.TIME + "=" + "'" + currentTask.getTime() + "'", null);
    }

    private int databaseSize() {
        int size = 0;
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            size ++;
            cursor.moveToNext();
        }
        return size;
    }

    public int getDataBaseSize() {
        int size = databaseSize();
        return size;
    }

}
