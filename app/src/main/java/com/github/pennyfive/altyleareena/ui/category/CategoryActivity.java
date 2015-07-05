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

package com.github.pennyfive.altyleareena.ui.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.github.pennyfive.altyleareena.ApplicationComponent;
import com.github.pennyfive.altyleareena.R;
import com.github.pennyfive.altyleareena.model.categories.Category;
import com.github.pennyfive.altyleareena.utils.DaggerUtils;
import com.github.pennyfive.altyleareena.utils.annotations.ProvidesComponent;

public class CategoryActivity extends FragmentActivity implements ProvidesComponent<CategoryActivityComponent> {
    private static final String EXTRA_CATEGORY = "category";

    private CategoryActivityComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildActivityComponent();
        setContentView(R.layout.activity_category);
    }

    private void buildActivityComponent() {
        Category category = getIntent().getParcelableExtra(EXTRA_CATEGORY);
        component = DaggerCategoryActivityComponent.builder()
                .applicationComponent(DaggerUtils.findComponent(this, ApplicationComponent.class))
                .categoryActivityModule(new CategoryActivityModule(category))
                .build();
    }

    @Override
    public CategoryActivityComponent provideComponent() {
        return component;
    }

    public static void launchWith(Context context, Category category) {
        Intent intent = new Intent(context, CategoryActivity.class);
        intent.putExtra(EXTRA_CATEGORY, category);
        context.startActivity(intent);
    }
}
