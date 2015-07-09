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
import com.github.pennyfive.altyleareena.ui.base.images.CloudinaryImageLoader;
import com.github.pennyfive.altyleareena.ui.base.images.CloudinaryImageLoaderFactory;
import com.github.pennyfive.altyleareena.ui.category.programs.CategoryProgramsPresenter;
import com.github.pennyfive.altyleareena.utils.annotations.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class CategoryActivityModule {
    private final CategoryActivity activity;
    private final Category category;

    CategoryActivityModule(CategoryActivity activity, Category category) {
        this.activity = activity;
        this.category = category;
    }

    @Provides
    @ActivityScope
    CategoryProgramsPresenter provideProgramsPresenter(CategoryActivityAppScopedBundle bundle) {
        return bundle.getProgramsPresenter(category);
    }

    @Provides
    @ActivityScope
    CloudinaryImageLoader provideImageLoader(CloudinaryImageLoaderFactory factory) {
        return factory.createLoader(activity);
    }
}
