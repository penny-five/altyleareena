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

package com.github.pennyfive.altyleareena.model.api;

import com.github.pennyfive.altyleareena.model.categories.Category;
import com.github.pennyfive.altyleareena.model.programs.Program;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface YleApiService {
    @GET("/categories.json")
    Observable<Response<List<Category>>> getCategories();

    @GET("/items.json?availability=ondemand&mediaobject=video")
    Observable<Response<List<Program>>> getPrograms(@Query("category") String categoryId, @Query("order") ProgramQueryOrderParams order);

}
