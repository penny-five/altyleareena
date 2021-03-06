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
import com.github.pennyfive.altyleareena.ui.base.mvp.impl.AbsAsyncCollectionPresenter;

import rx.Observable;
import rx.Scheduler;

public class MainCategoriesPresenter extends AbsAsyncCollectionPresenter<Category, MainCategoriesView> {
    private final CategoryStore store;

    public MainCategoriesPresenter(CategoryStore store, Scheduler scheduler) {
        super(scheduler);
        this.store = store;
    }

    @Override
    protected Observable<Category> createObservable() {
        return store.getMainCategories();
    }

    public void onCategoryClicked(Category category) {
        getView().showCategory(category);
    }

}
