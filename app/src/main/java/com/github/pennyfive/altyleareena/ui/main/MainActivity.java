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

import android.view.View;
import android.view.ViewGroup;

import com.github.pennyfive.altyleareena.ApplicationComponent;
import com.github.pennyfive.altyleareena.R;
import com.github.pennyfive.altyleareena.ui.base.activity.PagerActivity;
import com.github.pennyfive.altyleareena.ui.main.categories.MainCategoriesViewImpl;
import com.github.pennyfive.altyleareena.ui.main.popular.PopularProgramsViewImpl;
import com.github.pennyfive.altyleareena.utils.annotations.ActivityInstanceScope;
import com.github.pennyfive.altyleareena.utils.annotations.ActivityScope;
import com.github.pennyfive.altyleareena.utils.annotations.ProvidesComponent;

public class MainActivity extends PagerActivity {

    @ProvidesComponent
    @ActivityScope
    public MainActivityComponent provideActivityComponent() {
        return DaggerMainActivityComponent.builder()
                .applicationComponent(findComponent(ApplicationComponent.class))
                .mainActivityModule(new MainActivityModule())
                .build();
    }

    @ProvidesComponent
    @ActivityInstanceScope
    public MainActivityInstanceComponent provideActivityInstanceComponent() {
        return DaggerMainActivityInstanceComponent.builder()
                .mainActivityComponent(findComponent(MainActivityComponent.class))
                .mainActivityInstanceModule(new MainActivityInstanceModule(this))
                .build();
    }

    @Override
    protected int getCount() {
        return 2;
    }

    @Override
    protected String getPageTitle(int position) {
        switch (position) {
            case 0:
                return getString(R.string.title_categories);
            case 1:
                return getString(R.string.title_popular_programs);
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    protected View instantiateView(ViewGroup container, int position) {
        switch (position) {
            case 0:
                return new MainCategoriesViewImpl(this);
            case 1:
                return new PopularProgramsViewImpl(this);
            default:
                throw new IllegalStateException();
        }
    }
}
