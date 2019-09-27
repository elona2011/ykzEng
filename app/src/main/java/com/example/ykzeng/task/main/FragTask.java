package com.example.ykzeng.task.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ykzeng.R;
import com.example.ykzeng.db.DBHelper;
import com.example.ykzeng.recyclerView.RecyclerItemClickListener;
import com.example.ykzeng.recyclerView.VerticalSpaceItemDecoration;
import com.example.ykzeng.task.detail.ListActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragTask.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragTask#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragTask extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private OnFragmentInteractionListener mListener;

    public FragTask() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragTask.
     */
    // TODO: Rename and change types and number of parameters
    public static FragTask newInstance(String param1, String param2) {
        FragTask fragment = new FragTask();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.task_main_frag, container, false);
        recyclerView = view.findViewById(R.id.task_list);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.Handler() {
            @Override
            public void onItemClick(MotionEvent e, int i, View view) {
                if (i >= 0) {
                    String t = ((TextView) view.findViewById(R.id.task_date)).getText().toString();
                    onDetail(t);
                }
            }
        }));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(12));

        recyclerAdapter = new RecyclerAdapter(getDateList());
        recyclerView.setAdapter(recyclerAdapter);
        return view;
    }

    public ArrayList<HashMap<String, String>> getDateList(){
        double total = 0.0;
        DBHelper db = new DBHelper(getActivity());
        ArrayList<HashMap<String, String>> allTasks = db.getTasks("total");
        final HashMap<String, Double> dateMoney = new HashMap<>();

        for (HashMap<String, String> n : allTasks) {
            String _date = n.get(DBHelper.KEY_CREATETIME).substring(0, 10);
            final Double v = Double.parseDouble(n.get(DBHelper.KEY_MONEY));
            if (dateMoney.containsKey(_date)) {
                dateMoney.put(_date, dateMoney.get(_date) + v);
            } else {
                dateMoney.put(_date, v);
            }
            total += v;
        }

        ArrayList<HashMap<String, String>> dateList = new ArrayList<>();
        HashMap<String, String> hm_total = new HashMap<>();
        hm_total.put(DBHelper.KEY_CREATETIME, "total");
        hm_total.put(DBHelper.KEY_MONEY, Double.toString(total));
        dateList.add(hm_total);
        for (final String k : dateMoney.keySet()) {
            dateList.add(new HashMap<String, String>() {{
                put(DBHelper.KEY_CREATETIME, k);
                put(DBHelper.KEY_MONEY, dateMoney.get(k).toString());
            }});
        }
        return dateList;
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerAdapter = new RecyclerAdapter(getDateList());
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onDetail(String date) {
        if (mListener != null) {
//            mListener.onFragmentInteraction(date);
            Intent intent = new Intent(getActivity(), ListActivity.class);
            intent.putExtra("date", date);
            startActivity(intent);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String name);
    }
}
