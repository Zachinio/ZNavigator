package com.zach.znavigator.ZNavigation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.zach.znavigator.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.fragment_wrapper)
public class FragmentWrapper extends Fragment {

    Fragment childFragment;
    private boolean isInflated = false;

    @AfterViews
    protected void afterViews() {
        if (isInflated) {
            return;
        }
        if (childFragment == null) {
            return;
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
