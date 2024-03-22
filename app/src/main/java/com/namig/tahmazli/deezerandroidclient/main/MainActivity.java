package com.namig.tahmazli.deezerandroidclient.main;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.Fragment;

import com.namig.tahmazli.deezerandroidclient.R;
import com.namig.tahmazli.deezerandroidclient.di.android.AndroidInjection;
import com.namig.tahmazli.deezerandroidclient.di.android.AndroidInjector;
import com.namig.tahmazli.deezerandroidclient.di.android.HasFragmentInjector;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements HasFragmentInjector {

    @Inject
    Navigator navigator;

    @Inject
    NavigationManager mNavigationManager;

    @Inject
    AndroidInjector<Fragment> fragmentInjector;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        mNavigationManager.attachFragmentManager(getSupportFragmentManager());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            navigator.navigateToGenres();
        }

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    @Override
    protected void onDestroy() {
        mNavigationManager.detachFragmentManager();
        super.onDestroy();
    }

    @Override
    public AndroidInjector<Fragment> getInjector() {
        return fragmentInjector;
    }
}
