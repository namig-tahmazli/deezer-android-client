package com.namig.tahmazli.deezerandroidclient.navigation;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.namig.tahmazli.deezerandroidclient.R;

import javax.inject.Inject;

public class NavigationManager {

    private final FragmentManager mFragmentManager;

    private static final String TAG = NavigationManager.class.getSimpleName();

    @Inject
    public NavigationManager(final FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }

    public void navigateTo(final Fragment fragment,
                           @Nullable final Bundle args) {


        final Class<? extends Fragment> type = fragment.getClass();
        final String tag = type.getName();

        Log.d(TAG, tag);

        final FragmentTransaction transaction = mFragmentManager.beginTransaction();

        @Nullable final Fragment existingFragment = mFragmentManager.findFragmentByTag(tag);
        if (existingFragment != null && !existingFragment.isRemoving()) {
            if (existingFragment.isDetached())
                transaction
                        .attach(existingFragment)
                        .setPrimaryNavigationFragment(existingFragment);
        } else {
            if (args != null)
                fragment.setArguments(args);

            transaction
                    .replace(R.id.fragmentContainer, fragment, tag)
                    .addToBackStack(tag)
                    .setPrimaryNavigationFragment(fragment);
        }

        transaction.setReorderingAllowed(true).commit();
    }
}
