package com.zach.znavigator.ZNavigation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.zach.znavigator.R;

/**
 * Created by Zach on 11/21/2017.
 */

public class ZNavigation {

    public static void openChildFragment(FragmentManager fragmentManager, Fragment childFragment) {
        fragmentManager
                .beginTransaction()
                .add(R.id.inner_container, childFragment, childFragment.getClass().getName())
                .addToBackStack(childFragment.getClass().getName())
                .commit();
    }

}
