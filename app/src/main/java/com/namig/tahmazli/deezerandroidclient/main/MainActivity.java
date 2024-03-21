package com.namig.tahmazli.deezerandroidclient.main;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.Fragment;

import com.namig.tahmazli.deezerandroidclient.R;
import com.namig.tahmazli.deezerandroidclient.di.android.AndroidInjection;
import com.namig.tahmazli.deezerandroidclient.di.android.AndroidInjector;
import com.namig.tahmazli.deezerandroidclient.di.android.HasFragmentInjector;
import com.namig.tahmazli.deezerandroidclient.genres.GenresFragment;
import com.namig.tahmazli.deezerandroidclient.navigation.NavigationManager;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements HasFragmentInjector {

    @Inject
    NavigationManager navigationManager;

    @Inject
    AndroidInjector<Fragment> fragmentInjector;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            navigationManager.navigateTo(new GenresFragment(), null);
        }

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    @Override
    public AndroidInjector<Fragment> getInjector() {
        return fragmentInjector;
    }
}
