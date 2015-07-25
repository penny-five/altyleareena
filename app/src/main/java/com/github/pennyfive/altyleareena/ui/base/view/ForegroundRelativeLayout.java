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

package com.github.pennyfive.altyleareena.ui.base.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION_CODES;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.github.pennyfive.altyleareena.R;

/**
 * {@link RelativeLayout} with added support for foreground drawables.
 */
public class ForegroundRelativeLayout extends RelativeLayout {
    private Drawable foregroundDrawable;

    public ForegroundRelativeLayout(Context context) {
        this(context, null);
    }

    public ForegroundRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ForegroundRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttrs(attrs);
    }

    private void readAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.ForegroundRelativeLayout);
            Drawable foreground = ta.getDrawable(R.styleable.ForegroundRelativeLayout_foreground);
            setForeground(foreground);
            ta.recycle();
        }
    }

    public void setForegroundResource(@DrawableRes int resid) {
        setForeground(getResources().getDrawable(resid));
    }

    public void setForeground(Drawable drawable) {
        foregroundDrawable = drawable;
        if (foregroundDrawable != null) {
            foregroundDrawable.setCallback(this);
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (foregroundDrawable != null) {
            foregroundDrawable.setState(getDrawableState());
            invalidate();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (foregroundDrawable != null) {
            foregroundDrawable.setBounds(0, 0, w, h);
        }
    }

    @Override
    protected void dispatchDraw(@NonNull Canvas canvas) {
        super.dispatchDraw(canvas);
        if (foregroundDrawable != null) {
            foregroundDrawable.draw(canvas);
        }
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return super.verifyDrawable(who) || (foregroundDrawable != null && who == foregroundDrawable);
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (foregroundDrawable != null) {
            foregroundDrawable.jumpToCurrentState();
        }
    }

    @TargetApi(VERSION_CODES.LOLLIPOP)
    @Override
    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);
        if (foregroundDrawable != null) {
            foregroundDrawable.setHotspot(x, y);
        }
    }
}
