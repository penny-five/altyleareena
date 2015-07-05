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

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.FrameLayout;

import com.github.pennyfive.altyleareena.ui.base.mvp.MvpView;

public class AbsMvpView extends FrameLayout implements MvpView {

    public AbsMvpView(Context context) {
        super(context);
    }

    public AbsMvpView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsMvpView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AbsMvpView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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

}
