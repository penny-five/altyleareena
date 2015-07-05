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

package com.github.pennyfive.altyleareena.ui.category;

import android.util.Log;

import com.github.pennyfive.altyleareena.model.categories.Category;
import com.github.pennyfive.altyleareena.model.programs.Program;
import com.github.pennyfive.altyleareena.model.programs.ProgramsStore;
import com.github.pennyfive.altyleareena.ui.base.mvp.MvpPresenter;

import java.util.List;

import rx.Observer;
import rx.Scheduler;

public class CategoryMvpPresenter extends MvpPresenter<CategoryMvpView> {
    private final ProgramsStore store;
    private final Category category;
    private final Scheduler scheduler;

    public CategoryMvpPresenter(ProgramsStore store, Category category, Scheduler scheduler) {
        this.store = store;
        this.category = category;
        this.scheduler = scheduler;
    }

    @Override
    protected void onViewBound(CategoryMvpView view) {
        store.getPrograms(category).observeOn(scheduler).toList().subscribe(new Observer<List<Program>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (getView() != null) {
                    getView().showError(e.getMessage());
                }
            }

            @Override
            public void onNext(List<Program> programs) {
                if (getView() != null) {
                    getView().setPrograms(programs);
                    getView().showContent();
                }
            }
        });
    }

    @Override
    protected void onViewDropped(CategoryMvpView view) {

    }

    public void onProgramClicked(Program program) {
        Log.d("foo", "program clicked");
    }
}
