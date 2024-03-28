package com.namig.tahmazli.deezerandroidclient.utils.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.namig.tahmazli.deezerandroidclient.di.android.AndroidInjection;
import com.namig.tahmazli.deezerandroidclient.di.android.ViewModelFactory;
import com.namig.tahmazli.deezerandroidclient.utils.BaseViewModel;
import com.namig.tahmazli.deezerandroidclient.utils.view.MvpView;
import com.namig.tahmazli.deezerandroidclient.utils.presenter.Presenter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

import javax.inject.Inject;

public abstract class BaseFragment<V extends MvpView, VM extends BaseViewModel<V, ? extends Presenter<V, ?>>> extends Fragment {

    @Inject
    protected ViewModelFactory.Injector mFactoryInjector;

    private VM mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Objects.requireNonNull(mFactoryInjector);

        final Type genericSuperClass = getClass().getGenericSuperclass();
        if (genericSuperClass instanceof ParameterizedType parameterizedType) {
            final Type viewModelType = parameterizedType.getActualTypeArguments()[1];

            if (viewModelType instanceof Class<?> c && ViewModel.class.isAssignableFrom(c)) {
                //noinspection unchecked
                final Class<VM> viewModelClass = (Class<VM>) c;
                mViewModel = new ViewModelProvider(this, mFactoryInjector).get(viewModelClass);
            }
        }

        requireActivity().getOnBackPressedDispatcher()
                .addCallback(this, new OnBackPressedCallback(shouldInterceptBackPress()) {
                    @Override
                    public void handleOnBackPressed() {
                        onBackPressed();
                    }
                });
    }

    protected boolean shouldInterceptBackPress() {
        return false;
    }

    protected void onBackPressed() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        AndroidInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater,
                                   @Nullable ViewGroup container,
                                   @Nullable Bundle savedInstanceState) {
        final V view = createView(inflater, container);
        mViewModel.getPresenter().attachView(view);
        return view.getRoot();
    }

    protected abstract V createView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container);

    @Override
    public void onDestroyView() {
        mViewModel.getPresenter().detachView();
        super.onDestroyView();
    }

    protected final VM getViewModel() {
        return mViewModel;
    }
}
