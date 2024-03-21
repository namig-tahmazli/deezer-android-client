package com.namig.tahmazli.deezerandroidclient.di.android;

import com.namig.tahmazli.deezerandroidclient.utils.GenericUtils;

import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

public class AndroidInjector<T> {

    private final Map<Class<? extends T>, Injector.Factory<?>> mInjectors;

    @Inject
    public AndroidInjector(final Map<Class<? extends T>, Injector.Factory<?>> injectors) {
        this.mInjectors = injectors;
    }

    void inject(final T instance) throws InjectionFailedException {
        final Injector.Factory<T> factory = mInjectors.entrySet()
                .stream()
                .filter(e -> instance.getClass().isAssignableFrom(e.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .map(i -> {
                    final Class<?> genericTypeClass =
                            GenericUtils.getGenericType(i.getClass().getGenericInterfaces());

                    if (instance.getClass().isAssignableFrom(genericTypeClass)) {
                        //noinspection unchecked
                        final Class<Injector.Factory<T>> clazz = (Class<Injector.Factory<T>>) i.getClass();
                        return clazz.cast(i);
                    }

                    throw new InjectionFailedException(
                            String.format(
                                    Locale.getDefault(),
                                    "The type %s is not assignable from %s",
                                    genericTypeClass, instance));
                })
                .orElseThrow(() -> new InjectionFailedException(String.format(
                        Locale.getDefault(),
                        "Could not find an Injector for %s",
                        instance.getClass())));

        factory.create(instance).inject(instance);
    }
}
