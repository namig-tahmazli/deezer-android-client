package com.namig.tahmazli.deezerandroidclient.di.android;

import android.app.Activity;
import android.app.Application;

import androidx.fragment.app.Fragment;

import java.util.Locale;
import java.util.Objects;

public class AndroidInjection {

    private AndroidInjection() {
        throw new IllegalStateException("Android Injection class should not be constructed");
    }

    public static <A extends Activity> void inject(final A instance)
            throws InjectionFailedException {
        final Application app = instance.getApplication();

        if (app instanceof HasActivityInjector hasActivityInjector) {
            hasActivityInjector.getInjector().inject(instance);
        } else {
            throw new InjectionFailedException(
                    String.format(
                            Locale.getDefault(),
                            "Could not find ActivityInjector to inject %s",
                            instance));
        }
    }

    public static <F extends Fragment> void inject(final F instance)
            throws InjectionFailedException {
        FragmentInjector.tryToInject(instance);
    }

    private static class FragmentInjector {

        static <F extends Fragment> void tryToInject(final F instance)
                throws InjectionFailedException {

            final AndroidInjector<Fragment> fragmentInjector =
                    findFragmentInjector(instance).getInjector();

            fragmentInjector.inject(instance);
        }

        private static HasFragmentInjector findFragmentInjector(final Fragment instance)
                throws InjectionFailedException {
            final Fragment parent = instance.getParentFragment();

            if (parent == null) {
                final Activity activity = instance.getActivity();

                Objects.requireNonNull(activity,
                        String.format(Locale.getDefault(),
                                "Fragment %s is supposed to be attached to a specific activity",
                                instance));

                if (activity instanceof HasFragmentInjector hasFragmentInjector) {
                    return hasFragmentInjector;
                } else {
                    throw new InjectionFailedException(
                            String.format(Locale.getDefault(),
                                    "No Fragment Injector found to inject %s",
                                    instance));
                }
            } else if (parent instanceof HasFragmentInjector hasFragmentInjector) {
                return hasFragmentInjector;
            } else {
                return findFragmentInjector(parent);
            }
        }
    }
}
