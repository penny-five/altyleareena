package com.github.pennyfive.altyleareena.ui.main.categories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.pennyfive.altyleareena.R;
import com.github.pennyfive.altyleareena.model.categories.Category;
import com.github.pennyfive.altyleareena.ui.base.adapter.ArrayRecyclerAdapter;

import java.util.Collection;

public class CategoriesAdapter extends ArrayRecyclerAdapter<Category, CategoriesViewHolder> {

    public CategoriesAdapter(Context context) {
        super(context);
    }

    public CategoriesAdapter(Context context, Collection<Category> items) {
        super(context, items);
    }

    @Override
    public CategoriesViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int ViewType) {
        return new CategoriesViewHolder(inflater.inflate(R.layout.item_categories, parent, false));
    }

    @Override
    public void onBindViewHolder(CategoriesViewHolder holder, Category item) {
        holder.bind(item);
    }
}
