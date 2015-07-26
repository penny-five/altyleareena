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

package com.github.pennyfive.altyleareena.ui.base.activity;

import android.os.Bundle;

import com.github.pennyfive.altyleareena.ApplicationComponent;
import com.github.pennyfive.altyleareena.utils.DaggerUtils;
import com.github.pennyfive.altyleareena.utils.annotations.ProvidesComponent;

/**
 * Base class that helps dealing with two Dagger components, one that has the same lifecycle as the Activity instance, and one that is
 * retained when the configuration is changed.
 *
 * @param <E> {@link dagger.Component} with {@link com.github.pennyfive.altyleareena.utils.annotations.ActivityScope}
 * @param <S> {@link dagger.Component} with {@link com.github.pennyfive.altyleareena.utils.annotations.ActivityInstanceScope}
 */
public abstract class ScopedActivity<E, S> extends RetainedActivity implements ProvidesComponent<S> {
    private ApplicationComponent applicationComponent;
    private E activityComponent;
    private S activityInstanceComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        applicationComponent = DaggerUtils.findComponent(this, ApplicationComponent.class);

        //noinspection unchecked
        activityComponent = (E) getRetainedData();
        if (activityComponent == null) {
            activityComponent = onCreateActivityComponent();
            setRetainedData(activityComponent);
        }

        activityInstanceComponent = onCreateActivityInstanceComponent();
    }

    protected abstract E onCreateActivityComponent();

    protected abstract S onCreateActivityInstanceComponent();

    protected ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    protected E getActivityComponent() {
        return activityComponent;
    }

    protected S getActivityInstanceComponent() {
        return activityInstanceComponent;
    }

    @Override
    public final S provideComponent() {
        return activityInstanceComponent;
    }
}
