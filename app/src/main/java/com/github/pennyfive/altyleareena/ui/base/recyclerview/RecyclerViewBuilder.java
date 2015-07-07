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

package com.github.pennyfive.altyleareena.ui.base.recyclerview;

import android.content.Context;
import android.support.annotation.DimenRes;
import android.support.annotation.IntRange;
import android.support.annotation.IntegerRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class RecyclerViewBuilder {
    private final Context context;
    private int padding;
    private int spanCount;

    public RecyclerViewBuilder(Context context) {
        this.context = context;
    }

    public RecyclerViewBuilder setSpanCount(@IntRange(from = 0) int spanCount) {
        this.spanCount = spanCount;
        return this;
    }

    public RecyclerViewBuilder setSpanCountFromResource(@IntegerRes int resid) {
        this.spanCount = context.getResources().getInteger(resid);
        return this;
    }

    public int getSpanCount() {
        return spanCount;
    }

    public RecyclerViewBuilder setPadding(@IntRange(from = 0) int paddingInPixels) {
        this.padding = paddingInPixels;
        return this;
    }

    public RecyclerViewBuilder setPaddingFromResource(@DimenRes int resid) {
        this.padding = context.getResources().getDimensionPixelSize(resid);
        return this;
    }

    public int getPadding() {
        return padding;
    }

    public RecyclerView build() {
        RecyclerView recyclerView = new RecyclerView(context);
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), spanCount));
        if (padding > 0) {
            PaddingDecoration decoration = new PaddingDecoration(padding);
            decoration.setSpanCount(spanCount);
            recyclerView.addItemDecoration(decoration);
        }
        return recyclerView;
    }
}
