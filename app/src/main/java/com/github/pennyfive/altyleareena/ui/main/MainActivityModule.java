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

package com.github.pennyfive.altyleareena.ui.main;

import com.github.pennyfive.altyleareena.model.categories.CategoryStore;
import com.github.pennyfive.altyleareena.model.programs.ProgramStore;
import com.github.pennyfive.altyleareena.ui.main.categories.CategoriesPresenter;
import com.github.pennyfive.altyleareena.ui.main.popular.PopularProgramsPresenter;
import com.github.pennyfive.altyleareena.utils.annotations.ActivityScope;
import com.github.pennyfive.altyleareena.utils.annotations.UiThread;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

@Module
public class MainActivityModule {

    @Provides
    @ActivityScope
    CategoriesPresenter provideCategoriesPresenter(CategoryStore store, @UiThread Scheduler scheduler) {
        return new CategoriesPresenter(store, scheduler);
    }

    @Provides
    @ActivityScope
    PopularProgramsPresenter providePopularProgramsPresenter(ProgramStore store, @UiThread Scheduler scheduler) {
        return new PopularProgramsPresenter(store, scheduler);
    }
}
