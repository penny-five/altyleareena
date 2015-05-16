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

package com.github.pennyfive.altyleareena;

import android.content.Context;

import com.github.pennyfive.altyleareena.model.api.YleApiService;
import com.github.pennyfive.altyleareena.model.categories.CategoriesStore;
import com.github.pennyfive.altyleareena.model.categories.impl.ApiServiceBackedCategoriesStore;
import com.github.pennyfive.altyleareena.util.ForApplication;

import dagger.Module;
import dagger.Provides;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

@Module
public class ApplicationModule {
    private final MyApplication application;

    ApplicationModule(MyApplication application) {
        this.application = application;
    }

    @Provides
    @ForApplication
    Context provideApplicationContext() {
        return application;
    }

    @Provides
    @ForApplication
    CategoriesStore provideCategories(YleApiService service) {
        return new ApiServiceBackedCategoriesStore(service);
    }

    @Provides
    @ForApplication
    YleApiService provideYleApiService() {
        return new RestAdapter.Builder()
                .setEndpoint(BuildConfig.YLE_API_ENDPOINT)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        // app_id and app_key are required for all API queries.
                        request.addQueryParam("app_id", BuildConfig.YLE_APP_ID);
                        request.addQueryParam("app_key", BuildConfig.YLE_APP_KEY);
                    }
                })
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.BASIC : RestAdapter.LogLevel.NONE)
                .build()
                .create(YleApiService.class);
    }
}
