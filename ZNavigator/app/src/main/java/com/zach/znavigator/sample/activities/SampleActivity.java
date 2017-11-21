package com.zach.znavigator.sample.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.zach.znavigator.R;
import com.zach.znavigator.ZNavigation.NavigationActivity;
import com.zach.znavigator.sample.fragments.FirstTab_;
import com.zach.znavigator.sample.fragments.SecondTab_;
import com.zach.znavigator.sample.fragments.ThirdTab_;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.LinkedHashMap;

/**
 * Created by Zach on 11/19/2017.
 */

@EActivity(R.layout.activity_main)
public class SampleActivity extends NavigationActivity {

    @ViewById
    BottomNavigationView navigationView;

    @AfterViews
    protected void afterViews() {
        LinkedHashMap<Integer, Fragment> rootFragments = new LinkedHashMap<>();
        rootFragments.put(R.id.tab1, FirstTab_.builder().build());
        rootFragments.put(R.id.tab2, SecondTab_.builder().build());
        rootFragments.put(R.id.tab3, ThirdTab_.builder().build());
        init(rootFragments, R.id.container);

        navigationView.setOnNavigationItemSelectedListener(this);
        navigationView.setOnNavigationItemReselectedListener(this);
    }

    @Override
    public void tabChanged(int id) {
        navigationView.getMenu().findItem(id).setChecked(true);
    }
}
