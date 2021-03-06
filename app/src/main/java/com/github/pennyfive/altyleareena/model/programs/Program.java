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

package com.github.pennyfive.altyleareena.model.programs;

import com.github.pennyfive.altyleareena.model.Language;
import com.github.pennyfive.altyleareena.model.TextBundle;
import com.github.pennyfive.altyleareena.model.categories.Category;

import java.util.List;

import auto.parcelgson.AutoParcelGson;
import auto.parcelgson.gson.annotations.SerializedName;

@AutoParcelGson
public abstract class Program {
    @SerializedName("id")
    public abstract String id();

    @SerializedName("image")
    abstract Image image();

    public String imageId() {
        return image() != null && image().available() ? image().id() : null;
    }

    @SerializedName("title")
    public abstract TextBundle titles();

    public String getTitle(Language language) {
        return titles() != null ? titles().get(language) : null;
    }

    public String getSeriesTitle(Language language) {
        return series() != null && series().titles() != null ? series().titles().get(language) : null;
    }

    @SerializedName("description")
    public abstract TextBundle descriptions();

    @SerializedName("subject")
    public abstract List<Category> categories();

    @SerializedName("partOfSeries")
    public abstract Series series();

    public boolean isPartOfSeries() {
        return series() != null;
    }

}
