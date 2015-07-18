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

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

/**
 * Decorates RecyclerView items with padding. Works with ~all different LayoutManagers.
 */
public class PaddingDecoration extends RecyclerView.ItemDecoration {
    private final int leftPadding;
    private final int topPadding;
    private final int rightPadding;
    private final int bottomPadding;

    public PaddingDecoration(int padding) {
        this.leftPadding = padding;
        this.topPadding = padding;
        this.rightPadding = padding;
        this.bottomPadding = padding;
    }

    public PaddingDecoration(int leftPadding, int topPadding, int rightPadding, int bottomPadding) {
        this.leftPadding = leftPadding;
        this.topPadding = topPadding;
        this.rightPadding = rightPadding;
        this.bottomPadding = bottomPadding;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        SpanResolver resolver = getSpanResolver(parent.getLayoutManager());
        int spanIndex = resolver.resolveChildSpanIndex(view.getLayoutParams());
        int spanCount = resolver.resolveSpanCount(parent.getLayoutManager());
        boolean isChildInFirstColumn = spanIndex == 0;
        boolean isChildInLastColumn = spanIndex == spanCount - 1;
        boolean isChildInFirstRow = parent.getChildAdapterPosition(view) < spanCount;
        outRect.left = isChildInFirstColumn ? leftPadding : (int) (leftPadding * 0.5);
        outRect.top = isChildInFirstRow ? topPadding : 0;
        outRect.right = isChildInLastColumn ? rightPadding : (int) (rightPadding * 0.5);
        outRect.bottom = bottomPadding;
    }

    private SpanResolver getSpanResolver(RecyclerView.LayoutManager manager) {
        if (manager instanceof GridLayoutManager) {
            return GRID_LAYOUT_MANAGER_SPAN_RESOLVER;
        } else if (manager instanceof StaggeredGridLayoutManager) {
            return STAGGERED_GRID_LAYOUT_MANAGER_SPAN_RESOLVER;
        } else if (manager instanceof LinearLayoutManager) {
            return LINEAR_LAYOUT_MANAGER_SPAN_RESOLVER;
        }
        throw new IllegalStateException("Unsupported LayoutManager impl: " + manager.getClass().getName());
    }

    private interface SpanResolver {
        int resolveSpanCount(RecyclerView.LayoutManager manager);

        int resolveChildSpanIndex(ViewGroup.LayoutParams childLayoutParams);
    }

    private static final SpanResolver GRID_LAYOUT_MANAGER_SPAN_RESOLVER = new SpanResolver() {
        @Override
        public int resolveSpanCount(RecyclerView.LayoutManager manager) {
            return ((GridLayoutManager) manager).getSpanCount();
        }

        @Override
        public int resolveChildSpanIndex(ViewGroup.LayoutParams childLayoutParams) {
            return ((GridLayoutManager.LayoutParams) childLayoutParams).getSpanIndex();
        }
    };

    private static final SpanResolver STAGGERED_GRID_LAYOUT_MANAGER_SPAN_RESOLVER = new SpanResolver() {
        @Override
        public int resolveSpanCount(RecyclerView.LayoutManager manager) {
            return ((StaggeredGridLayoutManager) manager).getSpanCount();
        }

        @Override
        public int resolveChildSpanIndex(ViewGroup.LayoutParams childLayoutParams) {
            return ((StaggeredGridLayoutManager.LayoutParams) childLayoutParams).getSpanIndex();
        }
    };

    private static final SpanResolver LINEAR_LAYOUT_MANAGER_SPAN_RESOLVER = new SpanResolver() {
        @Override
        public int resolveSpanCount(RecyclerView.LayoutManager manager) {
            return 1;
        }

        @Override
        public int resolveChildSpanIndex(ViewGroup.LayoutParams childLayoutParams) {
            return 1;
        }
    };
}
