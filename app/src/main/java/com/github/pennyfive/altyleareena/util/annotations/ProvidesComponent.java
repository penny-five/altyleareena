package com.github.pennyfive.altyleareena.util.annotations;

import android.content.Context;

/**
 * @param <T>
 * @see {@link com.github.pennyfive.altyleareena.util.DaggerUtils#getComponent(Context, Class)}
 */
public interface ProvidesComponent<T> {
    T provideComponent();
}
