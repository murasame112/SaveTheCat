package com.example.savethecat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private  Context context;
    private static final String DATABASE_NAME = "Stats.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "runs";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_POINTS= "_points";
    private static final String COLUMN_MODE = "_mode";
    private static final String COLUMN_DIED_BY = "_died_by";
    private static final String COLUMN_TIME_ALIVE = "_time_alive";


    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_POINTS + " INTEGER, " +
                        COLUMN_MODE + " TEXT, " +
                        COLUMN_DIED_BY + " TEXT, " +
                        COLUMN_TIME_ALIVE + " INTEGER);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public void addRun(String mode, int points, int time_alive, String died_by){
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_MODE, mode);
        cv.put(COLUMN_POINTS, points);
        cv.put(COLUMN_TIME_ALIVE, time_alive);
        cv.put(COLUMN_DIED_BY, died_by);
        long result = db.insert(TABLE_NAME, null, cv);
        // TEST
        if(result == -1){
            Toast.makeText(context, "Niepowodzenie", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Zapisano poprawnie", Toast.LENGTH_SHORT).show();
        }

    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }
}
