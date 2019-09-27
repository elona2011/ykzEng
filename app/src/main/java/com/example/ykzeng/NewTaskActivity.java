package com.example.ykzeng;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.ykzeng.db.DBHelper;

public class NewTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
    }

    public void add(View view) {
        EditText name = findViewById(R.id.new_task_name);
        EditText money = findViewById(R.id.new_money);

        DBHelper db = new DBHelper(this);
        db.insert(name.getText().toString(), money.getText().toString());

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
