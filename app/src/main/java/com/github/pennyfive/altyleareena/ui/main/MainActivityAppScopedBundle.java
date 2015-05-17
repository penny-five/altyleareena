package com.github.pennyfive.altyleareena.ui.main;

import com.github.pennyfive.altyleareena.model.categories.CategoriesStore;
import com.github.pennyfive.altyleareena.ui.main.categories.CategoriesMvpPresenter;

public class MainActivityAppScopedBundle {
    private final CategoriesMvpPresenter categoriesMVPPresenter;

    public MainActivityAppScopedBundle(CategoriesStore store) {
        categoriesMVPPresenter = new CategoriesMvpPresenter(store);
    }

    public CategoriesMvpPresenter getCategoriesMVPPresenter() {
        return categoriesMVPPresenter;
    }
}
