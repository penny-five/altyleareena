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

package com.github.pennyfive.altyleareena.ui.common;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.pennyfive.altyleareena.R;
import com.github.pennyfive.altyleareena.model.Language;
import com.github.pennyfive.altyleareena.model.categories.Category;
import com.github.pennyfive.altyleareena.model.programs.Program;
import com.github.pennyfive.altyleareena.ui.CategoryTheme;
import com.github.pennyfive.altyleareena.ui.base.adapter.BindingViewHolder;
import com.github.pennyfive.altyleareena.ui.base.images.CloudinaryImageLoader;
import com.github.pennyfive.altyleareena.utils.CloudinaryUriBuilder;

import java.util.List;

import butterknife.Bind;

public class ProgramItemHolder extends BindingViewHolder<Program> {
    private CloudinaryImageLoader imageLoader;

    @Bind(R.id.image) ImageView image;
    @Bind(R.id.series_name) TextView seriesNameTextView;
    @Bind(R.id.program_name) TextView programNameTextView;
    @Bind(R.id.category_name) TextView categoryNameTextView;

    public ProgramItemHolder(Context context, ViewGroup parent) {
        super(context, R.layout.card_program, parent);
    }

    public void setImageLoader(CloudinaryImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    @Override
    public void onBind(Program program) {
        imageLoader.load(program.imageId()).withCropMode(CloudinaryUriBuilder.CropMode.FIT).withHeight(250).into(image);
        programNameTextView.setText(program.getTitle(Language.FI));

        String programTitle = program.getTitle(Language.FI);
        programNameTextView.setText(programTitle);
        programNameTextView.setVisibility(programTitle != null ? View.VISIBLE : View.GONE);

        String seriesTitle = program.getSeriesTitle(Language.FI);
        seriesNameTextView.setText(seriesTitle);
        seriesNameTextView.setVisibility(seriesTitle != null ? View.VISIBLE : View.GONE);

        categoryNameTextView.setText(program.categories().get(0).titles().get(Language.FI));
        categoryNameTextView.setTextColor(resolveDisplayedThemeColor(program.categories()));
    }

    private int resolveDisplayedThemeColor(List<Category> categories) {
        @ColorRes int colorResource = -1;
        /* Iterate backwards. Categories are ordered from most specific to least specific. */
        for (int i = categories.size() - 1; i >= 0; i--) {
            CategoryTheme theme = CategoryTheme.resolve(categories.get(i));
            if (theme != CategoryTheme.UNKNOWN) {
                colorResource = theme.getColorResource();
                break;
            }
        }
        if (colorResource == -1) {
            colorResource = CategoryTheme.UNKNOWN.getColorResource();
        }
        return itemView.getResources().getColor(colorResource);
    }
}
