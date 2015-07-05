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

package com.github.pennyfive.altyleareena.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.pennyfive.altyleareena.utils.annotations.ProvidesComponent;

import dagger.Component;

public class DaggerUtils {
    private DaggerUtils() {
    }

    /**
     * Traverses context hierarchy (activity -> application) to find provider for given Dagger component.
     *
     * @param context
     * @param componentClass
     * @param <T>
     * @return Requested component or throws {@link IllegalStateException} if provider wasn't found.
     */
    @NonNull
    public static <T> T findComponent(Context context, Class<T> componentClass) {
        if (!componentClass.isAnnotationPresent(Component.class)) {
            throw new IllegalStateException(componentClass.getName() + " is not a component");
        }
        return tryFindComponent(context, componentClass);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    private static <T> T tryFindComponent(Context context, Class<T> componentClass) {
        if (context instanceof ProvidesComponent) {
            Object component = ((ProvidesComponent) context).provideComponent();
            if (componentClass.isInstance(component)) {
                return (T) component;
            }
        }
        if (context == context.getApplicationContext()) {
            throw new IllegalStateException("Could not find provider for component " + componentClass.getName());
        }
        return tryFindComponent(context.getApplicationContext(), componentClass);
    }
}
