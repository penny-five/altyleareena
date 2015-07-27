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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.github.pennyfive.altyleareena.model.programs.Program;
import com.github.pennyfive.altyleareena.ui.base.activity.ScopedActivity;

public class ProgramActivity extends ScopedActivity<ProgramActivityComponent, ProgramActivityInstanceComponent> {
    private static final String EXTRA_PROGRAM_ID = "program";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ProgramViewImpl(this));
    }

    @Override
    protected ProgramActivityComponent onCreateActivityComponent() {
        String programId = getIntent().getStringExtra(EXTRA_PROGRAM_ID);
        return DaggerProgramActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .programActivityModule(new ProgramActivityModule(programId))
                .build();
    }

    @Override
    protected ProgramActivityInstanceComponent onCreateActivityInstanceComponent() {
        return DaggerProgramActivityInstanceComponent.builder()
                .programActivityComponent(getActivityComponent())
                .programActivityInstanceModule(new ProgramActivityInstanceModule(this))
                .build();
    }

    public static void launchWith(Context context, Program program) {
        Intent intent = new Intent(context, ProgramActivity.class);
        intent.putExtra(EXTRA_PROGRAM_ID, program.id());
        context.startActivity(intent);
    }
}
