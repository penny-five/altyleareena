/*
 * Copyright 2015 Joonas Lehtonen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.pennyfive.altyleareena.ui.base.mvp.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.github.pennyfive.altyleareena.ui.base.mvp.Presenter;
import com.github.pennyfive.altyleareena.ui.base.mvp.View;

public abstract class AbsPresenter<T extends View> implements Presenter<T> {
    private static final String DEBUG_LOG_TAG = "AbsPresenter";
    private static final boolean DEBUG = true;
    private Bundle savedState;
    private T view;

    protected AbsPresenter() {
        if (DEBUG) Log.d(DEBUG_LOG_TAG, "create presenter " + this.getClass().getSimpleName());
    }

    @Nullable
    public T getView() {
        return view;
    }

    @Override
    public void bindView(T view) {
        if (this.view != null) {
            throw new IllegalStateException("Tried to bind presenter that already was bound to a view");
        }

        this.view = view;
        onViewBound(view);
        if (DEBUG) Log.d(DEBUG_LOG_TAG, "bind view " + view.getClass().getSimpleName() + " -> " + this.getClass().getSimpleName());

        if (savedState != null && !savedState.isEmpty()) {
            view.onRestoreState(savedState);
            if (DEBUG) Log.d(DEBUG_LOG_TAG, "restore state " + savedState + " -> " + this.getClass().getSimpleName());
        }
    }

    abstract protected void onViewBound(T view);

    @Override
    public void dropView(T view) {
        if (this.view != view) {
            throw new IllegalStateException("Tried to drop unbound view");
        }

        savedState = new Bundle();
        view.onSaveState(savedState);
        if (DEBUG) Log.d(DEBUG_LOG_TAG, "save state " + savedState + " -> " + this.getClass().getSimpleName());

        onViewDropped(this.view);
        this.view = null;
        if (DEBUG) Log.d(DEBUG_LOG_TAG, "drop view " + view.getClass().getSimpleName() + " -> " + this.getClass().getSimpleName());
    }

    abstract protected void onViewDropped(T view);
}
