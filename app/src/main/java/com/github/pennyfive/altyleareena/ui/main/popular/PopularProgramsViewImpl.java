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

package com.github.pennyfive.altyleareena.ui.main.popular;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.pennyfive.altyleareena.R;
import com.github.pennyfive.altyleareena.model.programs.Program;
import com.github.pennyfive.altyleareena.ui.base.images.CloudinaryImageLoader;
import com.github.pennyfive.altyleareena.ui.base.mvp.impl.AbsAsyncCollectionView;
import com.github.pennyfive.altyleareena.ui.base.recyclerview.RecyclerViewBuilder;
import com.github.pennyfive.altyleareena.ui.common.ProgramItemHolder;
import com.github.pennyfive.altyleareena.ui.main.MainActivityInstanceComponent;
import com.github.pennyfive.altyleareena.utils.DaggerUtils;

import javax.inject.Inject;

public class PopularProgramsViewImpl extends AbsAsyncCollectionView<Program, ProgramItemHolder> implements PopularProgramsView {
    @Inject PopularProgramsPresenter presenter;
    @Inject CloudinaryImageLoader imageLoader;

    public PopularProgramsViewImpl(Context context) {
        this(context, null);
    }

    public PopularProgramsViewImpl(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopularProgramsViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DaggerUtils.findComponent(context, MainActivityInstanceComponent.class).inject(this);
    }

    @Inject
    public void setPresenter(PopularProgramsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.bindView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.dropView(this);
    }

    @Override
    protected void onSetupRecyclerViewBuilder(RecyclerViewBuilder builder) {
        super.onSetupRecyclerViewBuilder(builder);
        builder.setIsStaggered(true);
        builder.setSpanCountFromResource(R.integer.grid_span_count);
        builder.setPaddingFromResource(R.dimen.grid_padding);
    }

    @Override
    protected ProgramItemHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int ViewType) {
        ProgramItemHolder holder = new ProgramItemHolder(getContext(), parent);
        holder.setImageLoader(imageLoader);
        return holder;
    }

    @Override
    protected void onItemClick(int position, Program item) {
        presenter.onProgramClicked(item);
    }

    @Override
    protected void onRetryAfterErrorClicked() {
        presenter.onViewRetryAfterErrorClicked();
    }

    @Override
    public void showProgram(Program program) {

    }
}
