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

import android.util.Log;

import com.github.pennyfive.altyleareena.model.categories.CategoriesStore;
import com.github.pennyfive.altyleareena.model.categories.Category;
import com.github.pennyfive.altyleareena.ui.base.mvp.MvpPresenter;

import java.util.List;

import rx.Observer;
import rx.Scheduler;

public class CategoriesMvpPresenter extends MvpPresenter<CategoriesMvpView> {
    private final CategoriesStore store;
    private final Scheduler scheduler;

    public CategoriesMvpPresenter(CategoriesStore store, Scheduler scheduler) {
        super();
        this.store = store;
        this.scheduler = scheduler;
    }

    @Override
    protected void onViewBound(CategoriesMvpView view) {
        store.getCategories().observeOn(scheduler).toList().subscribe(new Observer<List<Category>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Category> categories) {
                if (getView() != null) {
                    getView().setCategories(categories);
                    getView().showContent();
                }
            }
        });
    }

    @Override
    protected void onViewDropped(CategoriesMvpView view) {

    }

    public void onCategoryClicked(Category category) {
        Log.d("foo", "on click " + category.toString());
    }
}
