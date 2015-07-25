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

import android.support.annotation.DrawableRes;

import com.github.pennyfive.altyleareena.R;
import com.github.pennyfive.altyleareena.model.categories.Category;

/**
 * Theming for a specific {@link Category}. Includes category icon and theme color.
 */
public enum CategoryTheme {
    FINNISH_SERIES("5-133", R.color.category_theme_finnish_series, R.drawable.ic_category_finnish_series),
    FOREIGN_SERIES("5-134", R.color.category_theme_foreign_series, R.drawable.ic_category_finnish_series),
    MOVIES("5-135", R.color.category_theme_movies, R.drawable.ic_category_movies),
    NEWS("5-226", R.color.category_theme_news, R.drawable.ic_category_news),
    SPORTS("5-228", R.color.category_theme_sports, R.drawable.ic_category_sports),
    CHILDREN("5-195", R.color.category_theme_children, R.drawable.ic_category_children),
    UNKNOWN("-1", R.color.category_theme_unknown, android.R.color.transparent);

    private final String id;
    private final int colorResid;
    private final int iconResid;

    CategoryTheme(String id, int colorResid, int iconResid) {
        this.id = id;
        this.colorResid = colorResid;
        this.iconResid = iconResid;
    }

    @DrawableRes
    public int getColorResource() {
        return colorResid;
    }

    @DrawableRes
    public int getIconResource() {
        return iconResid;
    }

    /**
     * Resolves theme for a {@link Category}.
     *
     * @param category
     * @return Matching {@link CategoryTheme} or if it doesn't exist then for parent category if {@link Category#isSubCategory()}
     * returns {@code true}. If both lookups fail then returns {@link #UNKNOWN}.
     */
    public static CategoryTheme resolve(Category category) {
        CategoryTheme theme = forId(category.id());
        if (theme == null && category.isSubCategory()) {
            theme = forId(category.getParentCategoryId());
        }
        return theme != null ? theme : UNKNOWN;
    }

    private static CategoryTheme forId(String id) {
        for (CategoryTheme theme : values()) {
            if (theme.id.equals(id)) {
                return theme;
            }
        }
        return null;
    }

}
