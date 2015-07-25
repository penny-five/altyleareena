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
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.pennyfive.altyleareena.R;
import com.github.pennyfive.altyleareena.model.categories.Category;
import com.github.pennyfive.altyleareena.ui.base.mvp.impl.AbsAsyncCollectionView;
import com.github.pennyfive.altyleareena.ui.base.recyclerview.RecyclerViewBuilder;
import com.github.pennyfive.altyleareena.ui.category.CategoryActivity;
import com.github.pennyfive.altyleareena.ui.main.MainActivityInstanceComponent;
import com.github.pennyfive.altyleareena.utils.DaggerUtils;

import javax.inject.Inject;

public class MainCategoriesViewImpl extends AbsAsyncCollectionView<Category, MainCategoriesItemHolder> implements MainCategoriesView {
    private MainCategoriesPresenter presenter;

    public MainCategoriesViewImpl(Context context) {
        this(context, null);
    }

    public MainCategoriesViewImpl(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainCategoriesViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DaggerUtils.findComponent(context, MainActivityInstanceComponent.class).inject(this);
    }

    @Inject
    public void setPresenter(MainCategoriesPresenter presenter) {
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
    protected void onSetupRecyclerViewBuilder(RecyclerViewBuilder builder) {
        super.onSetupRecyclerViewBuilder(builder);
        builder.setSpanCountFromResource(R.integer.main_categories_span_count);
        builder.setPaddingFromResource(R.dimen.grid_padding);
    }

    @Override
    protected MainCategoriesItemHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int ViewType) {
        return new MainCategoriesItemHolder(inflater.inflate(R.layout.item_category_with_image, parent, false));
    }

    @Override
    protected void onRetryAfterErrorClicked() {
        presenter.onViewRetryAfterErrorClicked();
    }

    @Override
    protected void onItemClick(int position, Category item) {
        presenter.onCategoryClicked(item);
    }

    @Override
    public void showCategory(Category category) {
        CategoryActivity.launchWith(getContext(), category);
    }

}
