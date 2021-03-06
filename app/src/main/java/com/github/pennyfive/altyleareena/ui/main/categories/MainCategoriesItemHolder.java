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

package com.github.pennyfive.altyleareena.ui.main.categories;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.pennyfive.altyleareena.R;
import com.github.pennyfive.altyleareena.model.Language;
import com.github.pennyfive.altyleareena.model.categories.Category;
import com.github.pennyfive.altyleareena.ui.CategoryTheme;
import com.github.pennyfive.altyleareena.ui.base.adapter.BindingViewHolder;

import butterknife.Bind;

public class MainCategoriesItemHolder extends BindingViewHolder<Category> {
    @Bind(R.id.category_icon) ImageView iconImageView;
    @Bind(R.id.category_name) TextView nameTextView;

    public MainCategoriesItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBind(Category item) {
        CategoryTheme theme = CategoryTheme.resolve(item);
        itemView.setBackgroundResource(theme.getColorResource());
        iconImageView.setImageResource(theme.getIconResource());
        nameTextView.setText(item.titles().get(Language.FI));
    }
}
