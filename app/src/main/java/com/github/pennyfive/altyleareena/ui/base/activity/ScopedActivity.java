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

import com.github.pennyfive.altyleareena.utils.DaggerUtils;
import com.github.pennyfive.altyleareena.utils.annotations.ActivityInstanceScope;
import com.github.pennyfive.altyleareena.utils.annotations.ActivityScope;
import com.github.pennyfive.altyleareena.utils.annotations.ComponentProvider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Activity base class that helps dealing with Dagger components that should be retained over configuration changes.
 * <p>
 * Subclasses should annotate provider methods with either {@link ActivityScope} or {@link ActivityInstanceScope} depending on if the
 * dependency should be retained or not.
 */
public abstract class ScopedActivity extends RetainedActivity implements ComponentProvider {
    private List<Object> instanceComponents = new ArrayList<>();
    private List<Object> scopedComponents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //noinspection unchecked
        scopedComponents = (List<Object>) getRetainedData();
        boolean shouldInstantiateScopedComponents = scopedComponents == null;
        if (shouldInstantiateScopedComponents) {
            scopedComponents = new ArrayList<>();
            setRetainedData(scopedComponents);
        }

        for (Method method : DaggerUtils.findProviderMethods(this.getClass())) {
            try {
                if (shouldInstantiateScopedComponents && method.isAnnotationPresent(ActivityScope.class)) {
                    scopedComponents.add(method.invoke(this));
                } else if (method.isAnnotationPresent(ActivityInstanceScope.class)) {
                    instanceComponents.add(method.invoke(this));
                }
            } catch (InvocationTargetException e) {
                throw new IllegalStateException(e);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    @Override
    public <T> T findComponent(Class<T> componentClass) {
        T component = searchComponent(instanceComponents, componentClass);
        if (component == null) {
            component = searchComponent(scopedComponents, componentClass);
        }
        return component != null ? component : DaggerUtils.findComponent(getApplication(), componentClass);
    }

    @SuppressWarnings("unchecked")
    private static <T> T searchComponent(Collection<Object> items, Class<T> clazz) {
        for (Object o : items) {
            if (clazz.isInstance(o)) {
                return (T) o;
            }
        }
        return null;
    }
}
