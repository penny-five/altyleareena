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

package com.github.pennyfive.altyleareena.ui.category.programs;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

import com.github.pennyfive.altyleareena.R;
import com.github.pennyfive.altyleareena.model.programs.Program;
import com.github.pennyfive.altyleareena.ui.base.adapter.ArrayRecyclerAdapter;
import com.github.pennyfive.altyleareena.ui.base.mvp.impl.AbsStatefulMvpView;
import com.github.pennyfive.altyleareena.ui.base.utils.Views;
import com.github.pennyfive.altyleareena.ui.category.CategoryActivityComponent;
import com.github.pennyfive.altyleareena.utils.DaggerUtils;

import java.util.List;

import javax.inject.Inject;

public class CategoryProgramsListViewImpl extends AbsStatefulMvpView implements CategoryProgramsListView, ArrayRecyclerAdapter.OnItemClickListener {
    private CategoryProgramsListPresenter presenter;
    private CategoryProgramsListAdapter adapter;
    private RecyclerView recyclerView;

    public CategoryProgramsListViewImpl(Context context) {
        this(context, null);
    }

    public CategoryProgramsListViewImpl(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CategoryProgramsListViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DaggerUtils.findComponent(context, CategoryActivityComponent.class).injectProgramsListView(this);
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        recyclerView = new RecyclerView(getContext());
        recyclerView.setId(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Inject
    public void setPresenter(CategoryProgramsListPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.bindView(this);
    }

    @Override
    public void saveHierarchyState(@NonNull SparseArray<Parcelable> container) {
        super.saveHierarchyState(container);
        Views.saveHierarchyStates(container, recyclerView);
    }

    @Override
    public void restoreHierarchyState(@NonNull SparseArray<Parcelable> container) {
        super.restoreHierarchyState(container);
        Views.restoreHierarchyStates(container, recyclerView);
    }

    @Override
    protected View onCreateContentView(@NonNull LayoutInflater inflater) {
        return recyclerView;
    }

    @Override
    public void setPrograms(List<Program> programs) {
        adapter = new CategoryProgramsListAdapter(getContext(), programs);
        adapter.setItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.dropView(this);
    }

    @Override
    public void onItemClick(int position) {
        presenter.onProgramClicked(adapter.getItem(position));
    }

}
