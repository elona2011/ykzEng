package com.example.ykzeng.task.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.ykzeng.R;
import com.example.ykzeng.db.DBHelper;
import com.example.ykzeng.recyclerView.RecyclerItemClickListener;
import com.example.ykzeng.recyclerView.VerticalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;

public class ListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    String mdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent intent = getIntent();
        mdate = intent.getStringExtra("date");
        setTitle(mdate);
        recyclerView = findViewById(R.id.task_detail_recycler);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.Handler() {
            @Override
            public void onItemClick(MotionEvent e, int i, View view) {
                if (i >= 0) {
                    String id = view.getTag().toString();
                    taskDetail(id);
                }
            }
        }));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(12));

        DBHelper db = new DBHelper(this);
        ArrayList<HashMap<String, String>> allTasks = db.getTasks(mdate);

        recyclerView.setAdapter(new RecyclerAdapter(allTasks));
    }

    void taskDetail(String id) {
        Intent intent = new Intent(this, TaskDetail.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        DBHelper db = new DBHelper(this);
        ArrayList<HashMap<String, String>> allTasks = db.getTasks(mdate);

        RecyclerAdapter a = new RecyclerAdapter(allTasks);
        recyclerView.setAdapter(a);
        a.notifyDataSetChanged();
    }
}
