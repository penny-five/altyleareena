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

package com.github.pennyfive.altyleareena.ui;

import android.os.Parcelable;
import android.util.SparseArray;
import android.view.View;

public class Views {

    public static void saveHierarchyStates(SparseArray<Parcelable> dest, View firstView, View... moreViews) {
        if (firstView != null) {
            firstView.saveHierarchyState(dest);
        }
        for (View view : moreViews) {
            if (view != null) {
                view.saveHierarchyState(dest);
            }
        }
    }

    public static void restoreHierarchyStates(SparseArray<Parcelable> src, View firstView, View... moreViews) {
        if (firstView != null) {
            firstView.restoreHierarchyState(src);
        }
        for (View view : moreViews) {
            if (view != null) {
                view.restoreHierarchyState(src);
            }
        }
    }
}
