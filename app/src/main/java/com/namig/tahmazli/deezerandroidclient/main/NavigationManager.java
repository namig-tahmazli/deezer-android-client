package com.namig.tahmazli.deezerandroidclient.main;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.namig.tahmazli.deezerandroidclient.R;
import com.namig.tahmazli.deezerandroidclient.di.AppScope;

import java.util.Objects;

import javax.inject.Inject;

@AppScope
class NavigationManager {

    @Nullable
    private FragmentManager mFragmentManager;

    private static final String TAG = NavigationManager.class.getSimpleName();

    @Inject
    NavigationManager() {
    }

    void attachFragmentManager(final FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    void detachFragmentManager() {
        mFragmentManager = null;
    }

    void navigateTo(final Fragment fragment,
                    @Nullable final Bundle args) {
        Objects.requireNonNull(mFragmentManager);

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

    public void navigateBack() {
        if (mFragmentManager != null)
            mFragmentManager.popBackStackImmediate();
    }
}
