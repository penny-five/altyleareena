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

package com.github.pennyfive.altyleareena.ui.category.programs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.pennyfive.altyleareena.R;
import com.github.pennyfive.altyleareena.model.programs.Program;
import com.github.pennyfive.altyleareena.ui.base.mvp.impl.AbsAsyncCollectionView;
import com.github.pennyfive.altyleareena.ui.category.CategoryActivityComponent;
import com.github.pennyfive.altyleareena.utils.DaggerUtils;

import javax.inject.Inject;

public class CategoryProgramsViewImpl extends AbsAsyncCollectionView<Program, CategoryProgramsItemViewHolder> implements CategoryProgramsView {
    private CategoryProgramsPresenter presenter;

    public CategoryProgramsViewImpl(Context context) {
        this(context, null);
    }

    public CategoryProgramsViewImpl(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CategoryProgramsViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DaggerUtils.findComponent(context, CategoryActivityComponent.class).injectProgramsView(this);
    }

    @Inject
    public void setPresenter(CategoryProgramsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.bindView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.dropView(this);
    }

    @Override
    protected CategoryProgramsItemViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int ViewType) {
        return new CategoryProgramsItemViewHolder(inflater.inflate(R.layout.item_one_line, parent, false));
    }

    @Override
    protected void onItemClick(int position, Program item) {
        presenter.onProgramClicked(item);
    }

    @Override
    public void showProgram(Program program) {

    }
}