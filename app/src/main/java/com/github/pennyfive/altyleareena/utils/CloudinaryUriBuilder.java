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

package com.github.pennyfive.altyleareena.utils;

import android.net.Uri;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Constructs Cloudinary Uris that follow pattern
 * <p>
 * <code>http:{cdn}/{transformations}/{id}.{format}</code>
 * <p>
 * Default format is .png.
 */
public class CloudinaryUriBuilder {
    public enum ImageFormat {
        JPG("jpg"),
        PNG("png"),
        GIF("gif");

        private final String fileExtension;

        ImageFormat(String fileExtension) {
            this.fileExtension = fileExtension;
        }
    }

    private static final String CLOUDINARY_ROOT_PATH = "image/upload";
    private final String cdnPath;
    private ImageFormat format = ImageFormat.PNG;
    private int widthInPixels = -1;
    private int heightInPixels = -1;
    private String id;

    public CloudinaryUriBuilder(String cdnPath) {
        this.cdnPath = cdnPath;
    }

    public CloudinaryUriBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public CloudinaryUriBuilder setImageFormat(ImageFormat format) {
        this.format = format;
        return this;
    }

    public CloudinaryUriBuilder setWidth(int widthInPixels) {
        this.widthInPixels = widthInPixels;
        return this;
    }

    public CloudinaryUriBuilder setHeight(int heightInPixels) {
        this.heightInPixels = heightInPixels;
        return this;
    }

    /**
     * @return Cloudinary Uri or null if no image id was provided.
     */
    public Uri buildUri() {
        if (id == null || id.isEmpty()) {
            return null;
        }

        Uri.Builder builder = Uri.parse(cdnPath).buildUpon();
        builder.appendEncodedPath(CLOUDINARY_ROOT_PATH);
        builder.appendEncodedPath(constructManipulationsPath());
        return builder.appendPath(id + "." + format.fileExtension).build();
    }

    private String constructManipulationsPath() {
        List<String> manipulations = new ArrayList<>();
        if (widthInPixels > 0) manipulations.add("w_" + widthInPixels);
        if (heightInPixels > 0) manipulations.add("h_" + heightInPixels);
        return TextUtils.join(",", manipulations);
    }
}
