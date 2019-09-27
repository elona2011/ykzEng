package com.example.ykzeng;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.ykzeng.db.DBHelper;

public class NewSentenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sentence);
    }

    public void add(View view) {
        EditText t = findViewById(R.id.new_task_name);

        DBHelper db = new DBHelper(this);
        db.insertSentence(t.getText().toString());

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
