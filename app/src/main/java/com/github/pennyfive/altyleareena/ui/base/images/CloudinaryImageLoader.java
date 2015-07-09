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

import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.pennyfive.altyleareena.utils.CloudinaryUriBuilder;

/**
 * Wrapper around used image loading library (Glide) that provides a no-nonsense way to load images from Cloudinary backend
 * (in this case for programs that are returned from Yle API).
 */
public class CloudinaryImageLoader {
    private static final String LOG_TAG = "CloudinaryImageLoader";
    private boolean debugEnabled = true;

    private final String cdnRootPath;
    private final GlideRequestManagerCreator creator;
    private LoadRequest pendingRequest;

    CloudinaryImageLoader(String cdnRootPath, GlideRequestManagerCreator creator) {
        this.cdnRootPath = cdnRootPath;
        this.creator = creator;
    }

    void setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

    public LoadRequest load(String imageId) {
        if (pendingRequest != null) {
            throw new IllegalStateException("CloudinaryImageLoader already has pending request for " + pendingRequest.builder.getId());
        }
        CloudinaryUriBuilder builder = new CloudinaryUriBuilder(cdnRootPath);
        builder.setId(imageId);
        pendingRequest = new LoadRequest(builder);
        return pendingRequest;
    }

    private void commit(LoadRequest request, ImageView view) {
        DrawableTypeRequest<Uri> glideRequest = creator.create().load(request.builder.buildUri());
        if (debugEnabled) {
            glideRequest.listener(debugListener);
        }
        glideRequest.into(view);
        pendingRequest = null;
    }

    public class LoadRequest {
        private final CloudinaryUriBuilder builder;

        private LoadRequest(CloudinaryUriBuilder builder) {
            this.builder = builder;
        }

        public LoadRequest withWidth(int width) {
            builder.setWidthInPixels(width);
            return this;
        }

        public LoadRequest withHeight(int height) {
            builder.setHeightInPixels(height);
            return this;
        }

        public LoadRequest withImageFormat(CloudinaryUriBuilder.ImageFormat format) {
            builder.setImageFormat(format);
            return this;
        }

        public LoadRequest withCropMode(CloudinaryUriBuilder.CropMode cropMode) {
            builder.setCropMode(cropMode);
            return this;
        }

        public void into(ImageView view) {
            CloudinaryImageLoader.this.commit(this, view);
        }
    }

    private static final RequestListener<Uri, GlideDrawable> debugListener = new RequestListener<Uri, GlideDrawable>() {

        @Override
        public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
            if (model != null) Log.d(LOG_TAG, "ERROR: " + model + " ---> " + e);
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            if (model != null && !isFromMemoryCache) {
                Log.d(LOG_TAG, "<--- " + model);
            }
            return false;
        }

    };
}
