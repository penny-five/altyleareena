package com.github.pennyfive.altyleareena.util;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.pennyfive.altyleareena.util.annotations.ProvidesComponent;

import dagger.Component;

public class DaggerUtils {
    private DaggerUtils() {
    }

    /**
     * Traverses context hierarchy (activity -> application) to find provider for given Dagger component.
     *
     * @param context
     * @param componentClass
     * @param <T>
     * @return Requested component or throws {@link IllegalStateException} if provider wasn't found.
     */
    @NonNull
    public static <T> T getComponent(Context context, Class<T> componentClass) {
        if (!componentClass.isAnnotationPresent(Component.class)) {
            throw new IllegalStateException(componentClass.getName() + " is not a component");
        }
        return tryProvideComponent(context, componentClass);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    private static <T> T tryProvideComponent(Context context, Class<T> componentClass) {
        if (context instanceof ProvidesComponent) {
            Object component = ((ProvidesComponent) context).provideComponent();
            if (componentClass.isInstance(component)) {
                return (T) component;
            }
        }
        if (context == context.getApplicationContext()) {
            throw new IllegalStateException("Could not find provider for component " + componentClass.getName());
        }
        return tryProvideComponent(context.getApplicationContext(), componentClass);
    }
}
