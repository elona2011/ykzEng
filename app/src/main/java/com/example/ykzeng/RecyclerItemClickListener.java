package com.example.ykzeng;

import android.util.Log;
import android.view.MotionEvent;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {
    public RecyclerItemClickListener() {

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.d("debugger", "onTouchEvent: aaabcd2");
        return true;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e){
        Log.d("debugger", "onTouchEvent: aaabcd");
    }
}
