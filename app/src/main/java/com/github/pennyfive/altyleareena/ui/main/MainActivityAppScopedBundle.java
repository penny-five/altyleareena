package com.github.pennyfive.altyleareena.ui.main;

import com.github.pennyfive.altyleareena.model.categories.CategoriesStore;
import com.github.pennyfive.altyleareena.ui.main.categories.CategoriesMvpPresenter;

import rx.Scheduler;

public class MainActivityAppScopedBundle {
    private final CategoriesMvpPresenter categoriesMVPPresenter;

    public MainActivityAppScopedBundle(CategoriesStore store, Scheduler scheduler) {
        categoriesMVPPresenter = new CategoriesMvpPresenter(store, scheduler);
    }

    public CategoriesMvpPresenter getCategoriesMVPPresenter() {
        return categoriesMVPPresenter;
    }
}
