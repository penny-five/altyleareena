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

package com.github.pennyfive.altyleareena.ui.base.mvp.impl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.pennyfive.altyleareena.R;
import com.github.pennyfive.altyleareena.ui.base.adapter.ArrayRecyclerAdapter;
import com.github.pennyfive.altyleareena.ui.base.adapter.BindingViewHolder;
import com.github.pennyfive.altyleareena.ui.base.mvp.AsyncListView;

import java.util.List;

public abstract class AbsAsyncListView<T, VH extends BindingViewHolder<T>> extends AbsAsyncView implements AsyncListView<T> {
    private RecyclerView recyclerView;

    public AbsAsyncListView(Context context) {
        super(context);
    }

    public AbsAsyncListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsAsyncListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater) {
        recyclerView = new RecyclerView(getContext());
        recyclerView.setId(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        return recyclerView;
    }

    @Override
    public void setItems(List<T> items) {
        ArrayRecyclerAdapter<T, VH> adapter = new ArrayRecyclerAdapter<T, VH>(getContext(), items) {
            @Override
            public VH onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int ViewType) {
                return AbsAsyncListView.this.onCreateViewHolder(inflater, parent, ViewType);
            }

            @Override
            public void onBindViewHolder(VH holder, T item) {
                holder.onBind(item);
            }
        };
        adapter.setItemClickListener(position -> onItemClick(position, adapter.getItem(position)));
        recyclerView.setAdapter(adapter);
    }

    protected abstract VH onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int ViewType);

    protected abstract void onItemClick(int position, T item);
}
