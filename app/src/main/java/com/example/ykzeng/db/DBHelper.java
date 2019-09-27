package com.example.ykzeng.db;

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
    public static final int DATABASE_VERSION = 11;
    public static final String DATABASE_NAME = "Words.db";
    public static final String TABLE_TASKS = "tasks";
    public static final String TABLE_SENTENCE = "sentences";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_CREATETIME = "createtime";
    public static final String KEY_ISDEL = "isDel";
    public static final String KEY_USEDNUM = "usedNum";
    public static final String KEY_MONEY = "money";

    private String SQL_CREATE_TASKS =
            "CREATE TABLE " + TABLE_TASKS + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    KEY_NAME + " TEXT," +
                    KEY_CREATETIME + " TEXT," +
                    KEY_MONEY + " INTEGER," +
                    KEY_ISDEL + " INTEGER DEFAULT 0" +
                    ")";

    private String SQL_CREATE_SENTENCE = "CREATE TABLE " + TABLE_SENTENCE + " (" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_NAME + " TEXT," +
            KEY_CREATETIME + " TEXT," +
            KEY_USEDNUM + " INTEGER DEFAULT 0," +
            KEY_ISDEL + " INTEGER DEFAULT 0" +
            ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TASKS);
        db.execSQL(SQL_CREATE_SENTENCE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SENTENCE);
        onCreate(db);
    }

    public void insert(String name, String money) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NAME, name);
        cValues.put(KEY_MONEY, money);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        cValues.put(KEY_CREATETIME, currentDateandTime);

        db.insert(TABLE_TASKS, null, cValues);
        db.close();
    }

    public void insertSentence(String sentence) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NAME, sentence);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        cValues.put(KEY_CREATETIME, currentDateandTime);

        db.insert(TABLE_SENTENCE, null, cValues);
        db.close();
    }

    public void del(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_ISDEL, 1);
        db.update(TABLE_TASKS, cValues, KEY_ID + "=" + id, null);
        db.close();
    }

    public HashMap<String, String> getDetail(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        HashMap<String, String> word = new HashMap<>();
        Cursor cursor = db.query(TABLE_TASKS, new String[]{KEY_ID, KEY_NAME, KEY_CREATETIME}, KEY_ID + "=" + id, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            word.put(KEY_NAME, cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            word.put(KEY_CREATETIME, cursor.getString(cursor.getColumnIndex(KEY_CREATETIME)));
        }
        db.close();
        return word;
    }

    public HashMap<String, String> getTask(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        HashMap<String, String> task = new HashMap<>();
        Cursor cursor;
        cursor = db.query(TABLE_TASKS, new String[]{KEY_ID, KEY_NAME, KEY_CREATETIME, KEY_MONEY}, KEY_ID + "=" + id, null, null, null, null);

        while (cursor.moveToNext()) {
            task.put(KEY_NAME, cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            task.put(KEY_ID, cursor.getString(cursor.getColumnIndex(KEY_ID)));
            task.put(KEY_CREATETIME, cursor.getString(cursor.getColumnIndex(KEY_CREATETIME)));
            task.put(KEY_MONEY, cursor.getString(cursor.getColumnIndex(KEY_MONEY)));
        }
        db.close();
        return task;
    }

    public ArrayList<HashMap<String, String>> getTasks(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HashMap<String, String>> words = new ArrayList<>();
        Cursor cursor;
        if (date.equals("total")) {
            cursor = db.query(TABLE_TASKS, new String[]{KEY_ID, KEY_NAME, KEY_CREATETIME, KEY_MONEY}, KEY_ISDEL + "=0", null, null, null, null);
        } else {
            cursor = db.query(TABLE_TASKS, new String[]{KEY_ID, KEY_NAME, KEY_CREATETIME, KEY_MONEY}, KEY_CREATETIME + " like '%" + date + "%' and " + KEY_ISDEL + "=0", null, null, null, null);
        }
        while (cursor.moveToNext()) {
            HashMap<String, String> word = new HashMap<>();
            word.put(KEY_NAME, cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            word.put(KEY_ID, cursor.getString(cursor.getColumnIndex(KEY_ID)));
            word.put(KEY_CREATETIME, cursor.getString(cursor.getColumnIndex(KEY_CREATETIME)));
            word.put(KEY_MONEY, cursor.getString(cursor.getColumnIndex(KEY_MONEY)));
            words.add(word);
        }
        db.close();
        return words;
    }

    public void delTask(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_ISDEL, 1);
        db.update(TABLE_TASKS, cValues, KEY_ID + "=" + id, null);
        db.close();
    }

    public ArrayList<HashMap<String, String>> getWords() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HashMap<String, String>> words = new ArrayList<>();
        Cursor cursor = db.query(TABLE_TASKS, new String[]{KEY_ID, KEY_NAME, KEY_CREATETIME, KEY_MONEY}, KEY_ISDEL + "=0", null, null, null, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> word = new HashMap<>();
            word.put(KEY_NAME, cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            word.put(KEY_ID, cursor.getString(cursor.getColumnIndex(KEY_ID)));
            word.put(KEY_CREATETIME, cursor.getString(cursor.getColumnIndex(KEY_CREATETIME)));
            word.put(KEY_MONEY, cursor.getString(cursor.getColumnIndex(KEY_MONEY)));
            words.add(word);
        }
        db.close();
        return words;
    }
}
