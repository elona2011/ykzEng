package com.example.ykzeng;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ykzeng.recyclerView.RecyclerItemClickListener;
import com.example.ykzeng.task.main.FragTask;
import com.example.ykzeng.viewPage.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements
        FragTask.OnFragmentInteractionListener {
    private static final String TAG = RecyclerItemClickListener.class.getName();
    private ViewPager vp;
    private int vp_index;
    BottomNavigationView bv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragListener();
    }

    private void initFragListener() {
        bv = findViewById(R.id.navigation);
        vp = findViewById(R.id.vp);
        bv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.nav_task:
                        vp_index = 0;
                        vp.setCurrentItem(vp_index);
                        break;
                    case R.id.nav_eng:
                        vp_index = 1;
                        vp.setCurrentItem(vp_index);
                        break;
                    case R.id.nav_3:
                        vp_index = 2;
                        vp.setCurrentItem(vp_index);
                        break;
                }
                return false;
            }
        });
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bv.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
    }

    public void addNewTask() {
        Intent intent = new Intent(this, NewTaskActivity.class);
        startActivity(intent);
    }

    public void addNewSentence() {
        Intent intent = new Intent(this, NewSentenceActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

//        ViewPagerAdapter ad = new ViewPagerAdapter(getSupportFragmentManager());
//        vp.setAdapter(ad);
        vp.setCurrentItem(vp_index);
//        ad.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.main_option_add) {
            int pos = vp.getCurrentItem();
            switch (pos) {
                case 0:
                    addNewTask();
                    break;
                case 1:
                    addNewSentence();
                    break;
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(String s) {
    }
}
