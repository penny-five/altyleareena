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

package com.github.pennyfive.altyleareena.model.categories;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Describes a category in Yle Areena (tv shows, movies, news etc.)
 */
public class Category {
    @SerializedName("id") private String id;
    @SerializedName("title") private Map<String, String> titles;

    public String getId() {
        return id;
    }

    public String getTitleInLanguage(@NonNull String language) {
        return titles.get(language);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", titles=" + titles +
                '}';
    }
}
