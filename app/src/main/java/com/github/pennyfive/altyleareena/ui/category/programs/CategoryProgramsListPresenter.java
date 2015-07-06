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

import android.util.Log;

import com.github.pennyfive.altyleareena.model.categories.Category;
import com.github.pennyfive.altyleareena.model.programs.Program;
import com.github.pennyfive.altyleareena.model.programs.ProgramStore;
import com.github.pennyfive.altyleareena.ui.base.mvp.MvpPresenter;

import rx.Scheduler;

public class CategoryProgramsListPresenter extends MvpPresenter<CategoryProgramsListView> {
    private final ProgramStore store;
    private final Category category;
    private final Scheduler scheduler;

    public CategoryProgramsListPresenter(ProgramStore store, Category category, Scheduler scheduler) {
        this.store = store;
        this.category = category;
        this.scheduler = scheduler;
    }

    @Override
    protected void onViewBound(CategoryProgramsListView view) {
        store.getPrograms(category).observeOn(scheduler).toList().doOnError(throwable -> {
            if (getView() != null) {
                getView().showError(throwable.getMessage());
            }
        }).doOnNext(programs -> {
            if (getView() != null) {
                getView().setPrograms(programs);
                getView().showContent();
            }
        }).subscribe();
    }

    @Override
    protected void onViewDropped(CategoryProgramsListView view) {

    }

    public void onProgramClicked(Program program) {
        Log.d("foo", "program clicked");
    }
}
