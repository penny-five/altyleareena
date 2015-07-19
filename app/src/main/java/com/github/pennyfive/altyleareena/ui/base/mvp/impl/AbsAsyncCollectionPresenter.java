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

import com.github.pennyfive.altyleareena.ui.base.mvp.AsyncView;
import com.github.pennyfive.altyleareena.ui.base.mvp.CollectionView;

import java.util.Collection;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscription;

public abstract class AbsAsyncCollectionPresenter<T, E extends CollectionView<T> & AsyncView> extends AbsPresenter<E> {
    private final Scheduler scheduler;
    private Subscription subscription;
    private Collection<T> data;
    private Throwable error;

    protected AbsAsyncCollectionPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    protected void onViewBound(E view) {
        if (data != null) {
            showItems();
        } else if (error != null) {
            showError();
        } else if (subscription != null) {
            showLoading();
        } else {
            subscribe();
        }
    }

    private void subscribe() {
        showLoading();

        subscription = createObservable().observeOn(scheduler).toList().subscribe(new Observer<List<T>>() {
            @Override
            public void onCompleted() {
                subscription = null;
            }

            @Override
            public void onError(Throwable e) {
                error = e;
                showError();
            }

            @Override
            public void onNext(List<T> items) {
                data = items;
                showItems();
            }
        });
    }

    private void showLoading() {
        E view = getView();
        if (view != null) {
            view.showLoading();
        }
    }

    private void showItems() {
        E view = getView();
        if (view != null) {
            view.setItems(data);
            if (data.isEmpty()) {
                view.showEmpty();
            } else {
                view.showContent();
            }
        }
    }

    private void showError() {
        E view = getView();
        if (view != null) {
            view.showError(error);
        }
    }

    public void onViewRetryAfterErrorClicked() {
        error = null;
        subscribe();
    }

    @Override
    protected void onViewDropped(E view) {

    }

    protected abstract Observable<T> createObservable();
}
