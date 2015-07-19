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

import com.github.pennyfive.altyleareena.model.TextBundle;
import com.github.pennyfive.altyleareena.model.categories.Category;

import java.util.List;

import auto.parcelgson.AutoParcelGson;
import auto.parcelgson.gson.annotations.SerializedName;

@AutoParcelGson
public abstract class Series {

    @SerializedName("id")
    public abstract String id();

    @SerializedName("image")
    abstract Image image();

    public String imageId() {
        return image() != null && image().available() ? image().id() : null;
    }

    @SerializedName("description")
    public abstract TextBundle descriptions();

    @SerializedName("title")
    public abstract TextBundle titles();

    @SerializedName("subject")
    abstract List<Category> categories();

}
