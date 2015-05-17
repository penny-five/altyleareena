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

package com.github.pennyfive.altyleareena.ui.base.layout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

public abstract class AbsStatefulFrameLayout extends FrameLayout {
    private static final int STATE_NOT_DEFINED = -255;
    private int currentState = STATE_NOT_DEFINED;

    public AbsStatefulFrameLayout(Context context) {
        this(context, null);
    }

    public AbsStatefulFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AbsStatefulFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected final void changeState(int state) {
        if (state != currentState) {
            currentState = state;
            applyState();
        }
    }

    private void applyState() {
        removeAllViews();

        if (currentState != STATE_NOT_DEFINED) {
            View view = onCreateViewForState(LayoutInflater.from(getContext()), currentState);
            if (view == null) {
                throw new IllegalStateException("Didn't create view for state " + currentState);
            }
            onViewCreatedForState(view, currentState);
            addView(view);
        }
    }

    protected abstract View onCreateViewForState(@NonNull LayoutInflater inflater, int state);

    protected void onViewCreatedForState(@NonNull View view, int state) {
    }
}
