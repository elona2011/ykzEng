package com.example.ykzeng.recyclerView;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {
    private Handler mhandler;
    private GestureDetector mGestureDetector;
    private static final String TAG = RecyclerItemClickListener.class.getName();

    public interface Handler{
        public void onItemClick(MotionEvent e,int i);
    }

    public RecyclerItemClickListener(Context context, Handler handler) {
        mhandler=handler;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if(mGestureDetector.onTouchEvent(e)){
            View childView = rv.findChildViewUnder(e.getX(), e.getY());
            int i =rv.getChildAdapterPosition(childView);
            Log.d(TAG, "onInterceptTouchEvent: "+i);
            mhandler.onItemClick(e,i);
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e){
    }
}
