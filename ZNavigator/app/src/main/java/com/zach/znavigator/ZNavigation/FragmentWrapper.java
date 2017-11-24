package com.zach.znavigator.ZNavigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zach.znavigator.R;

public class FragmentWrapper extends Fragment {

    Fragment childFragment;
    private boolean isInflated = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_wrapper, null);
        if (isInflated) {
            return rootView;
        }
        if (childFragment == null) {
            return rootView;
        }
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        Fragment fragmentById = getChildFragmentManager().findFragmentById(R.id.inner_container);
        if (fragmentById != null) {
            fragmentTransaction.hide(fragmentById);
        }
        fragmentTransaction
                .add(R.id.inner_container, childFragment, childFragment.getClass().getName())
                .addToBackStack(childFragment.getClass().getName())
                .commit();
        isInflated = true;
        return rootView;
    }

    public void loadInnerFragment(Fragment fragment) {
        childFragment = fragment;
    }

    public boolean popIfHasChild() {
        if (getChildFragmentManager().getBackStackEntryCount() > 1) {
            getChildFragmentManager().popBackStackImmediate();
            getChildFragmentManager()
                    .beginTransaction()
                    .show(getChildFragmentManager().findFragmentById(R.id.inner_container))
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onPause() {
        super.onPause();
        fragmentWillDisappear();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        fragmentWillDisappear();
    }

    private void fragmentWillDisappear() {

    }

}

