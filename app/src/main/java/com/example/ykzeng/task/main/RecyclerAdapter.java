package com.example.ykzeng.task.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ykzeng.R;
import com.example.ykzeng.db.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private ArrayList<HashMap<String, String>> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mtv_task_date;
        public TextView tv_id;
        public TextView mtv_task_money;

        public MyViewHolder(View v) {
            super(v);
            mtv_task_date = v.findViewById(R.id.task_date);
            mtv_task_money = v.findViewById(R.id.task_money);
        }
    }

    public RecyclerAdapter(ArrayList<HashMap<String, String>> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_main_item_frag, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mtv_task_date.setText(mDataset.get(position).get(DBHelper.KEY_CREATETIME));
//        holder.tv_id.setText(mDataset.get(position).get(DBHelper.KEY_ID));
        holder.mtv_task_money.setText(mDataset.get(position).get(DBHelper.KEY_MONEY));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
