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

package com.github.pennyfive.altyleareena;

import com.github.pennyfive.altyleareena.ui.base.images.CloudinaryImageLoaderFactory;
import com.github.pennyfive.altyleareena.ui.category.CategoryActivityAppScopedBundle;
import com.github.pennyfive.altyleareena.ui.main.MainActivityAppScopedBundle;
import com.github.pennyfive.altyleareena.utils.annotations.ApplicationScope;

import dagger.Component;

/**
 * Dagger component with application scope.
 */
@ApplicationScope
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    MainActivityAppScopedBundle getMainActivityBundle();

    CategoryActivityAppScopedBundle getCategoryActivityBundle();

    CloudinaryImageLoaderFactory getImageLoaderFactory();
}
