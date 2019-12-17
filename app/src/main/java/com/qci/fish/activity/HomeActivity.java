package com.qci.fish.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.qci.fish.R;
import com.qci.fish.adapter.TabPagerAdapter;
import com.qci.fish.fragment.CollectionStage_first;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    private TabLayout tabLayout;

    @BindView(R.id.frame_layout)
    FrameLayout frame_layout;

    private int localSampleId ;

    private String click_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        localSampleId = getIntent().getIntExtra("local_sample_id",00);

        click_type = getIntent().getStringExtra("click_type");

        Fragment fragment = new CollectionStage_first();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frame_layout,fragment);
        ft.commit();


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                  switch (tab.getPosition()){
                      case 0:

                          break;

                      case 1:
                          break;


                      case 2:

                          break;

                      case 3:

                          break;


                  }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public int getMyData() {
        return localSampleId;
    }

    public String getClick_event(){
        return click_type;
    }

    @Override
    public void onBackPressed() {
        /*if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {

        }*/

        finish();
    }
}
