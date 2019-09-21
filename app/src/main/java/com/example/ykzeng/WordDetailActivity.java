package com.example.ykzeng;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

public class WordDetailActivity extends AppCompatActivity {
    private static final String TAG = WordDetailActivity.class.getName();
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detail);
        Intent intent = getIntent();
        id = intent.getStringExtra(getString(R.string.recycler_item_pos));
        Log.d(TAG, "onCreate: " + id);

        DBHelper db = new DBHelper(this);
        HashMap<String, String> hm = db.getDetail(id);

        TextView textView = findViewById(R.id.tv_wordname);
        textView.setText(hm.get(DBHelper.KEY_NAME));
        TextView tv_createTime = findViewById(R.id.tv_createTime);
        tv_createTime.setText(hm.get(DBHelper.KEY_CREATETIME));
        TextView tv_remember = findViewById(R.id.tv_remember);
        tv_remember.setText(hm.get(DBHelper.KEY_REMEMBER) + "/" + hm.get(DBHelper.KEY_FORGET));
    }

    public void remember(View view) {
        DBHelper db = new DBHelper(this);
        db.remember(id);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void forget(View view) {
        DBHelper db = new DBHelper(this);
        db.forget(id);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void del() {
        DBHelper db = new DBHelper(this);
        db.del(id);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_word_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.detail_del) {
            del();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
