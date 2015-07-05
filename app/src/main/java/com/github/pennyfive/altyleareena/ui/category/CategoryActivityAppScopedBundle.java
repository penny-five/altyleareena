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

import com.github.pennyfive.altyleareena.model.categories.Category;
import com.github.pennyfive.altyleareena.model.programs.ProgramStore;
import com.github.pennyfive.altyleareena.ui.category.programs.CategoryProgramsListPresenter;

import java.util.HashMap;
import java.util.Map;

import rx.Scheduler;

public class CategoryActivityAppScopedBundle {
    private final ProgramStore store;
    private final Scheduler scheduler;
    private final Map<Category, CategoryProgramsListPresenter> presenterCache = new HashMap<>();

    public CategoryActivityAppScopedBundle(ProgramStore store, Scheduler scheduler) {
        this.store = store;
        this.scheduler = scheduler;
    }

    public CategoryProgramsListPresenter getProgramsListPresenter(Category category) {
        CategoryProgramsListPresenter presenter = presenterCache.get(category);
        if (presenter == null) {
            presenter = new CategoryProgramsListPresenter(store, category, scheduler);
            presenterCache.put(category, presenter);
        }
        return presenter;
    }
}
