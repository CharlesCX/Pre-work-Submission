package com.charles.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.UUID;

/**
 * Created by charles on 16/6/27.
 */
public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    public static final String DATABASE_NAME = "TodoDB.db";
    public static final String TASK_ID = "_id";
    public static final String TASK_TITLE = "title";
    public static final String TASK_NOTE = "note";
    public static final String TASK_YEAR = "year";
    public static final String TASK_MONTH = "month";
    public static final String TASK_DAY = "day";
    public static final String TASK_PRIORITY = "priority";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 7);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("onCreate", "table created");
        db.execSQL("CREATE TABLE TodoDB (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL, " +
                "note TEXT NOT NULL, " +
                "year TEXT NOT NULL, " +
                "month TEXT NOT NULL, " +
                "day TEXT NOT NULL, " +
                "priority TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TodoDB");
        onCreate(db);
    }

    public boolean insert(String title, String note, String year, String month, String day, Integer priority) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK_TITLE, title);
        values.put(TASK_NOTE, note );
        values.put(TASK_YEAR, year );
        values.put(TASK_MONTH, month );
        values.put(TASK_DAY, day );
        values.put(TASK_PRIORITY, priority );

        long prevRows = db.rawQuery("SELECT * FROM TodoDB", null).getCount();
        long curRows = db.insert("TodoDB", null, values);

        if(curRows - prevRows == 1) return true;
        else return false;
    }

    public void update(String id, String title, String note, String year, String month, String day, Integer priority) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(TASK_TITLE, title);
        content.put(TASK_NOTE, note );
        content.put(TASK_YEAR, year );
        content.put(TASK_MONTH, month );
        content.put(TASK_DAY, day );
        content.put(TASK_PRIORITY, priority );

        db.update("TodoDB", content, "_id = ?", new String[]{id});
    }

    public void delete(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("TodoDB", "_id = ?", new String[]{id});
    }

    public Cursor getAll() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM TodoDB", null);
    }

    public Cursor getData(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM TodoDB WHERE _id=?", new String[]{id});
        return result;
    }

    public void close() {
        db.close();
    }


}
