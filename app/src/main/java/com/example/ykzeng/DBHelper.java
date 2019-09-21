package com.example.ykzeng;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 9;
    public static final String DATABASE_NAME = "Words.db";
    public static final String TABLE_Users = "words";
    private static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_CREATETIME = "createtime";
    public static final String KEY_ISDEL = "isDel";
    public static final String KEY_REMEMBER = "remember";
    public static final String KEY_FORGET = "forget";
    public static final String KEY_EXAMPLE = "example";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_Users + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    KEY_NAME + " TEXT," +
                    KEY_CREATETIME + " TEXT," +
                    KEY_EXAMPLE + " TEXT," +
                    KEY_REMEMBER + " INTEGER DEFAULT 0," +
                    KEY_FORGET + " INTEGER DEFAULT 0," +
                    KEY_ISDEL + " INTEGER DEFAULT 0" +
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

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        cValues.put(KEY_CREATETIME, currentDateandTime);

        long newRowId = db.insert(TABLE_Users, null, cValues);
        db.close();
    }

    void remember(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_Users, new String[]{KEY_REMEMBER}, KEY_ID + "=" + id, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int rememberNum = cursor.getInt(cursor.getColumnIndex(KEY_REMEMBER));
            ContentValues cValues = new ContentValues();
            cValues.put(KEY_REMEMBER, ++rememberNum);
            db.update(TABLE_Users, cValues, KEY_ID + "=" + id, null);
        }
        db.close();
    }

    void forget(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_Users, new String[]{KEY_FORGET}, KEY_ID + "=" + id, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int rememberNum = cursor.getInt(cursor.getColumnIndex(KEY_FORGET));
            ContentValues cValues = new ContentValues();
            cValues.put(KEY_FORGET, ++rememberNum);
            db.update(TABLE_Users, cValues, KEY_ID + "=" + id, null);
        }
        db.close();
    }

    void del(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_ISDEL, 1);
        db.update(TABLE_Users, cValues, KEY_ID + "=" + id, null);
        db.close();
    }

    public HashMap<String, String> getDetail(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        HashMap<String, String> word = new HashMap<>();
        Cursor cursor = db.query(TABLE_Users, new String[]{KEY_ID, KEY_NAME, KEY_CREATETIME, KEY_REMEMBER, KEY_FORGET}, KEY_ID + "=" + id, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            word.put(KEY_NAME, cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            word.put(KEY_CREATETIME, cursor.getString(cursor.getColumnIndex(KEY_CREATETIME)));
            word.put(KEY_REMEMBER, cursor.getString(cursor.getColumnIndex(KEY_REMEMBER)));
            word.put(KEY_FORGET, cursor.getString(cursor.getColumnIndex(KEY_FORGET)));
        }
        db.close();
        return word;
    }

    public ArrayList<HashMap<String, String>> getWords() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HashMap<String, String>> words = new ArrayList<>();
        Cursor cursor = db.query(TABLE_Users, new String[]{KEY_ID, KEY_NAME, KEY_REMEMBER, KEY_FORGET}, KEY_ISDEL + "=0", null, null, null, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> word = new HashMap<>();
            word.put(KEY_NAME, cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            word.put(KEY_ID, cursor.getString(cursor.getColumnIndex(KEY_ID)));
            word.put(KEY_REMEMBER, cursor.getString(cursor.getColumnIndex(KEY_REMEMBER)));
            word.put(KEY_FORGET, cursor.getString(cursor.getColumnIndex(KEY_FORGET)));
            words.add(word);
        }
        db.close();
        return words;
    }
}
