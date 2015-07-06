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

import com.github.pennyfive.altyleareena.ui.base.mvp.AsyncListView;

import rx.Observable;
import rx.Scheduler;

public abstract class AbsAsyncListPresenter<T, E extends AsyncListView<T>> extends AbsPresenter<E> {
    private final Scheduler scheduler;

    protected AbsAsyncListPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    protected void onViewBound(E view) {
        createObservable().observeOn(scheduler).toList().doOnError(throwable -> {
            getView().showError(throwable.getMessage());
        }).doOnNext(items -> {
            getView().setItems(items);
            if (items.isEmpty()) {
                getView().showEmpty("");
            } else {
                getView().showContent();
            }
        }).subscribe();
    }

    @Override
    protected void onViewDropped(E view) {

    }

    protected abstract Observable<T> createObservable();
}
