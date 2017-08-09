package com.evoteam.evolist;

import android.database.Cursor;
import android.database.CursorWrapper;

/**
 * Created by user on 8/9/2017.
 */

public class TaskCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Task getTask(){
        String name = getString(getColumnIndex(DataBaseSchema.Task.culs.NAME));
        String day = getString(getColumnIndex(DataBaseSchema.Task.culs.DAY));
        String date = getString(getColumnIndex(DataBaseSchema.Task.culs.DATE));
        String time = getString(getColumnIndex(DataBaseSchema.Task.culs.TIME));
        String description = getString(getColumnIndex(DataBaseSchema.Task.culs.DESCRIPTION));
        boolean isImportant = Boolean.parseBoolean(getString(getColumnIndex(DataBaseSchema.Task.culs.ISIMPORTANT)));

        Task currentTask = new Task(name, day, date, time, description, isImportant);
        return currentTask;
    }
}
