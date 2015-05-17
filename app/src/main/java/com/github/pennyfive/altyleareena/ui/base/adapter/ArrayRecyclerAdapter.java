package com.github.pennyfive.altyleareena.ui.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

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
    private final Context context;
    private final List<E> items = new ArrayList<>();

    public ArrayRecyclerAdapter(Context context) {
        this.context = context;
    }

    public ArrayRecyclerAdapter(Context context, Collection<E> items) {
        this.context = context;
        this.items.addAll(items);
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

    @Override
    public final VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateViewHolder(LayoutInflater.from(context), parent, viewType);
    }

    public abstract VH onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int ViewType);

    @Override
    public final void onBindViewHolder(VH holder, int position) {
        onBindViewHolder(holder, items.get(position));
    }

    public abstract void onBindViewHolder(VH holder, E item);

    @Override
    public final int getItemCount() {
        return items.size();
    }
}
