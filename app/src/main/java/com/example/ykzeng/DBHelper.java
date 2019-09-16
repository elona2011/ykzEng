package com.example.ykzeng;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Words.db";
    public static final String TABLE_Users = "words";
    private static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_Users + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    KEY_NAME + " TEXT" +
                    ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        onCreate(db);
    }

    void insert(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NAME, name);
        long newRowId = db.insert(TABLE_Users, null, cValues);
        db.close();
    }

    public HashMap<String, String> getDetail(int i) {
        SQLiteDatabase db = this.getReadableDatabase();
        HashMap<String, String> word = new HashMap<>();
        Cursor cursor = db.query(TABLE_Users, new String[]{KEY_ID,KEY_NAME}, KEY_ID+"=" + i, null, null, null, null);
        cursor.moveToNext();
        word.put(KEY_NAME, cursor.getString(cursor.getColumnIndex(KEY_NAME)));
        return word;
    }

    public ArrayList<HashMap<String, String>> getWords() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HashMap<String, String>> words = new ArrayList<>();
        String query = "SELECT " + KEY_NAME + " FROM " + TABLE_Users;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> word = new HashMap<>();
            word.put(KEY_NAME, cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            words.add(word);
        }
        db.close();
        return words;
    }
}
