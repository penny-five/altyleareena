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

import com.github.pennyfive.altyleareena.model.categories.Category;
import com.github.pennyfive.altyleareena.model.programs.Program;
import com.github.pennyfive.altyleareena.model.programs.ProgramStore;
import com.github.pennyfive.altyleareena.ui.base.mvp.impl.AbsAsyncCollectionPresenter;

import rx.Observable;
import rx.Scheduler;

public class CategoryProgramsPresenter extends AbsAsyncCollectionPresenter<Program, CategoryProgramsView> {
    private final ProgramStore store;
    private final Category category;

    public CategoryProgramsPresenter(ProgramStore store, Category category, Scheduler scheduler) {
        super(scheduler);
        this.store = store;
        this.category = category;
    }

    @Override
    protected Observable<Program> createObservable() {
        return store.getPrograms(category);
    }

    public void onProgramClicked(Program program) {
        getView().showProgram(program);
    }
}
