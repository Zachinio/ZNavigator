package com.zach.sample.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;

import com.zach.sample.R;
import com.zach.sample.fragments.FirstTab;
import com.zach.sample.fragments.SecondTab;
import com.zach.sample.fragments.ThirdTab;
import com.zach.znavigator.ZNavigation.NavigationActivity;


import java.util.LinkedHashMap;

/**
 * Created by Zach on 11/19/2017.
 */


public class SampleActivity extends NavigationActivity {

    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = (BottomNavigationView) findViewById(R.id.navigationView);

        LinkedHashMap<Integer, Fragment> rootFragments = new LinkedHashMap<>();
        rootFragments.put(R.id.tab1, new FirstTab());
        rootFragments.put(R.id.tab2,new SecondTab());
        rootFragments.put(R.id.tab3, new ThirdTab());
        init(rootFragments, R.id.container);

        navigationView.setOnNavigationItemSelectedListener(this);
        navigationView.setOnNavigationItemReselectedListener(this);
    }

    @Override
    public void tabChanged(int id) {
        navigationView.getMenu().findItem(id).setChecked(true);
    }
}
