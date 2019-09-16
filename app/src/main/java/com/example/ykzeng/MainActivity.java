package com.example.ykzeng;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.ykzeng.recyclerView.MyAdapter;
import com.example.ykzeng.recyclerView.RecyclerItemClickListener;
import com.example.ykzeng.recyclerView.VerticalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final int VERTICAL_ITEM_SPACE = 12;
    private static final String TAG = RecyclerItemClickListener.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper db = new DBHelper(this);
        ArrayList<HashMap<String, String>> wordList = db.getWords();

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.Handler() {
            @Override
            public void onItemClick(MotionEvent e, int i) {
                if(i>=0){
                    detail(i);
                }
            }
        }));
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        mAdapter = new MyAdapter(wordList);
        recyclerView.setAdapter(mAdapter);
    }

    public void detail(int i) {
        Intent intent = new Intent(this, WordDetailActivity.class);
        intent.putExtra(getString(R.string.recycler_item_pos),i);
        startActivity(intent);
    }

    public void addNewWord(View view) {
        Intent intent = new Intent(this, NewWordActivity.class);
        startActivity(intent);
    }
}
