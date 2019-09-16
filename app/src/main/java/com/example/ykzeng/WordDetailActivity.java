package com.example.ykzeng;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.HashMap;

public class WordDetailActivity extends AppCompatActivity {
    private static final String TAG = WordDetailActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detail);
        Intent intent = getIntent();
        int index = intent.getIntExtra(getString(R.string.recycler_item_pos),-1);
        Log.d(TAG, "onCreate: "+index);

        DBHelper db = new DBHelper(this);
        HashMap<String,String> hm=db.getDetail(index);

        TextView textView = findViewById(R.id.textView2);
        textView.setText(hm.get(DBHelper.KEY_NAME));
    }
}
