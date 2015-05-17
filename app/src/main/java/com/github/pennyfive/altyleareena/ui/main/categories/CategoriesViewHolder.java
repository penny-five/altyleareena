package com.github.pennyfive.altyleareena.ui.main.categories;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.pennyfive.altyleareena.R;
import com.github.pennyfive.altyleareena.model.categories.Category;

public class CategoriesViewHolder extends RecyclerView.ViewHolder {

    public CategoriesViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(Category category) {
        ((TextView) itemView.findViewById(R.id.text)).setText(category.getTitleInLanguage("fi"));
    }
}
