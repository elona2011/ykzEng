package com.example.ykzeng.viewPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ykzeng.DBHelper;
import com.example.ykzeng.R;
import com.example.ykzeng.WordDetailActivity;
import com.example.ykzeng.recyclerView.MyAdapter;
import com.example.ykzeng.recyclerView.RecyclerItemClickListener;
import com.example.ykzeng.recyclerView.VerticalSpaceItemDecoration;

public class FragmentEng extends Fragment {
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_eng,container,false);
        recyclerView = view.findViewById(R.id.my_recycler_view);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.Handler() {
            @Override
            public void onItemClick(MotionEvent e, int i, View view) {
                if (i >= 0) {
                    String t=((TextView)view.findViewById(R.id.re_id)).getText().toString();
                    detail(t);
                }
            }
        }));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(12));

        DBHelper db = new DBHelper(getActivity());
        recyclerView.setAdapter(new MyAdapter(db.getWords()));
        return view;
    }

    public void detail(String s) {
        Intent intent = new Intent(getActivity(), WordDetailActivity.class);
        intent.putExtra(getString(R.string.recycler_item_pos), s);
        startActivity(intent);
    }
}
