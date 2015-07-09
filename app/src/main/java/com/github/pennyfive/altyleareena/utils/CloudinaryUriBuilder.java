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
 * <code>http:{cdn}/{transformations}/{id}.{imageFormat}</code>
 * <p>
 * Default imageFormat is .jpg.
 */
@SuppressWarnings("unused")
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

    public enum CropMode {
        SCALE("scale"),
        FIT("fit"),
        FILL("fill");

        private final String paramName;

        CropMode(String paramName) {
            this.paramName = paramName;
        }
    }

    private static final String CLOUDINARY_ROOT_PATH = "image/upload";
    private final String cdnPath;
    private ImageFormat imageFormat = ImageFormat.JPG;
    private CropMode cropMode = CropMode.FIT;
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

    public String getId() {
        return id;
    }

    public CloudinaryUriBuilder setImageFormat(ImageFormat format) {
        this.imageFormat = format;
        return this;
    }

    public ImageFormat getImageFormat() {
        return imageFormat;
    }

    public CloudinaryUriBuilder setCropMode(CropMode cropMode) {
        this.cropMode = cropMode;
        return this;
    }

    public CropMode getCropMode() {
        return cropMode;
    }

    public CloudinaryUriBuilder setWidthInPixels(int widthInPixels) {
        this.widthInPixels = widthInPixels;
        return this;
    }

    public int getWidthInPixels() {
        return widthInPixels;
    }

    public CloudinaryUriBuilder setHeightInPixels(int heightInPixels) {
        this.heightInPixels = heightInPixels;
        return this;
    }

    public int getHeightInPixels() {
        return heightInPixels;
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
        return builder.appendPath(id + "." + imageFormat.fileExtension).build();
    }

    private String constructManipulationsPath() {
        List<String> manipulations = new ArrayList<>();
        if (widthInPixels > 0) manipulations.add("w_" + widthInPixels);
        if (heightInPixels > 0) manipulations.add("h_" + heightInPixels);
        if (widthInPixels > 0 || heightInPixels > 0) manipulations.add("c_" + cropMode.paramName);
        return TextUtils.join(",", manipulations);
    }
}
