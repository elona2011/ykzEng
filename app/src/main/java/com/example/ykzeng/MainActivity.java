package com.example.ykzeng;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.ykzeng.recyclerView.RecyclerItemClickListener;
import com.example.ykzeng.viewPage.FragmentTask;
import com.example.ykzeng.viewPage.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements FragmentTask.OnFragmentInteractionListener {
    private static final String TAG = RecyclerItemClickListener.class.getName();
    private ViewPager vp;
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
                    case R.id.nav_eng:
                        vp.setCurrentItem(0);
                        break;
                    case R.id.nav_task:
                        vp.setCurrentItem(1);
                        break;
                    case R.id.nav_3:
                        vp.setCurrentItem(2);
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

    public void addNewWord(View view) {
        Intent intent = new Intent(this, NewWordActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ViewPagerAdapter ad = new ViewPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(ad);
        ad.notifyDataSetChanged();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
