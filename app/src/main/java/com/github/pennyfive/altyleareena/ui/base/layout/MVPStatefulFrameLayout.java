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

package com.github.pennyfive.altyleareena.ui.base.layout;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

import com.github.pennyfive.altyleareena.ui.base.mvp.AsyncMVPView;

public abstract class MVPStatefulFrameLayout extends AbsStatefulFrameLayout implements AsyncMVPView {
    private static final int STATE_LOADING = 0;
    private static final int STATE_CONTENT = 1;
    private static final int STATE_EMPTY = 2;
    private static final int STATE_ERROR = 3;

    private String emptyText;
    private String errorText;

    public MVPStatefulFrameLayout(Context context) {
        super(context, null);
    }

    public MVPStatefulFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public MVPStatefulFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setSaveEnabled(true);
        setId(android.support.v7.appcompat.R.id.custom);
    }

    @Override
    public final void onSaveState(Bundle outState) {
        SparseArray<Parcelable> stateContainer = new SparseArray<>();
        saveHierarchyState(stateContainer);
        outState.putSparseParcelableArray(getClass().getName(), stateContainer);
    }

    @Override
    public final void onRestoreState(Bundle savedInstanceState) {
        SparseArray<Parcelable> stateContainer = savedInstanceState.getSparseParcelableArray(getClass().getName());
        restoreHierarchyState(stateContainer);
    }

    public void showLoading() {
        changeState(STATE_LOADING);
    }

    public void showEmpty(String text) {
        emptyText = text;
        changeState(STATE_EMPTY);
    }

    public void showError(String text) {
        errorText = text;
        changeState(STATE_ERROR);
    }

    public void showContent() {
        changeState(STATE_CONTENT);
    }

    protected View onCreateViewForState(@NonNull LayoutInflater inflater, int state) {
        switch (state) {
            case STATE_CONTENT:
                return onCreateContentView(inflater);
            case STATE_LOADING:
                return onCreateLoadingView(inflater);
            case STATE_ERROR:
                return onCreateErrorView(inflater, errorText);
            case STATE_EMPTY:
                return onCreateEmptyView(inflater, emptyText);
            default:
                throw new IllegalStateException();
        }
    }

    protected View onCreateContentView(@NonNull LayoutInflater inflater) {
        return new View(getContext());
    }

    protected View onCreateLoadingView(@NonNull LayoutInflater inflater) {
        return new View(getContext());
    }

    protected View onCreateErrorView(@NonNull LayoutInflater inflater, @Nullable String text) {
        return new View(getContext());
    }

    protected View onCreateEmptyView(@NonNull LayoutInflater inflater, @Nullable String text) {
        return new View(getContext());
    }
}
