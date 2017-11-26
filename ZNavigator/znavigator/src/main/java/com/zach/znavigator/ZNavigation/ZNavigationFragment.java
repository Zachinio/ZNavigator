package com.zach.znavigator.ZNavigation;

import android.support.v4.app.Fragment;

/**
 * Created by Zach on 11/26/2017.
 */

public abstract class ZNavigationFragment extends Fragment {

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            onFragmentHidden();
        } else {
            onFragmentShowen();
        }
    }

    public abstract void onFragmentHidden();

    public abstract void onFragmentShowen();

}
