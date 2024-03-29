package com.namig.tahmazli.deezerandroidclient.utils;

import android.content.res.Resources;
import android.util.TypedValue;

public final class DimensionUtils {
    private DimensionUtils() {
        throw new IllegalStateException(String.format(
                "It is forbidden to construct %s class instance", getClass()));
    }

    public static int getDp(final int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
                Resources.getSystem().getDisplayMetrics());
    }
}
