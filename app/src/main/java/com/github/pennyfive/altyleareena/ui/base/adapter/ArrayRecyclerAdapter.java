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

package com.github.pennyfive.altyleareena.ui.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.pennyfive.altyleareena.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Simple {@link RecyclerView} backed by an array of items.
 *
 * @param <E>
 * @param <VH>
 */
public abstract class ArrayRecyclerAdapter<E, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private final Context context;
    private final List<E> items = new ArrayList<>();

    private OnItemClickListener clickDelegate;
    private final View.OnClickListener clickListener = v -> {
        if (clickDelegate != null) {
            int position = (int) v.getTag(R.id.adapter_position);
            clickDelegate.onItemClick(position);
        }
    };

    public ArrayRecyclerAdapter(Context context) {
        this.context = context;
    }

    public ArrayRecyclerAdapter(Context context, Collection<E> items) {
        this.context = context;
        this.items.addAll(items);
    }

    public final void setItemClickListener(OnItemClickListener listener) {
        this.clickDelegate = listener;
    }

    public final void addItem(E item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public final void addItems(Collection<E> items) {
        this.items.addAll(items);
        notifyItemRangeInserted(this.items.size() - items.size(), items.size());
    }

    public final void clearAll() {
        int sizeBefore = items.size();
        items.clear();
        notifyItemRangeRemoved(0, sizeBefore);
    }

    public final void replaceAll(Collection<E> items) {
        clearAll();
        addItems(items);
    }

    public final E getItem(int position) {
        return items.get(position);
    }

    public final List<E> getItems() {
        return new ArrayList<>(items);
    }

    @Override
    public final VH onCreateViewHolder(ViewGroup parent, int viewType) {
        VH holder = onCreateViewHolder(LayoutInflater.from(context), parent, viewType);
        holder.itemView.setOnClickListener(clickListener);
        return holder;
    }

    public abstract VH onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int ViewType);

    @Override
    public final void onBindViewHolder(VH holder, int position) {
        onBindViewHolder(holder, items.get(position));
        holder.itemView.setTag(R.id.adapter_position, position);
        holder.itemView.setClickable(clickDelegate != null);
    }

    public abstract void onBindViewHolder(VH holder, E item);

    @Override
    public final int getItemCount() {
        return items.size();
    }
}
