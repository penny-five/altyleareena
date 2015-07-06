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

import com.github.pennyfive.altyleareena.model.categories.Category;
import com.github.pennyfive.altyleareena.model.categories.CategoryStore;
import com.github.pennyfive.altyleareena.ui.base.mvp.MvpPresenter;

import rx.Scheduler;

public class CategoryListPresenter extends MvpPresenter<CategoryListView> {
    private final CategoryStore store;
    private final Scheduler scheduler;

    public CategoryListPresenter(CategoryStore store, Scheduler scheduler) {
        super();
        this.store = store;
        this.scheduler = scheduler;
    }

    @Override
    protected void onViewBound(CategoryListView view) {
        store.getCategories().observeOn(scheduler).toList().doOnError(throwable -> {
            if (getView() != null) {
                getView().showError(throwable.getMessage());
            }
        }).doOnNext(categories -> {
            if (getView() != null) {
                getView().setCategories(categories);
                getView().showContent();
            }
        }).subscribe();
    }

    @Override
    protected void onViewDropped(CategoryListView view) {

    }

    public void onCategoryClicked(Category category) {
        getView().showCategoryView(category);
    }
}
