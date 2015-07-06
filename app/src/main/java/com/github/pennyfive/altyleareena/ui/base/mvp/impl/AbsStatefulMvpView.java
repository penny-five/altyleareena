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

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

import com.github.pennyfive.altyleareena.ui.base.mvp.StatefulMvpView;

/**
 * Base layout that implements {@link StatefulMvpView}.
 * <p>
 * Delegates View creation for each state to subclasses. Default implementation of {@link #onCreateLoadingView(LayoutInflater)} (and other similar
 * methods) returns an empty View.
 */
public abstract class AbsStatefulMvpView extends AbsMvpView implements StatefulMvpView {
    private static final String STATE_UNDEFINED = "Undefined";
    private static final String STATE_LOADING = "Loading";
    private static final String STATE_CONTENT = "Content";
    private static final String STATE_EMPTY = "Empty";
    private static final String STATE_ERROR = "Error";

    private LayoutInflater inflater;
    private View contentView;

    private String emptyText;
    private String errorText;

    private String currentState = STATE_UNDEFINED;

    public AbsStatefulMvpView(Context context) {
        this(context, null);
    }

    public AbsStatefulMvpView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AbsStatefulMvpView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflater = LayoutInflater.from(context);
        contentView = onCreateContentView(inflater);
    }

    private void changeState(String state) {
        if (!state.equals(currentState)) {
            currentState = state;
            applyState();
        }
    }

    private void applyState() {
        removeAllViews();

        View view = onCreateViewForState(currentState);
        if (view == null) {
            throw new IllegalStateException("Didn't create view for state " + currentState);
        }
        addView(view);
    }

    @Override
    public void saveHierarchyState(@NonNull SparseArray<Parcelable> container) {
        contentView.saveHierarchyState(container);
    }

    @Override
    public void restoreHierarchyState(@NonNull SparseArray<Parcelable> container) {
        contentView.restoreHierarchyState(container);
    }

    private View onCreateViewForState(String state) {
        switch (state) {
            case STATE_CONTENT:
                return contentView;
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

    public void showLoading() {
        changeState(STATE_LOADING);
    }

    public void showContent() {
        changeState(STATE_CONTENT);
    }

    public void showEmpty(String text) {
        emptyText = text;
        changeState(STATE_EMPTY);
    }

    public void showError(String text) {
        errorText = text;
        changeState(STATE_ERROR);
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
