package com.qci.fish.adapter;

import android.content.Context;;import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.qci.fish.R;
import com.qci.fish.fragment.CollectionStage_first;
import com.qci.fish.fragment.CollectionStage_second;
import com.qci.fish.fragment.CollectionStage_third;

public class TabPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public TabPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        if(i==0){
            return new CollectionStage_first();
        }
        else if(i==1){
            return new CollectionStage_second();
        }else if (i==2){
            return new CollectionStage_third();
        }
        else
            return new CollectionStage_third();
    }

    @Override
    public int getCount() {
        //Total number of tabs
        return 4;
    }
    

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
            return context.getString(R.string.tab_one);
        }
        else if(position == 1){
            return context.getString(R.string.tab_two);
        }else if (position == 2){
            return context.getString(R.string.tab_three);
        }
        else
            return context.getString(R.string.tab_four);
    }
}
