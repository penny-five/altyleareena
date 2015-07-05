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

package com.github.pennyfive.altyleareena.model.categories.impl;

import com.github.pennyfive.altyleareena.model.api.Response;
import com.github.pennyfive.altyleareena.model.api.YleApiService;
import com.github.pennyfive.altyleareena.model.categories.Category;
import com.github.pennyfive.altyleareena.model.categories.CategoryStore;

import java.util.List;

import rx.Observable;

public class ApiServiceBackedCategoryStore implements CategoryStore {
    private final Observable<Response<List<Category>>> observable;

    public ApiServiceBackedCategoryStore(YleApiService service) {
        observable = service.getCategories().cache();
    }

    @Override
    public Observable<Category> getCategories() {
        return observable.flatMap(response -> Observable.from(response.getData()));
    }
}
