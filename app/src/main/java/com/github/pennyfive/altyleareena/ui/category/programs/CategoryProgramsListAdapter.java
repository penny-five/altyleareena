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
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.pennyfive.altyleareena.R;
import com.github.pennyfive.altyleareena.model.programs.Program;
import com.github.pennyfive.altyleareena.ui.base.adapter.ArrayRecyclerAdapter;

import java.util.Collection;

public class CategoryProgramsListAdapter extends ArrayRecyclerAdapter<Program, CategoryProgramsListViewHolder> {

    public CategoryProgramsListAdapter(Context context) {
        super(context);
    }

    public CategoryProgramsListAdapter(Context context, Collection<Program> items) {
        super(context, items);
    }

    @Override
    public CategoryProgramsListViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int ViewType) {
        return new CategoryProgramsListViewHolder(inflater.inflate(R.layout.item_one_line, parent, false));
    }

    @Override
    public void onBindViewHolder(CategoryProgramsListViewHolder holder, Program item) {
        holder.bind(item);
    }
}
