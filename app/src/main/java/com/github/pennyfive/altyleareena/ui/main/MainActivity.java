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

package com.github.pennyfive.altyleareena.ui.main;

import android.os.Bundle;

import com.github.pennyfive.altyleareena.R;
import com.github.pennyfive.altyleareena.ui.base.activity.ScopedActivity;

public class MainActivity extends ScopedActivity<MainActivityComponent, MainActivityInstanceComponent> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected MainActivityComponent onCreateActivityComponent() {
        return DaggerMainActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .mainActivityModule(new MainActivityModule())
                .build();
    }

    @Override
    protected MainActivityInstanceComponent onCreateActivityInstanceComponent() {
        return DaggerMainActivityInstanceComponent.builder()
                .mainActivityComponent(getActivityComponent())
                .mainActivityInstanceModule(new MainActivityInstanceModule(this))
                .build();
    }
}
