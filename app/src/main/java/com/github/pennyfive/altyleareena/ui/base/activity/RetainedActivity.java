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
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public abstract class RetainedActivity extends AppCompatActivity {
    private static final String TAG_FRAGMENT = "retained_fragment";
    private Object data;
    private RetainedFragment retainedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retainedFragment = (RetainedFragment) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
        if (retainedFragment == null) {
            retainedFragment = new RetainedFragment();
            getSupportFragmentManager().beginTransaction().add(retainedFragment, TAG_FRAGMENT).commit();
        } else {
            data = retainedFragment.data;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        retainedFragment.data = this.data;
    }

    public boolean hasRetainedData() {
        return this.data != null;
    }

    public Object getRetainedData() {
        return data;
    }

    public void setRetainedData(Object data) {
        this.data = data;
    }

    public static class RetainedFragment extends Fragment {
        private Object data;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }
    }
}
