package com.zach.znavigator.common;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * Created by Zach on 11/21/2017.
 */

public class FragmentsUtils {

    @Nullable
    public static Fragment getShowingFragment(FragmentManager fragmentManager) {
        List<Fragment> fragments = fragmentManager.getFragments();
        for (int i = 0; i < fragments.size(); i++) {
            if (!fragments.get(i).isHidden()) {
                return fragments.get(i);
            }
        }
        return null;
    }

}
