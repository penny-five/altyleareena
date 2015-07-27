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

package com.github.pennyfive.altyleareena.ui.program;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.pennyfive.altyleareena.R;
import com.github.pennyfive.altyleareena.model.Language;
import com.github.pennyfive.altyleareena.model.programs.Program;
import com.github.pennyfive.altyleareena.ui.base.images.CloudinaryImageLoader;
import com.github.pennyfive.altyleareena.ui.base.mvp.impl.AbsAsyncView;
import com.github.pennyfive.altyleareena.utils.DaggerUtils;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProgramViewImpl extends AbsAsyncView implements ProgramView {
    @Inject ProgramPresenter presenter;
    @Inject CloudinaryImageLoader imageLoader;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar) CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.toolbar_backdrop) ImageView toolbarBackdrop;

    public ProgramViewImpl(Context context) {
        this(context, null);
    }

    public ProgramViewImpl(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgramViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DaggerUtils.findComponent(context, ProgramActivityInstanceComponent.class).inject(this);
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
    protected View onCreateContentDecor(LayoutInflater inflater, ViewGroup parent) {
        View decor = inflater.inflate(R.layout.view_program_decor, parent, false);
        ButterKnife.bind(this, decor);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(v -> ((Activity) getContext()).finish());
        return decor;
    }

    @Override
    protected android.view.View onCreateContentView(@NonNull LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.view_program, parent, false);
    }

    @Override
    public void setItem(Program program) {
        imageLoader.load(program.imageId()).into(toolbarBackdrop);
        toolbarLayout.setTitle(program.getTitle(Language.FI));
        ((TextView) getContentView().findViewById(R.id.description)).setText(program.descriptions().get(Language.FI));
    }
}
