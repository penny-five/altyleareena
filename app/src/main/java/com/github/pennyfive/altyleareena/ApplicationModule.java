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

import com.github.pennyfive.altyleareena.model.TextBundle;
import com.github.pennyfive.altyleareena.model.api.YleApiService;
import com.github.pennyfive.altyleareena.model.categories.CategoryStore;
import com.github.pennyfive.altyleareena.model.categories.impl.ApiServiceBackedCategoryStore;
import com.github.pennyfive.altyleareena.model.json.TextBundleDeserializer;
import com.github.pennyfive.altyleareena.model.programs.ProgramStore;
import com.github.pennyfive.altyleareena.model.programs.impl.ApiServiceBackedProgramStore;
import com.github.pennyfive.altyleareena.ui.base.images.CloudinaryImageLoaderFactory;
import com.github.pennyfive.altyleareena.utils.annotations.ApplicationScope;
import com.github.pennyfive.altyleareena.utils.annotations.UiThread;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;

import auto.parcelgson.gson.AutoParcelGsonTypeAdapterFactory;
import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

@Module
public class ApplicationModule {
    private final MyApplication application;

    ApplicationModule(MyApplication application) {
        this.application = application;
    }

    @Provides
    @ApplicationScope
    Context provideApplicationContext() {
        return application;
    }

    @Provides
    @ApplicationScope
    CloudinaryImageLoaderFactory provideImageLoaderFactory() {
        return new CloudinaryImageLoaderFactory(BuildConfig.YLE_CLOUDINARY_ENDPOINT, BuildConfig.DEBUG);
    }

    @Provides
    @ApplicationScope
    CategoryStore provideCategoriesStore(YleApiService service) {
        return new ApiServiceBackedCategoryStore(service, Arrays.asList(BuildConfig.MAIN_CATEGORIES));
    }

    @Provides
    @ApplicationScope
    ProgramStore provideProgramsStore(YleApiService service) {
        return new ApiServiceBackedProgramStore(service);
    }

    @Provides
    @ApplicationScope
    YleApiService provideYleApiService(Gson gson) {
        return new RestAdapter.Builder()
                .setEndpoint(BuildConfig.YLE_API_ENDPOINT)
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(request -> {
                    request.addQueryParam("app_id", BuildConfig.YLE_APP_ID);
                    request.addQueryParam("app_key", BuildConfig.YLE_APP_KEY);
                })
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.BASIC : RestAdapter.LogLevel.NONE)
                .build()
                .create(YleApiService.class);
    }

    @Provides
    @ApplicationScope
    Gson provideGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapterFactory(new AutoParcelGsonTypeAdapterFactory());
        builder.registerTypeAdapter(TextBundle.class, new TextBundleDeserializer());
        return builder.create();
    }

    @Provides
    @ApplicationScope
    @UiThread
    Scheduler provideUiScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
