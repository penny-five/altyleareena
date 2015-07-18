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

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Map;

import auto.parcelgson.AutoParcelGson;
import auto.parcelgson.gson.annotations.SerializedName;

/**
 * Describes a category in Yle Areena (tv shows, movies, news etc.)
 */
@AutoParcelGson
public abstract class Category implements Parcelable {
    @SerializedName("id")
    public abstract String id();

    @SerializedName("title")
    abstract Map<String, String> titles();

    public String getTitleInLanguage(@NonNull String language) {
        return titles().get(language);
    }

    @Nullable
    @SerializedName("broader")
    abstract CategoryInfo parentInfo();

    public boolean isSubCategory() {
        return parentInfo() != null;
    }

    public String getParentCategoryId() {
        return isSubCategory() ? parentInfo().id() : null;
    }

    @AutoParcelGson
    abstract static class CategoryInfo implements Parcelable {
        abstract String id();
    }
}
