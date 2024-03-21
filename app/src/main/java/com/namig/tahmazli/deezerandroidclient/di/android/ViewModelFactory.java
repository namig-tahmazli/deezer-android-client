package com.namig.tahmazli.deezerandroidclient.di.android;

import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.SavedStateHandleSupport;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.namig.tahmazli.deezerandroidclient.utils.GenericUtils;

import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

public interface ViewModelFactory<VM extends ViewModel> {
    VM create(final SavedStateHandle handle);

    class Injector implements ViewModelProvider.Factory {

        private final Map<Class<? extends ViewModel>, ViewModelFactory<?>> mFactories;

        @Inject
        Injector(final Map<Class<? extends ViewModel>, ViewModelFactory<?>> factories) {
            this.mFactories = factories;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass,
                                              @NonNull CreationExtras extras) {

            final SavedStateHandle handle = SavedStateHandleSupport.createSavedStateHandle(extras);

            return mFactories.entrySet().stream()
                    .filter(e -> modelClass.isAssignableFrom(e.getKey()))
                    .map(Map.Entry::getValue)
                    .findFirst()
                    .map(f -> {
                        final Class<?> genericType = GenericUtils.getGenericType(
                                f.getClass().getGenericInterfaces());

                        if (modelClass.isAssignableFrom(genericType)) {
                            //noinspection unchecked
                            final ViewModelFactory<T> factory = (ViewModelFactory<T>) f;
                            return factory.create(handle);
                        }

                        throw new IllegalStateException(
                                String.format(Locale.getDefault(),
                                        "ViewModel factory %s is not assignable from %s",
                                        f, modelClass));
                    })
                    .orElseThrow(() -> new IllegalStateException(String.format(Locale.getDefault(),
                            "Could not find a ViewModel factory binding for %s",
                            modelClass)));
        }
    }
}
