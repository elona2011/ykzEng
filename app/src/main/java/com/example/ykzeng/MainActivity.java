package com.example.ykzeng;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final int VERTICAL_ITEM_SPACE = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper db=new DBHelper(this);
        ArrayList<HashMap<String,String>> wordList = db.getWords();

        recyclerView=(RecyclerView)findViewById(R.id.my_recycler_view);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener());
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        mAdapter=new MyAdapter(wordList);
        recyclerView.setAdapter(mAdapter);
    }

    public void addNewWord(View view){
        Intent intent=new Intent(this,NewWordActivity.class);
        startActivity(intent);
    }
}
