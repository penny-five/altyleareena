package com.github.pennyfive.altyleareena.ui.base.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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

    private final View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(@NonNull View v) {
            if (clickDelegate != null) {
                int position = (int) v.getTag(R.id.adapter_position);
                clickDelegate.onItemClick(position);
            }
        }
    };
    private OnItemClickListener clickDelegate;

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
