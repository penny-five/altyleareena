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

package com.github.pennyfive.altyleareena.ui.base.images;

import android.app.Activity;

import com.bumptech.glide.Glide;

public class CloudinaryImageLoaderFactory {
    private final String cdnRootPath;
    private final boolean printDebug;

    public CloudinaryImageLoaderFactory(String cdnRootPath, boolean printDebug) {
        this.cdnRootPath = cdnRootPath;
        this.printDebug = printDebug;
    }

    public CloudinaryImageLoader createLoader(Activity activity) {
        return createLoader(() -> Glide.with(activity));
    }

    private CloudinaryImageLoader createLoader(GlideRequestManagerCreator creator) {
        CloudinaryImageLoader instance = new CloudinaryImageLoader(cdnRootPath, creator);
        instance.setDebugEnabled(printDebug);
        return instance;
    }
}
