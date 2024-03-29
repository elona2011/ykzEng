package com.example.ykzeng.recyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ykzeng.R;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<HashMap<String,String>> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public TextView tv_id;
        public TextView tv_remember;
        public MyViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.task_date);
            tv_id = v.findViewById(R.id.task_id);
            tv_remember=v.findViewById(R.id.task_money);
        }
    }

    public MyAdapter(ArrayList<HashMap<String,String>> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(mDataset.get(position).get("name"));
        holder.tv_id.setText(mDataset.get(position).get("id"));
        holder.tv_remember.setText(mDataset.get(position).get("remember")+"/"+mDataset.get(position).get("forget"));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
