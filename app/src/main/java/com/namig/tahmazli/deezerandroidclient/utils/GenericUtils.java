package com.namig.tahmazli.deezerandroidclient.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericUtils {
    private GenericUtils() {
        throw new IllegalStateException("GenericUtils should not be constructed");
    }

    public static Class<?> getGenericType(final Type[] interfaces) {
        final Type type = interfaces[0];

        if (type instanceof ParameterizedType parameterizedType) {
            return getGenericTypeArgument(parameterizedType);
        } else {
            return getGenericType(((Class<?>) type).getGenericInterfaces());
        }
    }

    private static Class<?> getGenericTypeArgument(final ParameterizedType parameterizedType) {
        final Type typeArgument = parameterizedType.getActualTypeArguments()[0];
        if (typeArgument instanceof ParameterizedType deeper) {
            return getGenericTypeArgument(deeper);
        } else {
            return ((Class<?>) typeArgument);
        }
    }
}
