package com.zach.znavigator.ZNavigation;

import android.support.annotation.IdRes;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.zach.znavigator.common.FragmentsUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Zach on 11/19/2017.
 */

public abstract class NavigationActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemReselectedListener {

    private int fragmentContainer;
    private boolean homeReselectEnabled = true;
    private LinkedHashMap<Integer, Fragment> rootFragments = new LinkedHashMap<>();
    private List<Fragment> fragmentsStack = new ArrayList<>();

    public void init(LinkedHashMap<Integer, Fragment> fragments, @IdRes int fragmentContainer) {
        this.rootFragments = fragments;
        this.fragmentContainer = fragmentContainer;
        loadFirstTab();
    }

    public void setHomeReselectEnabled(boolean enable) {
        homeReselectEnabled = enable;
    }

    public abstract void tabChanged(@MenuRes int id);

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (!rootFragments.containsKey(item.getItemId())) {
            return false;
        }
        tabChanged(item.getItemId());
        Fragment selectedFragment = rootFragments.get(item.getItemId());
        String selectedTag = selectedFragment.getClass().getName();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(selectedTag);
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(FragmentsUtils.getShowingFragment(getSupportFragmentManager()))
                    .show(fragment)
                    .addToBackStack(selectedTag)
                    .commit();
            modifyStack(fragment);
        } else {
            FragmentWrapper fragmentWrapper = FragmentWrapper_.builder().build();
            fragmentWrapper.loadInnerFragment(selectedFragment);
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(getSupportFragmentManager().findFragmentById(fragmentContainer))
                    .add(fragmentContainer, fragmentWrapper, selectedTag)
                    .addToBackStack(selectedTag)
                    .commit();
            fragmentsStack.add(0, fragmentWrapper);
        }
        return true;
    }

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {
        if (!homeReselectEnabled) {
            return;
        }

        Fragment showingFragment = FragmentsUtils.getShowingFragment(getSupportFragmentManager());
        Fragment firstTabFragment = rootFragments.values().iterator().next();
        if (showingFragment.getTag().equals(firstTabFragment.getClass().getName())) {
            FragmentManager fragmentManager = showingFragment.getChildFragmentManager();
            if (fragmentManager.getBackStackEntryCount() > 1) {
                for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
                    fragmentManager.popBackStackImmediate();
                }
                fragmentManager
                        .beginTransaction()
                        .show(showingFragment)
                        .commit();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if ((fragmentsStack == null) || (fragmentsStack.size() < 1)) {
            finish();
            return;
        }
        FragmentWrapper currentFragment = (FragmentWrapper) fragmentsStack.get(0);
        if (currentFragment.popIfHasChild()) {
            return;
        }
        if ((fragmentsStack.size() == 1)) {
            String fragTag = rootFragments.values().iterator().next().getClass().getName();
            if (fragmentsStack.get(0).getTag().equals(fragTag)) {
                finish();
                return;
            }
            FragmentWrapper fragmentWrapper = (FragmentWrapper) getSupportFragmentManager().findFragmentByTag(fragTag);
            if (fragmentWrapper == null) {
                finish();
                return;
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(currentFragment)
                    .show(fragmentWrapper)
                    .addToBackStack(fragTag)
                    .commit();
            fragmentsStack.remove(0);
            fragmentsStack.add(0, fragmentWrapper);
            tabChanged(getItemIdByFragment(currentFragment));
            return;
        }
        fragmentsStack.remove(0);
        Fragment newFragment = fragmentsStack.get(0);
        getSupportFragmentManager()
                .beginTransaction()
                .hide(currentFragment)
                .show(newFragment)
                .addToBackStack(newFragment.getClass().getName())
                .commit();
        tabChanged(getItemIdByFragment((FragmentWrapper) newFragment));
    }

    private void loadFirstTab() {
        Fragment firstFragment = rootFragments.values().iterator().next();
        FragmentWrapper fragmentWrapper = FragmentWrapper_.builder().build();
        fragmentWrapper.loadInnerFragment(firstFragment);
        getSupportFragmentManager()
                .beginTransaction()
                .add(fragmentContainer, fragmentWrapper, firstFragment.getClass().getName())
                .addToBackStack(firstFragment.getClass().getName())
                .commit();
        fragmentsStack.add(0, fragmentWrapper);
    }

    private synchronized void modifyStack(Fragment newFragment) {
        for (int i = 0; i < fragmentsStack.size(); i++) {
            if (fragmentsStack.get(i) == newFragment) {
                fragmentsStack.remove(i);
            }
        }
        fragmentsStack.add(0, newFragment);
    }

    private int getItemIdByFragment(FragmentWrapper currentFragment) {
        Iterator it = rootFragments.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getValue().getClass().getName().equals(currentFragment.getTag())) {
                return (int) pair.getKey();
            }
        }
        return 0;
    }
}
