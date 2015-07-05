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

package com.github.pennyfive.altyleareena.model.programs.impl;

import com.github.pennyfive.altyleareena.model.api.Response;
import com.github.pennyfive.altyleareena.model.api.YleApiService;
import com.github.pennyfive.altyleareena.model.categories.Category;
import com.github.pennyfive.altyleareena.model.programs.Program;
import com.github.pennyfive.altyleareena.model.programs.ProgramStore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

public class ApiServiceBackedProgramStore implements ProgramStore {
    private final YleApiService service;
    private final Map<Category, Observable<Response<List<Program>>>> observables = new HashMap<>();

    public ApiServiceBackedProgramStore(YleApiService service) {
        this.service = service;
    }

    @Override
    public Observable<Program> getPrograms(Category category) {
        return getOrCreateProgramObservable(category).flatMap(new Func1<Response<List<Program>>, Observable<Program>>() {
            @Override
            public Observable<Program> call(Response<List<Program>> response) {
                return Observable.from(response.getData());
            }
        });
    }

    private Observable<Response<List<Program>>> getOrCreateProgramObservable(Category category) {
        Observable<Response<List<Program>>> observable = observables.get(category);
        if (observable == null) {
            observable = service.getCategoryPrograms(category.id()).cache();
            observables.put(category, observable);
        }
        return observable;
    }
}
