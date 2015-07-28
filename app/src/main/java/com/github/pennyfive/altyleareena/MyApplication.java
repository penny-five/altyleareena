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

import android.app.Application;

import com.github.pennyfive.altyleareena.utils.annotations.ApplicationScope;
import com.github.pennyfive.altyleareena.utils.annotations.ComponentProvider;
import com.github.pennyfive.altyleareena.utils.annotations.ProvidesComponent;

/**
 * TODO: rename once app name has been decided
 */
public class MyApplication extends Application implements ComponentProvider {
    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = createApplicationComponent();
    }

    private ApplicationComponent createApplicationComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    @ProvidesComponent
    @ApplicationScope
    public ApplicationComponent provideApplicationComponent() {
        return component;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T findComponent(Class<T> componentClass) {
        return componentClass == ApplicationComponent.class ? (T) component : null;
    }
}
