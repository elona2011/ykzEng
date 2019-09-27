package com.example.ykzeng.task.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.ykzeng.R;
import com.example.ykzeng.db.DBHelper;

import java.util.HashMap;

public class TaskDetail extends AppCompatActivity {
    private String mid;
    private String TAG = "TaskDetail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        DBHelper db = new DBHelper(this);
        HashMap<String, String> task = db.getTask(id);
        TextView task_name = findViewById(R.id.task_name);
        task_name.setText(task.get(DBHelper.KEY_NAME));
        TextView task_time = findViewById(R.id.task_time);
        task_time.setText(task.get(DBHelper.KEY_CREATETIME));
        TextView task_money = findViewById(R.id.task_money);
        task_money.setText(task.get(DBHelper.KEY_MONEY));
        mid = task.get(DBHelper.KEY_ID);
        Log.d(TAG, "onCreate: "+id);
    }

    public void del(View view) {
        DBHelper db = new DBHelper(this);
        db.delTask(mid);
        Intent intent = new Intent(this,ListActivity.class);
        startActivity(intent);
    }
}
