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

package com.github.pennyfive.altyleareena.ui.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Presenter for MVP architecture.
 *
 * @param <T>
 * @see MVPView
 */
public abstract class MVPPresenter<T extends MVPView> {
    private Bundle savedState;
    private T view;

    @Nullable
    protected T getView() {
        return view;
    }

    public void bindView(T view) {
        this.view = view;
        onViewBound(view);
        if (savedState != null && !savedState.isEmpty()) {
            view.onRestoreState(savedState);
        }
    }

    abstract protected void onViewBound(T view);

    public void dropView(T view) {
        if (this.view != view) {
            throw new IllegalStateException("Tried to drop unbound view");
        }
        savedState = new Bundle();
        view.onSaveState(savedState);
        onViewDropped(this.view);
        this.view = null;
    }

    abstract protected void onViewDropped(T view);
}
