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
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.pennyfive.altyleareena.R;
import com.github.pennyfive.altyleareena.ui.base.mvp.View;

public class AbsView extends FrameLayout implements View {
    private ViewGroup container;

    public AbsView(Context context) {
        this(context, null);
    }

    public AbsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AbsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupContentDecor();

    }

    private void setupContentDecor() {
        android.view.View decorView = onCreateContentDecor(LayoutInflater.from(getContext()), this);
        super.addView(decorView, -1, generateDefaultLayoutParams());

        container = (ViewGroup) decorView.findViewById(R.id.view_container);
        if (container == null) {
            throw new IllegalStateException("Content decor doesn't have ViewGroup with id R.id.view_container");
        }
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

    @Override
    public final void addView(@NonNull android.view.View child, int index, @NonNull ViewGroup.LayoutParams params) {
        throw new UnsupportedOperationException("Use #setContent(View)");
    }

    @Override
    public void removeAllViews() {
        throw new UnsupportedOperationException("Don't call manually");
    }

    protected android.view.View onCreateContentDecor(LayoutInflater inflater, ViewGroup parent) {
        FrameLayout decorLayout = new FrameLayout(getContext());
        decorLayout.setId(R.id.view_container);
        return decorLayout;
    }

    protected void setContent(android.view.View view) {
        container.removeAllViews();
        container.addView(view);
    }
}
