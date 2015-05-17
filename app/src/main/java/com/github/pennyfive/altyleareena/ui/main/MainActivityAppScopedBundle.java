package com.github.pennyfive.altyleareena.ui.main;

import com.github.pennyfive.altyleareena.model.categories.CategoriesStore;
import com.github.pennyfive.altyleareena.ui.main.categories.CategoriesMVPPresenter;

public class MainActivityAppScopedBundle {
    private final CategoriesMVPPresenter categoriesMVPPresenter;

    public MainActivityAppScopedBundle(CategoriesStore store) {
        categoriesMVPPresenter = new CategoriesMVPPresenter(store);
    }

    public CategoriesMVPPresenter getCategoriesMVPPresenter() {
        return categoriesMVPPresenter;
    }
}
