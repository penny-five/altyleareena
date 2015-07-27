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

import com.github.pennyfive.altyleareena.model.api.ProgramQueryOrderParams;
import com.github.pennyfive.altyleareena.model.api.ProgramQueryOrderParams.OrderBy;
import com.github.pennyfive.altyleareena.model.api.ProgramQueryOrderParams.SortOrder;
import com.github.pennyfive.altyleareena.model.api.ProgramQueryOrderParams.TimeWindow;
import com.github.pennyfive.altyleareena.model.api.Response;
import com.github.pennyfive.altyleareena.model.api.YleApiService;
import com.github.pennyfive.altyleareena.model.categories.Category;
import com.github.pennyfive.altyleareena.model.programs.Program;
import com.github.pennyfive.altyleareena.model.programs.ProgramStore;

import rx.Observable;

public class ApiServiceBackedProgramStore implements ProgramStore {
    private final YleApiService service;

    public ApiServiceBackedProgramStore(YleApiService service) {
        this.service = service;
    }

    @Override
    public Observable<Program> getPrograms(Category category) {
        return service.getPrograms(category.id(), null).flatMap(response -> Observable.from(response.getData()));
    }

    @Override
    public Observable<Program> getProgram(String programId) {
        return service.getProgram(programId).map(Response::getData);
    }

    @Override
    public Observable<Program> getPopularPrograms() {
        ProgramQueryOrderParams orderParams = new ProgramQueryOrderParams();
        orderParams.setOrderBy(OrderBy.PLAYCOUNT);
        orderParams.setTimeWindow(TimeWindow.DAY);
        orderParams.setSortOrder(SortOrder.DESC);
        return service.getPrograms(null, orderParams).flatMap(response -> Observable.from(response.getData()));
    }

}
