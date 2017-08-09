package com.evoteam.evolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 8/9/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    final static int VERSION = 1;
    final static String DATABASE_NAME = "dic.db";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DataBaseSchema.Task.NAME + "(" +
                " _id integer primary key autoincrement, " +
                DataBaseSchema.Task.culs.NAME + ", " +
                DataBaseSchema.Task.culs.DAY + ", " +
                DataBaseSchema.Task.culs.DATE + ", " +
                DataBaseSchema.Task.culs.TIME + ", " +
                DataBaseSchema.Task.culs.DESCRIPTION + ", " +
                DataBaseSchema.Task.culs.ISIMPORTANT +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
