package com.zach.znavigator.sample.fragments;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.zach.znavigator.R;
import com.zach.znavigator.ZNavigation.ZNavigation;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Zach on 11/19/2017.
 */

@EFragment(R.layout.fragment_second)
public class SecondTab extends Fragment {

    @ViewById
    TextView textView;

    @Click(R.id.textView)
    protected void onClick() {
        ZNavigation.openChildFragment(getFragmentManager(), ChildFragment_.builder().build());
    }
}
