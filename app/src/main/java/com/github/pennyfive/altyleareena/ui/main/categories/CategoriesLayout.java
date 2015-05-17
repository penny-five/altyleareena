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

package com.github.pennyfive.altyleareena.ui.main.categories;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.github.pennyfive.altyleareena.model.categories.Category;
import com.github.pennyfive.altyleareena.ui.base.layout.MVPStatefulFrameLayout;
import com.github.pennyfive.altyleareena.ui.main.MainActivity;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * Implementation of {@link CategoriesMVPView}
 */
public class CategoriesLayout extends MVPStatefulFrameLayout implements CategoriesMVPView {
    private CategoriesMVPPresenter presenter;

    public CategoriesLayout(Context context) {
        this(context, null);
    }

    public CategoriesLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CategoriesLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Inject
    public void setPresenter(CategoriesMVPPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((MainActivity) getContext()).getActivityComponent().inject(this);
        presenter.bindView(this);
    }

    @Override
    public void onRestoreState(Bundle savedInstanceState) {

    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater) {
        return new View(getContext());
    }

    @Override
    public void setCategories(List<Category> categories) {
        Log.d("foo", Arrays.toString(categories.toArray()));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.dropView(this);
    }

    @Override
    public void onSaveState(Bundle outState) {

    }
}
