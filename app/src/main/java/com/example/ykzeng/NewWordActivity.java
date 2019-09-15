package com.example.ykzeng;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NewWordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
    }

    public void add(View view){
        EditText t=(EditText)findViewById(R.id.new_word_name) ;

        DBHelper db=new DBHelper(this);
        db.insert(t.getText().toString());

        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
