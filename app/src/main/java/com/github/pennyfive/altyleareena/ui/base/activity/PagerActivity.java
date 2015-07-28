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

package com.github.pennyfive.altyleareena.ui.base.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.github.pennyfive.altyleareena.R;
import com.github.pennyfive.altyleareena.ui.base.adapter.PagerViewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public abstract class PagerActivity extends ScopedActivity {
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.tabs) TabLayout tabs;
    @Bind(R.id.pager) ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        pager.setAdapter(new PagerViewAdapter() {
            @Override
            public View instantiateView(ViewGroup container, int position) {
                return PagerActivity.this.instantiateView(container, position);
            }

            @Override
            public int getCount() {
                return PagerActivity.this.getCount();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return PagerActivity.this.getPageTitle(position);
            }
        });

        tabs.setupWithViewPager(pager);
    }

    protected abstract int getCount();

    protected abstract String getPageTitle(int position);

    protected abstract View instantiateView(ViewGroup container, int position);

}
